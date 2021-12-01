package com.record.travel.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDto {
	private String fileName;
	private String uuid;
	private String path;
	
	public String getImageURL() {
		try {
			return URLEncoder.encode(path+"/"+uuid+"_"+fileName,"UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getThumnailURL() {
		try {
			return URLEncoder.encode(path+"/s_"+uuid+"_"+fileName,"UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
