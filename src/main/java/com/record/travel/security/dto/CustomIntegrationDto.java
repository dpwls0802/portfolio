package com.record.travel.security.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.record.travel.security.entity.IntegrationEntity;

import lombok.Getter;

@Getter
public class CustomIntegrationDto implements IntegrationKakaoAndMember{

    private Long id;
    private String customName;
    private String upwd;
    private DefaultOAuth2User defaultOAuth2User;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private final Set<GrantedAuthority> authorities;

    public CustomIntegrationDto(IntegrationEntity integrationEntity, 
    		String username, String password, 
    		Collection<? extends GrantedAuthority> authorities) {
        new User(username, password, authorities);
        this.customName = integrationEntity.getName();
        this.upwd = integrationEntity.getUpwd();
        this.id = integrationEntity.getId();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
    }
    

	private Set<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(Comparator.comparing(GrantedAuthority::getAuthority));
        sortedAuthorities.addAll(authorities);
        return sortedAuthorities;
    }

    @Override
    public void eraseCredentials() {
        this.upwd = null;
    }

    @Override
    public String getPassword() {
        return this.upwd;
    }

    @Override
    public String getUsername() {
        return customName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

}
