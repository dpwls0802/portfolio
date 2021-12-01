package www.spring.com.framework.fileupload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;
import www.spring.com.framework.fileupload.model.AttachVO;
import www.spring.com.framework.fileupload.service.AttachService;

@Controller
@RequestMapping("/fileUpload/*")
public class UploadController {
	public static final String UPLOAD_FOLDER = "C:\\was_upload";
	public static final String THUMBNAIL_FILE_PREFIX = "s_";
	
	@Autowired
	private AttachService attachService;

	@GetMapping("uploadAjax")  //uploadAjax.jsp로 전환
	public void uploadAjax() {
	}
	
	//첨부파일 올리기
	@PreAuthorize("isAuthenticated()")
	//테이블에 반영하지 않습니다. 폴더에 파일을 올리기만 합니다.
	@PostMapping(value="uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) { 
		List<AttachVO> retList = new ArrayList<>();  //AttachVO의 리스트 반환
		
		//하루에 하나씩의 업로드용 폴더 검사하여 없으면 만들기(중복된 이름의 첨부파일 처리를 위해서) 
		String uploadFolder = UPLOAD_FOLDER;
		String strUploadPath = getFolderName(new Date());
		File uploadPath = new File(uploadFolder, strUploadPath);
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		// yyyy/MM/dd 폴더 생성(파일 저장시 자동으로 폴더 생성)
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachVO attachVO = new AttachVO();
			attachVO.setUploadPath(strUploadPath);
			attachVO.setShowBackFileName(strUploadPath);
			
			String uploadFileFullName = multipartFile.getOriginalFilename(); //업로드 되는 파일 이름

			String uploadFileName = uploadFileFullName.substring(
					uploadFileFullName.lastIndexOf("\\") + 1);
			
			attachVO.setFileName(uploadFileName);
			
			//파일 이름 중복방지 위한 UUID 적용
			//c:\\a\b\c.xx   -> c.xx
			String uuid = UUID.randomUUID().toString();//임의의 값 생성
			
			attachVO.setUuid(uuid);
			
			String uuidFileName = uuid + "_" + uploadFileName;
			File saveFile = new File(uploadPath, uuidFileName); //uuid적용한 파일 폴더에 저장
			
			//저장한 파일이 이미지 파일이면 섬네일 이미지 생성한다.
			try {
				//사용자 지정한 해당 파일을 저장한다.
				multipartFile.transferTo(saveFile);
				String savedFileName = makeThumbnail(uploadPath, multipartFile, 
						uuidFileName, saveFile, attachVO);
				
				attachVO.appendShowBackFileName(savedFileName);
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			retList.add(attachVO); //여러개 파일 리스트로 attachVO에 추가
		}
		return new ResponseEntity<>(retList, HttpStatus.OK);
	}

	/**
	 * @param fileName 예를들어 2019/12/04/s_sdfrghasdfrgqwe7r65248rtfbhasef_Poodle.png
	 * @return
	 */
	//섬네일 데이터 전송
	@GetMapping("display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) { //문자열로 파일의 경로가 포함된 fileName을 파라미터로 받고 byte[] 전송
		ResponseEntity<byte[]> ret = null;
		
		File file = new File(UPLOAD_FOLDER + File.separatorChar + fileName);
		
		//byte로 전송할 때 브라우저에 보내주는 MIME 타입이 파일의 종류에 따라 달라짐 -> probeContentType이용해 적절한 MIME 타입 데이터를 http 헤더 메시지에 포함될 수 있도록 처리
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));  
			ret = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	//첨부파일 다운로드	
	@GetMapping(value="download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
		Resource resource = new FileSystemResource(UPLOAD_FOLDER + File.separatorChar + fileName);
		if (!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		//파일 이름에 UUID 제거하고 순수하게 다운로드 되는 파일 이름으로 저장될 수 있도록 한다.
		String resourceOriginalName = resourceName.substring(resourceName.lastIndexOf("_") + 1);
		
		//다운로드 시 파일 이름 처리
		HttpHeaders header = new HttpHeaders();
		try {
			String downloadName = null;
			// IE/Edge 브라우저에서 한글 이름이 제대로 적용 안 되는 문제 해결(User-Agent 값 이용)
			if (userAgent.contains("Trident")) {
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			} else if (userAgent.contains("Edge")) {
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			} else {
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), 
						"ISO-8859-1");
			}
			header.add("Content-Disposition",
					"attachment;filename=" + downloadName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	//테이블에서 지우기 기능은 아님. 업로드한 폴더에서 해당 파일 창아서 삭제하기 data:{fileName:targetFile, type:fileType},
	//첨부파일 삭제
	@PostMapping(value="deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(AttachVO attachObj) {
		File file;
		try {
			file = new File(UPLOAD_FOLDER + File.separatorChar + URLDecoder.decode(attachObj.getFileName(), "UTF-8"));
			file.delete();
			//이미지 파일의 경우 섬네일과 원본을 함께 지워줌
			if (attachObj.isFileType()) {
				//~~~\sssss.jpg => 3
				String absolutePath = file.getAbsolutePath();
				int idxFileSeparator = absolutePath.lastIndexOf(File.separatorChar);
				
				StringBuilder sb = new StringBuilder(absolutePath.substring(0, idxFileSeparator + 1));
				sb.append(THUMBNAIL_FILE_PREFIX);
				sb.append(absolutePath.substring(idxFileSeparator + 1));
				
				String largerFileName = sb.toString();
				new File(largerFileName).delete();
			}
			
			//Attach Table을 통해 확보한 첨부 정보 삭제는 table에서도 삭제하여야 합니다.
			if (attachObj.getMasterId() != 0) {
				attachService.deleteAttach(attachObj);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	//첨부파일 삭제
	public void deleteFiles(List<AttachVO> listAttach) {
		for (AttachVO attachVO : listAttach) {
			try {
				String strPath = UPLOAD_FOLDER + File.separatorChar + attachVO.getUploadPath() 
				+ File.separatorChar + attachVO.getUuid() + "_" + attachVO.getFileName();
				Path path = Paths.get(strPath);
				Files.deleteIfExists(path);
				if (attachVO.isFileType()) {
					String strThumbPath = UPLOAD_FOLDER + File.separatorChar + attachVO.getUploadPath() 
					+ File.separatorChar + THUMBNAIL_FILE_PREFIX + attachVO.getUuid() + "_" + attachVO.getFileName();
					path = Paths.get(strThumbPath);
					Files.deleteIfExists(path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//섬네일 이미지 생성
	private String makeThumbnail(File uploadPath, MultipartFile multipartFile, 
			String uuidFileName, File saveFile, AttachVO attachVO)
			throws FileNotFoundException, IOException {
		String savedFileName; //저장된 파일
		 
		if (isImgType(saveFile)) {
			//이미지 파일의 경우 빠른 응답성을 위하여 크기가 작은 섬네일 파일을 추가적으로 만든다.
			attachVO.setFileType(true);
			
			String thumdnailFileName = THUMBNAIL_FILE_PREFIX + uuidFileName;
			savedFileName = File.separatorChar + thumdnailFileName;
			
			File thumdnailFile = new File(uploadPath, thumdnailFileName); //섬네일 파일을 폴더에 저장
			FileOutputStream fos = new FileOutputStream(thumdnailFile);
			Thumbnailator.createThumbnail(multipartFile.getInputStream(), fos, 100, 100); //섬네일 크기 : 100*100
			fos.close();
		} else {
			savedFileName = File.separatorChar + uuidFileName;
		}
		return savedFileName;
	}
	
	// 년/월/일 폴더 생성 -> 중복된 이름의 파일 처리를 해야하는 경우, UUID를 이용해 처리하나 하나의 폴더 안에 생성될 수 있는 파일 개수의 문제로 인해서 폴더를 나눈다.
	public static String getFolderName(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = sdf.format(date);
		//File.separatorChar OS 이식성을 보장해 준다.
		return ymd.replace('-', File.separatorChar);
	}
	
	/* 개발 표준 중에 함수에서 리턴은 오직 한번만 작성한다. */
	//특정한 파일이 이미지 타입인지 검사하는 별도의 메서드
	private boolean isImgType(File file) {
		boolean ret = false;
		try {
			String contentType = Files.probeContentType(file.toPath());
			if (contentType != null) {
				ret = contentType.startsWith("image");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
