package com.record.travel;

import java.util.List;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dto.BoardDto;
import com.record.travel.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PagingApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService bService;

	// 페이징 기본
	// @Test
	/*
	 * public void testListPaging() { //BoardDto board = new BoardDto();
	 * 
	 * int page = 3;
	 * 
	 * List<BoardDto> boards = bService.listPaging(page);
	 * 
	 * for(BoardDto board : boards) { logger.info(board.getNum()+""); } }
	 */

	// 페이징 조건
	//@Test
	public void testCriteria() {
		Criteria criteria = new Criteria();
		criteria.setPage(3);
		criteria.setPerPageNum(8);

		List<BoardDto> boards = bService.getBoardList2(criteria);

		for (BoardDto board : boards) {
			logger.info(board.getNum() + "");
		}

	}
}
