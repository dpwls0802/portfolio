package com.record.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.commons.paging.PageMaker;
import com.record.travel.dto.BoardDto;
import com.record.travel.service.BoardService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/blog/*")
@Log4j2
public class BoardController {
	@Autowired
	BoardService boardService;

	// 게시글 목록
	/*
	 * @RequestMapping(value = "/listPost", method = RequestMethod.GET) public
	 * String listPost(Model model) throws Exception {
	 * model.addAttribute("listPost", boardService.getBoardList());
	 * 
	 * return "/blog/listPost"; }
	 */

	// 게시글 목록 + 페이징
	@RequestMapping(value = "/listPost", method = RequestMethod.GET)
	public String listPost(Criteria criteria, Model model) throws Exception {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		// pageMaker.setTotalCnt(101);
		// 페이징 처리 위한 전체 게시글 개수
		pageMaker.setTotalCnt(boardService.countBoards(criteria));

		model.addAttribute("listPost", boardService.getBoardList2(criteria));
		model.addAttribute("pageMaker", pageMaker);

		return "/blog/listPost";
	}

	// 게시글 작성 페이지 이동
	@RequestMapping(value = "/writePost", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String writePost() {

		return "/blog/writePost";
	}

	// 게시글 작성 처리(이전)
	@RequestMapping(value = "/writePost", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String writePost(BoardDto board, RedirectAttributes redirectAttributes) throws Exception {
		log.info("=====================");
		log.info("등록 : " + board);

		boardService.write(board);
		redirectAttributes.addFlashAttribute("result", "writeSuccess");

		return "redirect:/blog/listPost";
	}


	// 게시글 상세 페이지 이동
	@RequestMapping(value = "/detailPost", method = RequestMethod.GET)
	public String detailPost(@RequestParam("num") int num, @ModelAttribute("criteria") Criteria critaria, Model model)
			throws Exception {
		model.addAttribute("board", boardService.getBoard(num));

		return "/blog/detailPost";
	}

	// 게시글 수정 페이지 이동
	@RequestMapping(value = "/modifyPost", method = RequestMethod.GET)
	public String modifyPost(@RequestParam("num") int num, @ModelAttribute("criteria") Criteria critaria, Model model)
			throws Exception {
		model.addAttribute("board", boardService.getBoard(num));

		return "/blog/modifyPost";
	}

	// 게시글 수정 처리
	@PreAuthorize("principal.customName == #board.writer")
	@RequestMapping(value = "/modifyPost", method = RequestMethod.POST)
	public String modifyPost(BoardDto board, @ModelAttribute("criteria") Criteria critaria,
			RedirectAttributes redirectAttributes) throws Exception {
		boardService.modify(board);
		redirectAttributes.addFlashAttribute("result", "modifySuccess");

		// 페이징
		redirectAttributes.addAttribute("page", critaria.getPage());
		redirectAttributes.addAttribute("perPageNum", critaria.getPerPageNum());

		return "redirect:/blog/listPost";
	}

	// 게시글 삭제 처리
	@PreAuthorize("principal.customName == #writer")
	@RequestMapping(value = "/removePost", method = RequestMethod.POST)
	public String removePost(@RequestParam("num") int num, @ModelAttribute("criteria") Criteria critaria,
			RedirectAttributes redirectAttributes) throws Exception {

		boardService.remove(num);
		// 페이징
		redirectAttributes.addAttribute("page", critaria.getPage());
		redirectAttributes.addAttribute("perPageNum", critaria.getPerPageNum());

		return "redirect:/blog/listPost";
	}

}
