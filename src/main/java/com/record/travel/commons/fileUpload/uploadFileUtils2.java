package com.record.travel.commons.fileUpload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

//파일 업로드, 삭제 , 전송, 폴더생성, 파일명 중복방지 등 기능 처리 메서드
public class uploadFileUtils2 {

		private static Logger logger = LoggerFactory.getLogger(uploadFileUtils2.class);

		/////////////////////파일 업로드 차리//////////////////////////
		public static String uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
			String originalFileName = file.getOriginalFilename(); // 파일명
			byte[] fileData = file.getBytes(); //파일 데이터
			//String rootPath ="/resources/upload"; //파일 저장 위치
			
			// 1. 파일명 중복 방지 처리
			// 중복된 파일명 저장을 하지 않기 위해 UUID 키값을 생성
		    UUID uuid = UUID.randomUUID();
		    // 저장파일명 = UUID + _ + 원본파일명
			String uuidFileName = uuid.toString() + "_" + originalFileName;
			
			// 2. 파일 업로드 경로 설정
			String rootPath = getRootPath(originalFileName, request); //기본경로 추출(이미지 or 일반파일)
			String datePath = getDatePath(rootPath); //날짜 경로 추출, 날짜 폴더 생성
			
			// 3. 서버에 파일 저장
			File target = new File(rootPath + datePath, uuidFileName); //파일 객체 생성
			FileCopyUtils.copy(fileData, target); //파일 객체에 파일 데이터 복사
			
			// 4. 이미지 파일인 경우 썸네일 이미지 생성
			if(MediaUtils.getMediaType(originalFileName) != null) {
				uuidFileName = makeThumbnail(rootPath, datePath, uuidFileName);
			}
			
			// 5. 파일 저장 경로 치환
			return replaceSavedFilePath(datePath, uuidFileName);
			
		}
		
		//////////////////////파일 삭제 처리////////////////////////////////
		public static void deleteFile(String fileName, HttpServletRequest request) {
			String rootPath = getRootPath(fileName, request); //기본경로 추출(이미지 or 일반파일)
			
			// 1. 원본 이미지 파일 삭제
			MediaType mediaType = MediaUtils.getMediaType(fileName); //파일타입 확인
			if(mediaType != null) {
				String originalImg = fileName.substring(0, 12) + fileName.substring(14);
				new File(rootPath + originalImg.replace('/', File.separatorChar)).delete();
			}
			
			// 2. 파일 삭제(섬네일이미지 or 일반파일)
			new File(rootPath + fileName.replace('/', File.separatorChar)).delete();
		}
		
		

		// 파일 출력 위한 httpheader 설정
		public static HttpHeaders getHttpHeaders(String fileName) throws Exception {
			MediaType mediaType = MediaUtils.getMediaType(fileName); //파일타입 확인
			HttpHeaders httpHeaders = new HttpHeaders();
			
			//이미지 파일 있으면
			if(mediaType != null) {
				httpHeaders.setContentType(mediaType);
				
				return httpHeaders;
			}
			
			//이미지 파일 아니면
			fileName = fileName.substring(fileName.indexOf("_")+1); //uuid 제거
			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM); //다운로드 mime 타입 설정
			httpHeaders.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),
					"ISO-8859-1")+"\"");
			return httpHeaders;
		}
		
		//기본 경로 추출
		public static String getRootPath(String fileName, HttpServletRequest request) {
			String rootPath ="/resources/upload";
			//String rootPath ="C:\\upload";
			MediaType mediaType = MediaUtils.getMediaType(fileName); //파일타입 확인
			if(mediaType != null)
				//이미지 파일 경로
				return request.getSession().getServletContext().getRealPath(rootPath+"/images");

			//일반 파일 경로
			return request.getSession().getServletContext().getRealPath(rootPath+"/files");
		}
		
		//날짜 폴더명 추출
		private static String getDatePath(String uploadPath) {
			Calendar calendar = Calendar.getInstance();
			String yearPath = File.separator + calendar.get(Calendar.YEAR);
			String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH)+1);
			String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));
			
			makeDateDir(uploadPath, yearPath, monthPath, datePath);
			
			return datePath;
		
		}
		
		// 날짜별 폴더 생성
		private static void makeDateDir(String uploadPath, String... paths) {
			//날짜별 폴더가 이미 존재하면 메서드 종료
			if(new File(uploadPath + paths[paths.length-1]).exists())
				return;
			
			for(String path : paths) {
				File dirPath = new File(uploadPath + path);
				if(!dirPath.exists())
					dirPath.mkdir();
			}
		}
		
		//파일 저장 경로 치환
		private static String replaceSavedFilePath(String datePath, String fileName) {
			String savedFilePath = datePath + File.separator + fileName;
			return savedFilePath.replace(File.separatorChar, '/');
		}
		
		
		//섬네일 이미지 생성
		private static String makeThumbnail(String uploadRootPath, String datePath, String fileName) throws Exception {
			//원본 이미지를 메모리상에 로딩
			BufferedImage originalImg = ImageIO.read(new File(uploadRootPath + datePath, fileName));
			//원본 이미지 축소
			BufferedImage thumbnailImg = Scalr.resize(originalImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 200);
			//섬네일 파일명
			String thumbnailImgName = "s_" + fileName;
			//섬네일 업로드 경로
			String fullPath = uploadRootPath + datePath + File.separator + thumbnailImgName;
			//섬네일 파일 객체 생성
			File newFile = new File(fullPath);
			//섬네일 파일 확장자 추출
			String formatName = MediaUtils.getFormatName(fileName);
			//섬네일 파일 저장
			ImageIO.write(thumbnailImg, formatName, newFile);
			
			return thumbnailImgName;
		}
		
		
		
		
}
