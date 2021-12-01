package com.record.travel.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.record.travel.security.dto.CustomIntegrationDto;
import com.record.travel.security.dto.IntegrationDto;
import com.record.travel.security.entity.IntegrationEntity;
import com.record.travel.security.repositoty.MemberRepository2;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class MemberService2 implements UserDetailsService {
    private MemberRepository2 integrationRepository;

    public IntegrationEntity joinUser(IntegrationDto integrationDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        integrationDto.setUpwd(passwordEncoder.encode(integrationDto.getUpwd()));

        return integrationRepository.save(integrationDto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<IntegrationEntity> integrationEntityOptional = integrationRepository.findByName(username);
        IntegrationEntity integrationEntity = integrationEntityOptional.orElse(null);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(integrationEntity.getRoleKey()));

        return new CustomIntegrationDto(integrationEntity, integrationEntity.getName(), integrationEntity.getUpwd(), authorities);
    }

    public Long getMemberInfo(String username) {
        Optional<IntegrationEntity> integrationEntityOptional =  integrationRepository.findByName(username);
        IntegrationEntity integrationEntity = integrationEntityOptional.orElse(null);

        return integrationEntity != null ? integrationEntity.getId() : null;
    }

    public boolean verifyId(String name) {
        Optional<IntegrationEntity> integrationEntityOptional = integrationRepository.findByName(name);
        try{
            IntegrationEntity integrationEntity = integrationEntityOptional.get();
            if(integrationEntity != null) {
                return false;
            }
        } catch (NoSuchElementException nse) {
            return true;
        }
        return false;
    }

}
