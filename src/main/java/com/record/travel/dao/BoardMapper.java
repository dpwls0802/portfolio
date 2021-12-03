package com.record.travel.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dto.BoardDto;

public interface BoardMapper {
    public void insertBoard (BoardDto board);
    public int updateBoard (BoardDto board);
    public int deleteBoard (int num);
    public BoardDto selectOneBoard (int num);
    public List<BoardDto> selectSearchBoard (HashMap<String, Object> params);
    public List<BoardDto> selectAllBoard();
    
    //페이징
    //public List<BoardDto> listPaging(int page);
    public List<BoardDto> listCriteria(Criteria criteria);
    
    //페이징 처리 위한 전체 게시글 개수 구하기
    public int countBoards(Criteria criteria);
    
    //댓글 수 추가
    public void updateReplyCnt(@Param("num") int num, @Param("amount") int amount);
    //조회 수 추가
    public void updateViewCnt(int num);

    
}

