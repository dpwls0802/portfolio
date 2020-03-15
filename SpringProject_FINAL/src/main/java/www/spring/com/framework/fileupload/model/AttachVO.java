package www.spring.com.framework.fileupload.model;

import java.io.File;

import lombok.Data;
import www.spring.com.framework.fileupload.controller.UploadController;

@Data
public class AttachVO {
	private String masterName;
	private String uuid;
	private int masterId;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
	private String showBackFileName;
	
	public void appendShowBackFileName(String savedFileName) {
		showBackFileName += savedFileName;
	}
	
	public void makeShowBackFileName() {
		if (fileType) {
			//Image 일 때
			showBackFileName = uploadPath + File.separator + UploadController.THUMBNAIL_FILE_PREFIX + uuid + "_" + fileName;  
		} else {
			//Image가 아닐 때
			showBackFileName = uploadPath + File.separator + uuid + "_" + fileName;  
		}
		
	}
}
