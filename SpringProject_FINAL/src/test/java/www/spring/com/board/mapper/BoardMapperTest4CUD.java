package www.spring.com.board.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
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
public class BoardMapperTest4CUD {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testCreateBoard() {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("술");
		boardVO.setContent("술먹은 다음 해장법");
		boardVO.setWriter("코주부");
		
		boardMapper.insertBoard(boardVO);
		System.out.println(boardVO);
	}

	@Test
	public void testUpdateBoard() {
		BoardVO board = boardMapper.getBoard(1, BoardVO.MASTER_NAME);
		board.setTitle(board.getTitle() + "0");
		
		int cnt = boardMapper.updateBoard(board);
		System.out.println(cnt);
	}

	@Test
	public void testDeleteBoard() {
		BoardVO board = boardMapper.getBoardLatest();
		
		int cnt = boardMapper.deleteBoard(board.getBno());
		System.out.println(cnt);
	}
	
	@Test
	public void deleteBoardByColl() {
		List<BoardVO> listBoard = boardMapper.getBoardTwoLatest();
		List<Integer> listBno = new ArrayList<>();
		//listBoard에 담겨있는 모든 객체를 대상으로 반복 할 것입니다.
		//그 객체 각각을 board라고 이름하고 
		//그 후속 처리는 bno읽어서 listParam에 담습니다.
		listBoard.forEach(board -> listBno.add(board.getBno()));
		int cnt = boardMapper.deleteBoardByColl(listBno);
		System.out.println(cnt);
	}

}











