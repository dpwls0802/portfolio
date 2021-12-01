package com.record.travel.x;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import com.record.travel.security.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity{
	
	@Id
	private String email;
	private String password;
	private String name;
	private boolean fromSocial;
	
	@ElementCollection(fetch = FetchType.LAZY) //멤버 권한 처리 위해
	@Builder.Default
	private Set<MemberRole> roleSet = new HashSet<>();
	
	public void addMemberRole(MemberRole memberRole) {
		roleSet.add(memberRole);
	}
	
}
