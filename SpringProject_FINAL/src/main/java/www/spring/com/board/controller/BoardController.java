package www.spring.com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import www.spring.com.board.model.BoardVO;
import www.spring.com.board.model.criteria.BoardCriteria;
import www.spring.com.board.service.BoardService;
import www.spring.com.framework.fileupload.controller.UploadController;
import www.spring.com.framework.fileupload.model.AttachVO;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UploadController uploadController;
	
	@RequestMapping("/list")
	public String listAllBoard(BoardCriteria cri, Model model) {
		List<BoardVO> listAllBoard = boardService.getListWithPagingByCondition(cri);
		model.addAttribute("listAllBoard", listAllBoard);
		
		int totalCnt = boardService.getTotalCount(cri);
		BoardCriteria criWithTot = new BoardCriteria(cri, totalCnt);

		model.addAttribute("pageMaker", criWithTot);
		
		return "board/listAllBoard";
	}
	
	@GetMapping("createPost")	//로그인한 사용자만 글 작성 가능.
	@PreAuthorize("isAuthenticated()")
	public void createPost(Model model) {
		model.addAttribute("masterName", BoardVO.MASTER_NAME);
	}
	
	@PostMapping("/insert.do")
	@PreAuthorize("isAuthenticated()") //로그인한 사용자만 글 작성 가능.
	public String insertBoard(BoardVO boardVO, RedirectAttributes rttr) {
		boardService.insertBoard(boardVO);
		rttr.addFlashAttribute("result", boardVO.getBno());
		return "redirect:/board/list";
	}
	
	//board/showDetail?dsfjsklgjskd/bno=2
	@GetMapping({"showDetail", "modify"})
	public void getPosting(BoardCriteria cri, int bno, Model model) {
		BoardVO board = boardService.getBoard(bno);
		model.addAttribute("board", board);
		model.addAttribute("pageMaker", cri);
		model.addAttribute("masterName", BoardVO.MASTER_NAME);
	}
	
	//로그인한 사용자와 작성자가 같아야 한다.
	@PreAuthorize("principal.party.name == #boardVO.writer")
	@PostMapping("/update.do")
	public String updateBoard(@ModelAttribute("pageMaker") 
			BoardCriteria cri, BoardVO boardVO, RedirectAttributes rttr) {
		boardService.updateBoard(boardVO);
		rttr.addFlashAttribute("result", "수정 처리 완료");

		return "redirect:/board/list" + cri.getListLink();
	}
	
	//로그인한 사용자과 작성자가 같아야 한다.
	@PreAuthorize("principal.party.name == #writer")
	@PostMapping("deletePosting")
	public String deletePosting(@ModelAttribute("pageMaker") BoardCriteria cri, 
							int bno, RedirectAttributes rttr, String writer) {
		List<AttachVO> listAttach = boardService.getAttachList(bno);
		
		if (boardService.deleteBoard(bno) != 0) {
			uploadController.deleteFiles(listAttach);
		}
		rttr.addFlashAttribute("result", "삭제 처리 완료");

		return "redirect:/board/list" + cri.getListLink();
	}
	
}

















