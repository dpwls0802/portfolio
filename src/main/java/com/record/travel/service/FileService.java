package com.record.travel.service;

import java.util.List;

public interface FileService {
	//public void add(String filelName); //파일 업로드
	public List<String> get(int num); // 파일 목록
	//public void deletes(int num); //게시글 첨부파일 전체 삭제
	public void delete(String fileName, int num); //첨부파일 삭제
	public void modify(String fileName, int num); //첨부파일 수정
	//public void updateFileCnt(int num); //첨부파일 개수 갱신
	
	public String getOne(int num); //목록 중 한개만
	 
	
}
