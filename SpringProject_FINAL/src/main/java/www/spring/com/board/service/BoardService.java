package www.spring.com.board.service;

import java.util.List;

import www.spring.com.board.model.BoardVO;
import www.spring.com.board.model.criteria.BoardCriteria;
import www.spring.com.framework.PageDTO;
import www.spring.com.framework.fileupload.model.AttachVO;

public interface BoardService {
	public int getTotalCount(BoardCriteria cri);
	public List<BoardVO> getAllBoard();
	public List<BoardVO> getListWithPagingByCondition(BoardCriteria cri);
	public BoardVO getBoard(int bno);
	public List<AttachVO> getAttachList(int bno);
	public int insertBoard(BoardVO boardVO);
	public int updateBoard(BoardVO board);
	public int deleteBoard(int bno);


	public List<BoardVO> getListWithPaging(PageDTO cri);

}
