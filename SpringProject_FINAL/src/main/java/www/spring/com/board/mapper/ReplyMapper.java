package www.spring.com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.spring.com.board.model.ReplyVO;
import www.spring.com.framework.PageDTO;

public interface ReplyMapper {
	public int getTotalCount(int bno);
	public ReplyVO getReply(int rno);
	public List<ReplyVO> getListReplyOfBoard(int bno);
	public List<ReplyVO> getPagingReplyOfBoard(
			@Param("cri") PageDTO cri, 
			@Param("bno") int bno);
	//Data가 안나올 때 즉 null 일 때 int면 에러 발생합니다.
	public Integer getOldestReply();
	public Integer getOldestBoardIdHavingReply();
	
	public int insertReply(ReplyVO replyVO);
	public int updateReply(ReplyVO replyVO);
	public int deleteReply(int rno);
	public int deleteReplyOfBoard(int bno);
}
