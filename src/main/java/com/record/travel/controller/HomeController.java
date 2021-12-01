package com.record.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.record.travel.commons.paging.Criteria;
import com.record.travel.service.BoardService;

@Controller
public class HomeController {
	@Autowired
	BoardService boardService;
	
	
	//메인
	@RequestMapping("/")
	public String home1(Model model) {
		model.addAttribute("listPost", boardService.getBoardList());
		
		return "home1";
	}
	
	@RequestMapping(value = "/home2")
	public String index() {
		return "index";
	}

	@RequestMapping("/2")
	public String index2() {
		return "index2";
	}
	
	@RequestMapping(value="/index3", method = RequestMethod.GET)
	public String index3(Model model)  throws Exception {
		model.addAttribute("listPost", boardService.getBoardList());
		//model.addAttribute("board", boardService.getBoard(num));
		
		return "/index3";
	}
	
}
