package www.spring.com.board.mapper;

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
public class BoardMapperTest4All {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testListAllBoard() {
		List<BoardVO> list = boardMapper.getAllBoard();
		for (BoardVO board : list) {
			System.out.println(board);
			log.info(board);
		}
	}
}











