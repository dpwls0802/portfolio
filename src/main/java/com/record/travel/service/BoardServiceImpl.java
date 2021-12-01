package com.record.travel.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dao.BoardMapper;
import com.record.travel.dao.FileMapper;
import com.record.travel.dto.BoardDto;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BoardServiceImpl implements BoardService {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bMapper;
	@Setter(onMethod_ = @Autowired)
	private FileMapper fMapper;

	// 글 + 첨부파일 등록 = 트랜잭션 처리 필수
	@Transactional
	@Override
	public void write(BoardDto board) {
		log.info("게시글 등록 : " + board);
		bMapper.insertBoard(board); // 게시물 등록

		String[] files = board.getFiles();
		if (files == null)
			return;

		// 게시긇 첨부파일 입력차리
		for (String fileName : files) // board.getNum();
			fMapper.addFile(fileName);
	}

	// 글 수정 + 기존 첨부파일 정보 전체 삭제 + 첨부파일 정보 새로 입력
	@Transactional
	@Override
	public void modify(BoardDto board) {
		int num = board.getNum();
		String files[] = board.getFiles();

		bMapper.updateBoard(board); // 게시글 수정
		fMapper.deleteFiles(num); // 참부파일 전체 삭제

		if (files == null)
			return;
		for (String fileName : files)
			fMapper.modifyFile(fileName, num);
	}

	// 글 삭제 전, 첨부파일 먼저 삭제
	@Transactional
	@Override
	public void remove(int num) {
		fMapper.deleteFiles(num); // 첨부파일 삭제
		bMapper.deleteBoard(num); // 글 삭제
	}

	// 글 1개 조회
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BoardDto getBoard(int num) {
		bMapper.updateViewCnt(num);// 조회수 추가
		return bMapper.selectOneBoard(num);
	}

	// 글 목록 조회
	@Override
	public List<BoardDto> getBoardList() {
		return bMapper.selectAllBoard();
	}

	// 글 목록 조회 + 페이징
	@Override
	public List<BoardDto> getBoardList2(Criteria criteria) {
		return bMapper.listCriteria(criteria);
	}

	// 페이징 처리 위한 전체 게시글 개수 구하기
	@Override
	public int countBoards(Criteria criteria) {
		return bMapper.countBoards(criteria);
	}

	// 검섹
	@Override
	public List<BoardDto> getBoardSearchList(HashMap<String, Object> params) {
		return null;
	}

	// 페이징 기준
	/*
	 * @Override public List<BoardDto> listCriteria(Criteria criteria) { return
	 * bMapper.listCriteria(criteria); }
	 */

}
