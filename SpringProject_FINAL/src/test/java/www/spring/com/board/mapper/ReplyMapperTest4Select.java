package www.spring.com.board.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import www.spring.com.board.model.ReplyVO;
import www.spring.com.framework.PageDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest4Select {
	@Autowired
	private ReplyMapper replyMapper;
	
	@Test
	public void testMapper() {
		System.out.println(replyMapper);
	}

	@Test
	public void testSingleSelect() {
		ReplyVO replyVO = replyMapper.getReply(1);
		
		System.out.println("SingleSelect에서 처리 중...");
		System.out.println(replyVO);
	}

	@Test
	public void testListReplyOfBoard() {
		List<ReplyVO> list = replyMapper.getListReplyOfBoard(1310751);
		
		System.out.println("ListReplyOfBoard에서 처리 중...");
		list.forEach(obj->System.out.println(obj));
	}

	@Test
	public void testPagingReplyOfBoard() {
		PageDTO cri = new PageDTO();
		Integer bno = replyMapper.getOldestBoardIdHavingReply();
		
		System.out.println("testPagingReplyOfBoard에서 처리 중...");
		if (bno != null) {
			List<ReplyVO> list = replyMapper.getPagingReplyOfBoard(cri, bno);
			list.forEach(obj->System.out.println(obj));
		}
	}
}











