package com.record.travel.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "member2")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntegrationEntity extends BaseEntity {
    //가제

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true, length = 200)
    private String upwd;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Builder
    public IntegrationEntity(Long id, String email, String name, String upwd, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.upwd = upwd;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public IntegrationEntity update(String name, String email) {
        this.name = name;
        this.email = email;

        return this;
    }
}
