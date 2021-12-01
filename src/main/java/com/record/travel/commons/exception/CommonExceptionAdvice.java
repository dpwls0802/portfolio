package com.record.travel.commons.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionAdvice {
	
	//예외 처리
	@ExceptionHandler(Exception.class)
	public ModelAndView commonException(Exception e) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", e);
		modelAndView.setViewName("/commons/common_error");
		
		return modelAndView;
	}
}
