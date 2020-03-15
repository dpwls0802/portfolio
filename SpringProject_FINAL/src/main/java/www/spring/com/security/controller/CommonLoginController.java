package www.spring.com.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommonLoginController {
	
	@GetMapping("/customLogin")
	public void customLogin(String err, String logout, Model model) {
		if (err != null)
			model.addAttribute("err", "로그인 실패. 아이디 및 비밀번호를 확인하세요");
		if (logout != null)
			model.addAttribute("logout", "로그아웃!");
	}
	
	@GetMapping("/customLogout")
	public void customLogout() {
	}

	@PostMapping("/customLogout")
	public void customLogoutPost() {
		System.out.println("나가기 성공!!!");
	}

	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		System.out.println("접근 권한 오류 발생");
		model.addAttribute("msg", "접근 권한 오류");
	}

	
	//연락처 메뉴 추가
	@GetMapping("/contact")
	public void contact() {
			
	}

}	
