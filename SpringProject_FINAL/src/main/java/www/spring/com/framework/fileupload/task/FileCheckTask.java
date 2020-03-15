package www.spring.com.framework.fileupload.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import www.spring.com.framework.fileupload.controller.UploadController;
import www.spring.com.framework.fileupload.mapper.AttachMapper;
import www.spring.com.framework.fileupload.model.AttachVO;
import www.spring.com.framework.util.DateUtil;

@Component
public class FileCheckTask {
	@Autowired
	AttachMapper attachMapper;

	//초, 분, 시, 일, 월, 주
	@Scheduled(cron="0 0 1 * * *")
	//@Scheduled(cron="0 0 1 * * *") // 매일 자정에
	public void checkFiles() throws Exception {
		List<AttachVO> listOldFiles = attachMapper.getOldFiles();
		
		//원본 파일 이름 구성하기
		List<Path> filePaths = listOldFiles.stream().map(
				attachObj -> Paths.get(UploadController.UPLOAD_FOLDER, 
						attachObj.getUploadPath(), 
						attachObj.getUuid() + "_" + attachObj.getFileName()))
				.collect(Collectors.toList());
		
		//원본 파일이 이미지 파일일 경우 이름 구성하기
		listOldFiles.stream().filter(attachObj -> attachObj.isFileType())
			.map(attachObj -> Paths.get(UploadController.UPLOAD_FOLDER, 
						attachObj.getUploadPath(), 
						UploadController.THUMBNAIL_FILE_PREFIX 
						+ attachObj.getUuid() + "_" + attachObj.getFileName()))
			.forEach(path->filePaths.add(path));

		//어제 폴더에 남아있는 파일들 확보
		File targetDir = Paths.get(UploadController.UPLOAD_FOLDER, 
				UploadController.getFolderName(DateUtil.getYesterday())).toFile();

		//어제 폴더에 남아있는 파일 중에 테이블에 정보가 남아있지 않은 파일 목록 찾기 및 삭제
		File[] removeTargetFiles = targetDir.listFiles(
				file -> filePaths.contains(file.toPath()) == false);
		if (removeTargetFiles != null) {
			for (File removeTargetFile : removeTargetFiles) {
				removeTargetFile.delete();
			}
		}
	}
}
