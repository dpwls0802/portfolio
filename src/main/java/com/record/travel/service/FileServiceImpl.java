package com.record.travel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.record.travel.dao.FileMapper;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileMapper fMapper;
	

	@Override
	public List<String> get(int num) {
		return fMapper.getFiles(num);
	}

	
	@Transactional
	@Override
	public void delete(String fileName, int num) {
		fMapper.deleteFile(fileName);
		fMapper.updateFileCnt(num);
	}
	
	@Override
	public void modify(String fileName, int num) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fileName", fileName);
		paramMap.put("num", num);
		
		fMapper.modifyFile(paramMap);
		
	}


	@Override
	public String getOne(int num) {
		return fMapper.getOneFile(num);
	}

	
}
