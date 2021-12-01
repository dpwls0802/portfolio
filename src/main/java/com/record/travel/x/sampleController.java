package com.record.travel.x;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class sampleController {
	
	@PreAuthorize("permitAll()") // 모두 접근 가능
	@GetMapping("/all")
	public String all() {
		log.info("모든 사용자 접근!!!");
		return "thymeleaf/sample/all";
	}
	
	@GetMapping("/member")
	public String member(@AuthenticationPrincipal MemberDto memberDto) {
		log.info("회원만 접근!!!");
		log.info("===============================");
		log.info(memberDto);
		return "thymeleaf/sample/member";
	}
	
	@PreAuthorize("hasRole('ADMIN')") //관리자만 접근 가능
	@GetMapping("/admin")
	public String admin() {
		log.info("관리자만 접근!!!");
		return "thymeleaf/sample/admin";
	}
}
