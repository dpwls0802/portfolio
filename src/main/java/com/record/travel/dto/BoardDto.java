package com.record.travel.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto {
	private int num;
	private String title;
	private String content;
	private String writer;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private String travelDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date writeDate;
	
	private String tags; //해시태그 추가
	
	private int replyCnt; //댓글 갯수 추가
	private int viewCnt; //조회수 추가
	
	// 첨부파일 위한 추가
	private String[] files; //다수의 청부파일 저장
	private int fileCnt; //첨푸파일 개수
	
	private List<ImageDto> imageList = new ArrayList<>();
}
