package com.record.travel.x;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	private final MemberRepository memberRepository;
	
	@Transactional
    public String joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
               BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getEmail();
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("MemberService loadUserByUsername " + username);
		
		Optional<Member> result = memberRepository.findByEmail(username, false);
		
		if(!result.isPresent()) {
			throw new UsernameNotFoundException("이메일이나 소셜을 확인하세요.");
		}
		
		Member member = result.get();
		
		log.info("===========================");
		log.info(member);
		
		MemberDto memberDto = new MemberDto(
				member.getEmail(), 
				member.getPassword(), 
				member.isFromSocial(), 
				member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
				);
		
		memberDto.setName(member.getName());
		memberDto.setFromSocial(member.isFromSocial());
		
		return memberDto;
	}
	
	/*
	 * public Long save(MemberDto memberDto) { BCryptPasswordEncoder encoder = new
	 * BCryptPasswordEncoder();
	 * memberDto.setPassword(encoder.encode(memberDto.getPassword()));
	 * 
	 * return memberRepository.save(Member.builder() .email(memberDto.getEmail())
	 * .name(memberDto.getName()) .fromSocial(memberDto.isFromSocial())
	 * .password(memberDto.getPassword()) .build()).builder() }
	 */
	
}
