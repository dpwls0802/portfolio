package www.spring.com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.spring.com.board.mapper.BoardMapper;
import www.spring.com.board.mapper.ReplyMapper;
import www.spring.com.board.model.ReplyVO;
import www.spring.com.framework.PageDTO;
import www.spring.com.framework.Pair;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardMapper boardMapper;

	public int getTotalCount(int bno) {
		return replyMapper.getTotalCount(bno);
	}

	public Pair<Integer, List<ReplyVO>> getPagingReplyOfBoard(int bno, PageDTO cri) {
		Pair<Integer, List<ReplyVO>> ret = new Pair<>();
		ret.setFirst(replyMapper.getTotalCount(bno));
		ret.setSecond(replyMapper.getPagingReplyOfBoard(cri, bno));
		return ret;
	}
	
	public ReplyVO getReply(int rno) {
		return replyMapper.getReply(rno);
	}
	
	@Transactional
	public int insertReply(ReplyVO replyVO) {
		boardMapper.updateReplyCount(replyVO.getBno(), 1);
		return replyMapper.insertReply(replyVO);
	}
	
	public int updateReply(ReplyVO replyVO) {
		return replyMapper.updateReply(replyVO);
	}
	
	@Transactional
	public int deleteReply(int bno, int rno) {
		boardMapper.updateReplyCount(bno, -1);
		 
		return replyMapper.deleteReply(rno);
	}

	//게시글 삭제 시 동일 트랜젝션으로 함께 모든 부속 댓글 삭제하기
	public int deleteReplyOfBoard(int bno) {
		return replyMapper.deleteReplyOfBoard(bno);
	}

}
