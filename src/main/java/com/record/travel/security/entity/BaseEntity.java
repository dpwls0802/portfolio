package com.record.travel.security.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass //해당 클래스는 테이블로 생성이 안됨. 이 클래스 상속한 엔티티 클래스로 테이블 생성됨.
@EntityListeners(value= {AuditingEntityListener.class})//엔티티 객체가 생성/변경되늰 것 감지하는 역할
@Getter
public class BaseEntity {
	
	@CreatedDate
	@Column(name="regdate", updatable = false) //jpa에서 엔티티 생성 시간 처리, 객체  db에 반영시 regdate값은 변경되지 않음
	private LocalDateTime regDate; //데이터 등록 시간
	
	@LastModifiedDate
	@Column(name="moddate")
	private LocalDateTime modDate;

}
