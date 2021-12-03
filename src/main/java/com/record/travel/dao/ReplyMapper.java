package com.record.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dto.ReplyDto;

public interface ReplyMapper {
	
	public ReplyDto readReply (int rnum); //조회
	public List<ReplyDto> selectAllReply(int num); //목록
	public void insertReply (ReplyDto reply); //등록
    public void updateReply (ReplyDto reply); //수정
    public void deleteReply (int rnum); //삭제
    
    //페이징
    public List<ReplyDto> pagingReply(@Param("num") int num, 
    		@Param("criteria") Criteria criteria);
   
	//public List<ReplyDto> pagingReply(Map<String, Object> paramMap);
	
	 public int countReply(int num);
}
