package com.record.travel.security.dto;

import java.io.Serializable;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IntegrationKakaoAndMember extends OAuth2User, Serializable, UserDetails, CredentialsContainer {

}
