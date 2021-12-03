package com.record.travel.service;

import java.util.List;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dto.ReplyDto;

public interface ReplyService {
	public ReplyDto get(int rnum); //조회
	public List<ReplyDto> getReplyList(int num); //목록
	public void write(ReplyDto reply); //등록
	public void modify(ReplyDto reply); //수정
	public void remove(int rnum); //삭제

	//페이징
	public List<ReplyDto> getPagingReply(int num, Criteria criteria);
	public int countReply(int num);
}
