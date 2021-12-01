package com.record.travel.x;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String> {
	// 일반 로그인 사용자와 소셜 로그인 사용자 구분 위해 별도 메서드 처리
	@EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
	@Query("select m from Member m where m.fromSocial = :social and m.email = :email")
	Optional<Member> findByEmail(String email, boolean social); // 사용자 이메일과 소셜로 추가된 회원 여부 선택해서 동작하도록 설계
}
