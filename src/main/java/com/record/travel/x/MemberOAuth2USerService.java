package com.record.travel.x;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberOAuth2USerService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("----------------------------------------------------------------");
		log.info("사용자 요청:" + userRequest);

		String clientName = userRequest.getClientRegistration().getClientName();

		log.info("클라이언트 이름 : " + clientName); // google로 출력
		log.info(userRequest.getAdditionalParameters());

		OAuth2User oAuth2User = super.loadUser(userRequest);

		log.info("///////////////////////////////////////");
		oAuth2User.getAttributes().forEach((k, v) -> {
			log.info(k + ":" + v); // sub, picture, email, email_verified, EMAIL 등 출력
		});

		String email = null;
		if (clientName.contentEquals("Google")) { // google 이용하는 경우
			email = oAuth2User.getAttribute("email");
		}

		log.info("이메일 : " + email);

		// Member member = saveSocialMember(email);
		// return oAuth2User;

		Member member = saveSocialMember(email);
		MemberDto memberDto = new MemberDto(
				member.getEmail(), member.getPassword(), true, member.getRoleSet().stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()),
				oAuth2User.getAttributes());
		memberDto.setName(member.getName());
		
		return memberDto;
	}

	private Member saveSocialMember(String email) {
		// 기존 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
		Optional<Member> result = memberRepository.findByEmail(email, true);

		if (result.isPresent()) {
			return result.get();
		}
		// 없다면 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로
		Member member = Member.builder().email(email).name(email).password(passwordEncoder.encode("1111")).build();

		member.addMemberRole(MemberRole.USER);

		memberRepository.save(member);

		return member;
	}
}
