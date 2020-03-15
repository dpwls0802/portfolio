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
public class ReplyMapperTest4CUD {
	@Autowired
	private ReplyMapper replyMapper;
	
	@Test
	public void test11Insert() {
		ReplyVO replyVO = new ReplyVO(1310751, "필림 끊길때 까지는 말이야...", "이태백");
		replyMapper.insertReply(replyVO);
		
		System.out.println(replyVO);
	}

	@Test
	public void test11UpdateReply() {
		try {
			Integer updateTarget = replyMapper.getOldestReply();
			if (updateTarget != null) {
				ReplyVO replyVO = replyMapper.getReply(updateTarget);
				replyVO.setContent(replyVO.getContent() + 0);
				replyMapper.updateReply(replyVO);

				System.out.println("댓글 " + updateTarget + "을 변경하였습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}











