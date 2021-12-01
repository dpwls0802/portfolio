package www.spring.com.framework.fileupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.spring.com.framework.fileupload.mapper.AttachMapper;
import www.spring.com.framework.fileupload.model.AttachVO;

@Service
public class AttachServiceImpl implements AttachService {
	@Autowired
	private AttachMapper attachMapper;
	
	public int deleteAttach(AttachVO attachVO) {
		return attachMapper.deleteAttach(attachVO);
	}
}
