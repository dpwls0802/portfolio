package com.record.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
	void addFile(String fileName); //파일 업로드
	public List<String> getFiles(int num); // 파일 목록
	public void deleteFiles(int num); //게시글 첨부파일 전체 삭제
	public void deleteFile(String fileName); //첨부파일 삭제
	public void modifyFile(String fileName, int num); //첨부파일 수정
	public void updateFileCnt(int num); //첨부파일 개수 갱신
	public void modifyFile(Map<String, Object> paramMap);
	
	public String getOneFile(int num); //한개만 추출
	
	
}
