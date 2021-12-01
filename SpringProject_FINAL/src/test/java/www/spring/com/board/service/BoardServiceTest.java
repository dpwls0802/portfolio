package www.spring.com.board.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import www.spring.com.board.model.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {
	@Autowired
	private BoardService boardService;
	
	@Test
	public void testCreateBoard() {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("술2");
		boardVO.setContent("술먹은 다음 해장법2");
		boardVO.setWriter("코주부");
		
		boardService.insertBoard(boardVO);
		System.out.println(boardVO);
	}

	@Test
	public void testListAllBoard() {
		List<BoardVO> list = boardService.getAllBoard();
		for (BoardVO board : list) {
			System.out.println(board);
			log.info(board);
		}
	}

}











