package com.record.travel.service;

import java.util.HashMap;
import java.util.List;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dto.BoardDto;

public interface BoardService {
	public void write(BoardDto board);

	public void modify(BoardDto board);

	public void remove(int num);

	public BoardDto getBoard(int num);

	public List<BoardDto> getBoardList();

	public List<BoardDto> getBoardSearchList(HashMap<String, Object> params);

	// 페이징
	// public List<BoardDto> listPaging(int page);

	// 글 목록 + 페이징
	public List<BoardDto> getBoardList2(Criteria criteria);

	// 페이징 처리 위한 전체 게시글 개수 구하기
	public int countBoards(Criteria criteria);

	
}
