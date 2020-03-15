package www.spring.com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.spring.com.board.model.ReplyVO;
import www.spring.com.board.service.ReplyService;
import www.spring.com.framework.PageDTO;
import www.spring.com.framework.Pair;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	@GetMapping(value="/getReply/{rno}")
	public ResponseEntity<ReplyVO> getReply(@PathVariable("rno") int rno) {
		return new ResponseEntity<ReplyVO>(replyService.getReply(rno), HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/createReply")
	public ResponseEntity<String> createReply(@RequestBody ReplyVO replyVO) {
		int cnt = replyService.insertReply(replyVO);
		return cnt == 1 ? new ResponseEntity<String>("success", HttpStatus.OK) : 
			new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PreAuthorize("principal.party.name == #replyVO.replyer")
	@PutMapping("updateReply")
	public ResponseEntity<String> updateReply(@RequestBody ReplyVO replyVO) {
		int cnt = replyService.updateReply(replyVO);
		return cnt == 1 ? new ResponseEntity<String>("success", HttpStatus.OK) : 
			new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PreAuthorize("principal.party.name == #curUser")
	@DeleteMapping("/deleteReply/{bno}/{rno}/{curUser}")
	public ResponseEntity<String> deleteReply(@PathVariable("bno") int bno, 
			@PathVariable("rno") int rno, @PathVariable("curUser") String curUser) {
		int cnt = replyService.deleteReply(bno, rno);
		return cnt == 1 ? new ResponseEntity<String>("success", HttpStatus.OK) : 
			new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/getPagingReply/{bno}/{pageNum}/{amount}")
	public ResponseEntity<Pair<Integer, List<ReplyVO>>> getPagingReplyOfBoard(
			@PathVariable("bno") int bno,
			@PathVariable("pageNum") int pageNum,
			@PathVariable("amount") int amount
			) {
		
		PageDTO cri = new PageDTO(pageNum, (int) amount);

		return new ResponseEntity<Pair<Integer, List<ReplyVO>>>
					(replyService.getPagingReplyOfBoard(bno, cri), HttpStatus.OK);
	}
}









