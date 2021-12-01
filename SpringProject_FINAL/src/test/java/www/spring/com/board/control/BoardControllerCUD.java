package www.spring.com.board.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;
import www.spring.com.board.mapper.BoardMapper;
import www.spring.com.board.model.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerCUD {
	@Autowired
	private WebApplicationContext wac;
	
	//Web Browser 대행기, 즉 가짜 브라우저
	private MockMvc mockMvc;
	
	@Autowired
	private BoardMapper boardMapper;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testGetPosting4Modify() throws Exception {
		MockHttpServletRequestBuilder mhsrb = MockMvcRequestBuilders.get("/board/modify");
		mhsrb.param("bno", "2");
		
		ResultActions ra = mockMvc.perform(mhsrb);
		ModelMap mm = ra.andReturn().getModelAndView().getModelMap();
		log.info(mm);
	}

	@Test
	public void testCreateBoard() throws Exception {
		MockHttpServletRequestBuilder mhsrb = MockMvcRequestBuilders.post("/board/insert.do");
		mhsrb.param("title", "MockMvc 활용법")
		.param("content", "JUnit으로 실행하세요")
		.param("writer", "소말");
		ResultActions ra = mockMvc.perform(mhsrb);
		String vn = ra.andReturn().getModelAndView().getViewName();
		log.info(vn);
	}

	@Test
	public void testUpdateBoard() throws Exception {
		BoardVO board = boardMapper.getBoard(1, BoardVO.MASTER_NAME);
		MockHttpServletRequestBuilder mhsrb 
			= MockMvcRequestBuilders.post("/board/update.do");
		mhsrb.param("bno", "" + board.getBno())
		.param("title", board.getTitle().substring(0, 6) + "999")
		.param("content", board.getContent() + "8")
		.param("writer", board.getWriter());
		ResultActions ra = mockMvc.perform(mhsrb);
		String vn = ra.andReturn().getModelAndView().getViewName();
		log.info(vn);
	}
}











