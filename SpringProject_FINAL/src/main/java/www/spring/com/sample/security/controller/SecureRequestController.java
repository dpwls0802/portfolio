package www.spring.com.sample.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("sample/*")
@Controller
public class SecureRequestController {
	@GetMapping("/all")
	public void doAll() {
		System.out.println("모든 사용자 접근 가능");
	}

	@GetMapping("/member")
	public void doMember() {
		System.out.println("로그인한 사용자만 접근 가능");
	}

	@GetMapping("/admin")
	public void doAdmin() {
		System.out.println("관리자만 접근 가능");
	}
}
