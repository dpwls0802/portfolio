package com.record.travel;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.record.travel.x.Member;
import com.record.travel.x.MemberRepository;
import com.record.travel.x.MemberRole;

@SpringBootTest
public class MemberTests {
	
	@Autowired
	private MemberRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//@Test
	public void insertDummies() {
		//1-80까지는  user만 지원
		//81-90까지는  user, manager
		//91-100까지는 user, manager, admin
		
		IntStream.rangeClosed(1, 100).forEach(i ->{
			Member member = Member.builder()
					.email("user" + i + "@email.com")
					.name("사용자" + i)
					.fromSocial(false)
					.password(passwordEncoder.encode("1111"))
					.build();
			
			member.addMemberRole(MemberRole.USER);
			
			if(i>80) {
				member.addMemberRole(MemberRole.MANAGER);
			}
			
			if(i>90) {
				member.addMemberRole(MemberRole.ADMIN);
			}
			
			repository.save(member);
		});
	}
	
	@Test
	public void testRead() {
		Optional<Member> result = repository.findByEmail("user95@email.com", false);
		Member member = result.get();
		System.out.println(member);
	}
}
