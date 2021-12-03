package com.record.travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/*
	 * @Bean public HiddenHttpMethodFilter httpMethodFilter() {
	 * HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
	 * return hiddenHttpMethodFilter; }
	 * 
	 * @Bean public CommonsMultipartResolver multipartResolver() {
	 * CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	 * multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
	 * multipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024); // 파일당 업로드 크기 제한
	 * (5MB) return multipartResolver; }
	 */

}
