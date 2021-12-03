package com.record.travel.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.record.travel.commons.fileUpload.uploadFileUtils2;
import com.record.travel.service.FileService;

@RestController
@RequestMapping("/blog/file")
public class FileController { // 게시글이 입력되기 전에 클라이언트로부터 AJAX 통신을 통해 첨부파일을 미리 서버에 저장하는 역할

	@Autowired
	FileService fileService;

	// 게시글 파일 업로드
	@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) {
        ResponseEntity<String> entity = null;
        try {
            String savedFilePath = uploadFileUtils2.uploadFile(file, request);
            entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 게시글 파일 출력
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ResponseEntity<byte[]> displayFile(String fileName, HttpServletRequest request) throws Exception {

        HttpHeaders httpHeaders = uploadFileUtils2.getHttpHeaders(fileName); // Http 헤더 설정 가져오기
        String rootPath = uploadFileUtils2.getRootPath(fileName, request); // 업로드 기본경로 경로

        ResponseEntity<byte[]> entity = null;

        // 파일데이터, HttpHeader 전송
        try (InputStream inputStream = new FileInputStream(rootPath + fileName)) {
            entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
    
    // 게시글 첨부 파일 한개(211119 추가)
    @RequestMapping(value = "/list2/{num}", method = RequestMethod.GET)
    public ResponseEntity<String> getFile(@PathVariable("num") Integer num) {
        ResponseEntity<String> entity = null;
        try {
            String file = fileService.getOne(num);
            entity = new ResponseEntity<>(file, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
    
    // 게시글 첨부 파일 목록
    @RequestMapping(value = "/list/{num}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getFiles(@PathVariable("num") Integer num) {
        ResponseEntity<List<String>> entity = null;
        try {
            List<String> fileList = fileService.get(num);
            entity = new ResponseEntity<>(fileList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 게시글 파일 삭제 : 게시글 작성
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(String fileName, HttpServletRequest request) {
        ResponseEntity<String> entity = null;

        try {
        	uploadFileUtils2.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    // 게시글 파일 삭제 : 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/delete/{num}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(@PathVariable("num") Integer num,
                                             String fileName,
                                             HttpServletRequest request) {
        ResponseEntity<String> entity = null;

        try {
        	uploadFileUtils2.deleteFile(fileName, request);
        	fileService.delete(fileName, num);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    // 게시글 파일 전체 삭제
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAllFiles(@RequestParam("files[]") String[] files, HttpServletRequest request) {

        if (files == null || files.length == 0)
            return new ResponseEntity<>("DELETED", HttpStatus.OK);

        ResponseEntity<String> entity = null;

        try {
            for (String fileName : files)
            	uploadFileUtils2.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

}
