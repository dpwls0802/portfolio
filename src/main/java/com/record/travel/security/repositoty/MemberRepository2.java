package com.record.travel.security.repositoty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.record.travel.security.entity.IntegrationEntity;

public interface MemberRepository2 extends JpaRepository<IntegrationEntity, Long> {
    Optional<IntegrationEntity> findByName(String email);
}
