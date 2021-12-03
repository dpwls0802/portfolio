package com.record.travel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dao.BoardMapper;
import com.record.travel.dao.ReplyMapper;
import com.record.travel.dto.ReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper rMapper;
	
	@Autowired
	private BoardMapper bMapper;
	
	//댓글 조회
	@Override
	public ReplyDto get(int rnum) {
		return rMapper.readReply(rnum);
	}
	
	//댓글 목록 조회
	@Override
	public List<ReplyDto> getReplyList(int num) {
		return rMapper.selectAllReply(num);
	}
	
	//댓글 등록
	@Transactional
	@Override
	public void write(ReplyDto reply) {
		rMapper.insertReply(reply);
		bMapper.updateReplyCnt(reply.getNum(), 1);
		
	}
	
	//댓글 수정
	@Override
	public void modify(ReplyDto reply) {
		rMapper.updateReply(reply);
	}
	
	//댓글 삭제
	@Transactional
	@Override
	public void remove(int rnum) {
		ReplyDto reply = rMapper.readReply(rnum);
		bMapper.updateReplyCnt(reply.getNum(), -1); //댓글 개수 감소
		rMapper.deleteReply(rnum); //댓글 삭제 후
		
	}
	
	
	////페이징
	@Override
	public List<ReplyDto> getPagingReply(int num, Criteria criteria) {
		/*
		 * Map<String, Object> paramMap = new HashMap<>(); paramMap.put("num", num);
		 * paramMap.put("criteria", criteria);
		 */
		return rMapper.pagingReply(num, criteria);
	}
	@Override
	public int countReply(int num) {
		return rMapper.countReply(num);
	}
	
	
	

}
