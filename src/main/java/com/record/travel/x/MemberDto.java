package com.record.travel.x;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@ToString
public class MemberDto extends User implements OAuth2User { //dto 역할 + 시큐리티에서 인증/인가 작업에 사용
	private String email;
	private String password;
	private String name;
	private boolean fromSocial;
	private Map<String, Object> attr;
	
	public MemberDto(String username, String password, 
			boolean fromSocial, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
		this(username, password, fromSocial, authorities);
		this.attr = attr;
	}
	
	public MemberDto(String username, String password, 
			boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.email = username;
		this.password = password;
		this.fromSocial = fromSocial;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attr;
	}
	
	public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .fromSocial(fromSocial)
                .build();
    }

	/*
	 * @Builder public MemberDto(String email, String password, String name, boolean
	 * fromSocial) { this.email = email; this.password = password; this.name = name;
	 * this.fromSocial = fromSocial; }
	 */
}


