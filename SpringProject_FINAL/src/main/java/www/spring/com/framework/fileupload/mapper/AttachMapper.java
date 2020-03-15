package www.spring.com.framework.fileupload.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.spring.com.framework.fileupload.model.AttachVO;

public interface AttachMapper {
	//여러 parameter와 연동될 때는  Map을 활용하거나 @Param 를 활용해야합니다. 
	public List<AttachVO> findByMasterId(@Param("masterId") int masterId, @Param("masterName") String masterName);
	
	public int insertAttach(AttachVO attachVO);
	public int deleteAttach(AttachVO attachVO);
	public int deleteAllAttach(AttachVO attachVO);
	
	public List<AttachVO> getOldFiles();
	
}
