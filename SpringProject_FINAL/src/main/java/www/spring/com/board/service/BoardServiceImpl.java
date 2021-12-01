package www.spring.com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.spring.com.board.mapper.BoardMapper;
import www.spring.com.board.model.BoardVO;
import www.spring.com.board.model.criteria.BoardCriteria;
import www.spring.com.framework.PageDTO;
import www.spring.com.framework.fileupload.mapper.AttachMapper;
import www.spring.com.framework.fileupload.model.AttachVO;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	

	public int getTotalCount(BoardCriteria cri) {
		return boardMapper.getTotalCount(cri);
	}
	
	public List<BoardVO> getAllBoard() {
		List<BoardVO> listAllBoard = boardMapper.getAllBoard();
		return listAllBoard;
	}

	public List<BoardVO> getListWithPagingByCondition(BoardCriteria cri) {
		return boardMapper.getListWithPagingByCondition(cri);
	}
	

	public BoardVO getBoard(int bno) {
		BoardVO board = boardMapper.getBoard(bno, BoardVO.MASTER_NAME);
		for (AttachVO attach : board.getListAttach()) {
			attach.makeShowBackFileName();
		}
		return board;
	}
	
	//게시글의 첨부파일들의 목록
	public List<AttachVO> getAttachList(int bno) {
		return attachMapper.findByMasterId(bno, "board");
	}

	@Transactional
	public int insertBoard(BoardVO boardVO) {
		//RI 때문에 마스터(게시글) 부터 insert하고 Detail(첨부정보)을 insert해야함.
		int ret = boardMapper.insertBoard(boardVO);
		
		List<AttachVO> listAttach = boardVO.getListAttach();
		
		if (listAttach != null && !listAttach.isEmpty()) {
			listAttach.forEach(attach->{
				attach.setMasterId(boardVO.getBno());
				attachMapper.insertAttach(attach);
			});
		}
		return ret;
	}

	@Transactional
	public int updateBoard(BoardVO board) {
		//테이블에 잔존하는 기존 정보 삭제
		AttachVO dummy4DeleteAll = new AttachVO();
		dummy4DeleteAll.setMasterId(board.getBno());
		dummy4DeleteAll.setMasterName("board");
		attachMapper.deleteAllAttach(dummy4DeleteAll);
		
		//테이블에 읽어 제공한 첨부 몰록에서 사용자가 화면에서 삭제하거나 추가한 정보의 최종 이미지
		int ret = boardMapper.updateBoard(board);
		
		if (ret == 1 && board.getListAttach() != null) {
			board.getListAttach().forEach(attach->{
				//첨부 테이블에서 정보 재등록
				attach.setMasterId(board.getBno());
				attach.setMasterName("board");
				attachMapper.insertAttach(attach);
			});
		}
		return ret;
	}

	@Transactional
	public int deleteBoard(int bno) {
		AttachVO attach = new AttachVO();
		attach.setMasterId(bno);
		attach.setMasterName("board");
		attachMapper.deleteAllAttach(attach);
		return boardMapper.deleteBoard(bno);
	}

	public List<BoardVO> getListWithPaging(PageDTO cri) {
		List<BoardVO> listAllBoard = boardMapper.getListWithPaging(cri);
		return listAllBoard;
	}
}
