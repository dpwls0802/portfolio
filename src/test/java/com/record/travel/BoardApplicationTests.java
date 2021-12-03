package com.record.travel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.record.travel.dto.BoardDto;
import com.record.travel.dto.ReplyDto;
import com.record.travel.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BoardApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService bService;

	// 글 등록
	//@Test
	public void testBoardreate() {
		for (int i = 1; i <= 100; i++) {
			BoardDto board = new BoardDto();
			board.setTitle(i + "번쨰 나라");
			board.setContent(i + "번째 내용 ");
			board.setTravelDate(i+"번째 여행기간");
			board.setWriter("user" + (i % 10));

			bService.write(board);
		}
	}
	
	//글 조회
	@Test
	public void testGetBoard() {
		logger.info(bService.getBoard(2).toString());
	}
}
