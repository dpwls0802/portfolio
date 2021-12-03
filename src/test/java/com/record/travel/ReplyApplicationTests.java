package com.record.travel;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.dto.ReplyDto;
import com.record.travel.service.ReplyService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReplyApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReplyService rService;

	// 댓글 조회
	// @Test
	public void testGetReply() {
		int targetRnum = 5;
		ReplyDto reply = rService.get(targetRnum);

		logger.info(reply + "");
	}

	// 댓글 등록
	// @Test
	public void testReplyCreate() {
		for (int i = 1; i <= 100; i++) {
			ReplyDto reply = new ReplyDto();
			reply.setNum(100);
			reply.setReplyText(i + "번재 댓글");
			reply.setReplyWriter("user" + (i % 10));

			rService.write(reply);
		}
	}

	@Test
	public void testReplyCreate2() {
		
			ReplyDto reply = new ReplyDto();
			reply.setNum(99);
			reply.setReplyText("댓글");
			reply.setReplyWriter("댓글user");

			rService.write(reply);
		
	}

	// 댓글 목록
	// @Test
	public void testReplyList() {
		logger.info(rService.getReplyList(100).toString());
	}

	// 댓글 수정
	// @Test
	public void testReplyUpdate() {
		ReplyDto reply = new ReplyDto();
		reply.setNum(2);
		reply.setReplyText(2 + "번재 댓글 수정!");

		rService.modify(reply);
	}

	// 댓글 삭제
	// @Test
	public void testReplyDelete() {
		rService.remove(3);
	}

	// 댓글 페이징
	// @Test
	public void testReplyPaging() {
		Criteria criteria = new Criteria();
		criteria.setPerPageNum(8); // 페이지당 출력되는 개수
		criteria.setPage(1); // 현재 페이지

		List<ReplyDto> replies = rService.getPagingReply(100, criteria);

		for (ReplyDto reply : replies) {
			logger.info(reply.getRnum() + " : " + reply.getReplyText());
		}
	}

}
