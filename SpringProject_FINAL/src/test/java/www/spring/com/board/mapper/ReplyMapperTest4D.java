package www.spring.com.board.mapper;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import www.spring.com.board.model.ReplyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyMapperTest4D {
	@Autowired
	private ReplyMapper replyMapper;
	
	@Test
	public void test2Delete() {
		try {
			Integer delTarget = replyMapper.getOldestReply();
			if (delTarget != null)
				replyMapper.deleteReply(delTarget);
			
			System.out.println("댓글 " + delTarget + "을 삭제하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test3DeleteReplyOfBoard() {
		Integer delTargetBNO = replyMapper.getOldestBoardIdHavingReply();
		if (delTargetBNO != null) {
			int delCnt = replyMapper.deleteReplyOfBoard(delTargetBNO);
			System.out.println(delTargetBNO + "글에 달린 댓글 " + delCnt + "개를 삭제하였습니다.");
		}
	}

}











