package www.spring.com.board.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import www.spring.com.board.model.BoardVO;
import www.spring.com.board.model.criteria.BoardCriteria;
import www.spring.com.framework.PageDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTest4SimpleSelect {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testListWithPaging() {
		List<BoardVO> list = boardMapper.getListWithPaging(new PageDTO(5, 15));
		list.forEach(board -> System.out.println(board));
	}

	@Test
	public void testListWithPagingByCondition() {
		try {
			BoardCriteria cri = new BoardCriteria();
			//페이징 만으로 조회
			List<BoardVO> list = boardMapper.getListWithPagingByCondition(cri);
			list.forEach(board -> System.out.println(board));
			
			//한가지 조건으로만 조회
			cri.setSearchType("T");
			cri.setKeyword("술");
			list = boardMapper.getListWithPagingByCondition(cri);
			list.forEach(board -> System.out.println(board));
			//두가지 조건으로 조회
			cri.setSearchType("TC");
			list = boardMapper.getListWithPagingByCondition(cri);
			list.forEach(board -> System.out.println(board));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindBoard() {
		BoardVO board = boardMapper.getBoard(1310782, BoardVO.MASTER_NAME);
		System.out.println(board);
		log.info(board);
		BoardVO board2 = boardMapper.getBoard(13107, BoardVO.MASTER_NAME);
		System.out.println(board);
		log.info(board);
	}
}











