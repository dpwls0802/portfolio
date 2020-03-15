package www.spring.com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.spring.com.board.model.BoardVO;
import www.spring.com.board.model.criteria.BoardCriteria;
import www.spring.com.framework.PageDTO;

public interface BoardMapper {
	public int getTotalCount(@Param("cri") BoardCriteria cri);
	public List<BoardVO> getAllBoard();
	public List<BoardVO> getListWithPagingByCondition(@Param("cri") BoardCriteria cri);
	public BoardVO getBoard(@Param("bno") int bno, @Param("masterName") String masterName);
	public BoardVO getBoardLatest();
	public List<BoardVO> getBoardTwoLatest();
	public int insertBoard(BoardVO boardVO);
	public int updateBoard(BoardVO boardVO);
	public int deleteBoard(int bno);
	public int deleteBoardByColl(List<Integer> listBno);
	public void updateReplyCount(@Param("bno") int bno, @Param("amount") int amount);

	public List<BoardVO> getListWithPaging(PageDTO cri);
}
