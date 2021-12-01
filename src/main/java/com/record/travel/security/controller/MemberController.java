package com.record.travel.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.record.travel.security.dto.IntegrationDto;
import com.record.travel.security.service.MemberService2;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {

	@Autowired
	private MemberService2 memberService2;

	// 로그인 페이지
	@GetMapping("/member/loginPage")
	public String loginPage() {
		return "/member/loginPage";
	}

	// 회원가입 페이지
	@GetMapping("/member/signUp")
	public String signUp() {
		return "/member/signUp";
	}

	// 회원가입 처리
	@PostMapping("/member/signUp")
	public String signUp(IntegrationDto integrationDto) {
		memberService2.joinUser(integrationDto);
		return "redirect:/member/loginPage";
	}
	
	//아이디 중복체크
    @GetMapping("/verify_id/{name}")
    @ResponseBody
    public boolean verify_id(@PathVariable("name") String name) {
        boolean flag = memberService2.verifyId(name);
        return flag;
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }

}
