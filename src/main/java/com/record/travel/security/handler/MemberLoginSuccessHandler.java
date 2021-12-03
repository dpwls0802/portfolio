package com.record.travel.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.record.travel.x.MemberDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
//인증이 성공하거나 실패 후 처리 지정하는 용도
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler{
	//일반적인 로그인은 기존과 동일하게 이동, 소셜 로그인은 회원 정보 수정하는 경로로 이동하도록 구현
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private PasswordEncoder passwordEncoder;
	public MemberLoginSuccessHandler(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		log.info("--_--_--_--_--_--_--_--_--_--_--_--_--_--_");
		log.info("onAuthenticationSuccess ");
		
		MemberDto authMember = (MemberDto)authentication.getPrincipal();
		
		boolean fromSocial = authMember.isFromSocial();
		
		log.info("수정할 멤버가 필요한가?" + fromSocial);
		
		boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
		
		if(fromSocial && passwordResult) {
			redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
		}
		
	}
}
