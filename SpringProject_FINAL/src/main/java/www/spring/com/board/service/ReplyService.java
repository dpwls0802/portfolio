package www.spring.com.board.service;

import java.util.List;

import www.spring.com.board.model.ReplyVO;
import www.spring.com.framework.PageDTO;
import www.spring.com.framework.Pair;

public interface ReplyService {
	public int getTotalCount(int bno);
	public Pair<Integer, List<ReplyVO>> getPagingReplyOfBoard(int bno, PageDTO cri);
	public ReplyVO getReply(int rno);
	public int insertReply(ReplyVO replyVO);
	public int updateReply(ReplyVO replyVO);
	public int deleteReply(int bno, int rno);
}
