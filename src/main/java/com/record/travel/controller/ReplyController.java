package com.record.travel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.commons.paging.PageMaker;
import com.record.travel.dto.ReplyDto;
import com.record.travel.service.ReplyService;

import lombok.Setter;

@Setter
@RestController
@RequestMapping("/reply/*")
public class ReplyController {

	@Autowired
	ReplyService replyService;

	// 댓글 조회
	@RequestMapping(value = "/getReply/{rnum}", /*
												 * produces= {MediaType.APPLICATION_XML_VALUE,
												 * MediaType.APPLICATION_JSON_VALUE},
												 */
			method = RequestMethod.GET)
	public ResponseEntity<ReplyDto> get(@PathVariable("rnum") int rnum) {
		return new ResponseEntity<>(replyService.get(rnum), HttpStatus.OK);
	}

	// 댓글 목록
	@RequestMapping(value = "/replyList/{num}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyDto>> getReplyList(@PathVariable("num") int num) {
		ResponseEntity<List<ReplyDto>> entity = null;

		try {
			entity = new ResponseEntity<>(replyService.getReplyList(num), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 댓글 등록
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/writeReply", method = RequestMethod.POST)
	public ResponseEntity<String> writeReply(@RequestBody ReplyDto reply) {
		ResponseEntity<String> entity = null;

		try {
			replyService.write(reply);
			entity = new ResponseEntity<>("writeSuccess", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 댓글 수정
	@PreAuthorize("principal.customName == #reply.replyWriter")
	@RequestMapping(value = "/{rnum}", method = { RequestMethod.PUT, RequestMethod.PATCH })
	public ResponseEntity<String> modify(@PathVariable("rnum") int rnum, @RequestBody ReplyDto reply) {
		ResponseEntity<String> entity = null;

		try {
			reply.setRnum(rnum);
			replyService.modify(reply);
			entity = new ResponseEntity<>("modifySuccess", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 댓글 삭제
	@PreAuthorize("principal.customName == #reply.replyWriter")
	@RequestMapping(value = "/{rnum}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rnum") int rnum) {
		ResponseEntity<String> entity = null;

		try {
			replyService.remove(rnum);
			entity = new ResponseEntity<>("removeSuccess", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 댓글 페이징

	/*
	 * @RequestMapping(value = "/replyListPaging/{num}/{page}", method =
	 * RequestMethod.GET) public ResponseEntity<Map<String, Object>>
	 * pagingReply(@PathVariable("num") int num,
	 * 
	 * @PathVariable("page") int page) { ResponseEntity<Map<String, Object>> entity
	 * = null;
	 * 
	 * try { Criteria criteria = new Criteria(); criteria.setPage(page);
	 * 
	 * List<ReplyDto> replies = replyService.getPagingReply(num, criteria); int
	 * repliesCount = replyService.countReply(num);
	 * 
	 * PageMaker pageMaker = new PageMaker(); pageMaker.setCriteria(criteria);
	 * pageMaker.setTotalCnt(repliesCount);
	 * 
	 * Map<String, Object> map = new HashMap<>(); map.put("replies", replies);
	 * map.put("pageMaker", pageMaker);
	 * 
	 * entity = new ResponseEntity<>(map, HttpStatus.OK); } catch (Exception e) {
	 * e.printStackTrace(); entity = new ResponseEntity<>(HttpStatus.OK); } return
	 * entity; }
	 */

	// 댓글 페이징

	@RequestMapping(value = "/replyListPaging/{num}/{page}", /*
																 * produces = { MediaType.APPLICATION_XML_VALUE,
																 * MediaType.APPLICATION_JSON_VALUE },
																 */ method = RequestMethod.GET)
	public ResponseEntity<List<ReplyDto>> getPagingReply(@PathVariable("num") int num, @PathVariable("page") int page) {
		Criteria criteria = new Criteria(page, 8);

		return new ResponseEntity<>(replyService.getPagingReply(num, criteria), HttpStatus.OK);
	}

}
