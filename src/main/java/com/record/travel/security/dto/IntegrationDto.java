package com.record.travel.security.dto;

import java.time.LocalDateTime;

import com.record.travel.security.entity.IntegrationEntity;
import com.record.travel.security.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IntegrationDto {
    //가제

    private Long id;
    private String name;
    private String email;
    private String upwd;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public IntegrationEntity toEntity() {
        return IntegrationEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .upwd(upwd)
                .role(Role.MEMBER)
                .build();
    }

    public IntegrationEntity toAdminEntity() {
        return IntegrationEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .upwd(upwd)
                .role(Role.ADMIN)
                .build();
    }


    @Builder
    public IntegrationDto(Long id, String name, String email, String upwd, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.upwd = upwd;
        this.role = role;
    }
}