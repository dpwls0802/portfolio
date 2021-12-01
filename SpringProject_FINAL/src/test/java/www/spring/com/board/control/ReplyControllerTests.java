package www.spring.com.board.control;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import www.spring.com.board.mapper.BoardMapper;
import www.spring.com.board.mapper.ReplyMapper;
import www.spring.com.board.model.BoardVO;
import www.spring.com.board.model.ReplyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyControllerTests {
	@Autowired
	private WebApplicationContext wac;

	//Web Browser 대행기, 즉 가짜 브라우저
	private MockMvc mockMvc;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ReplyMapper replyMapper;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testAuto() throws Exception {
		assertNotNull(boardMapper);
	}

	@Test
	public void test1SelectReply() {
		try {
			/*
			 * 본 클래스는 REST 방식 테스트 기능입니다. 
			 * 여기서 HTTP Client에 의한 선택된 게시글 번호를 빠르게 획득하기 위하여 
			 * 임시방편적으로 BoardMapper를 직접 참조하여 활용하도록 했습니다.
			 */
			int rno = replyMapper.getOldestReply();
			String url = "/reply/getReply/" + rno;
			
			MockHttpServletRequestBuilder mhsrb = MockMvcRequestBuilders.get(url);
			ResultActions resultActions = mockMvc.perform(mhsrb);
			MvcResult rst = resultActions.andReturn();
			MockHttpServletResponse mhsr = rst.getResponse();
			String data = mhsr.getContentAsString();
			System.out.println(data);
			resultActions.andExpect(MockMvcResultMatchers.status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2SelectListReply() {
		try {
			/*
			 * 본 클래스는 REST 방식 테스트 기능입니다. 
			 * 여기서 HTTP Client에 의한 선택된 게시글 번호를 빠르게 획득하기 위하여 
			 * 임시방편적으로 BoardMapper를 직접 참조하여 활용하도록 했습니다.
			 */
			int bno = replyMapper.getOldestBoardIdHavingReply();
			String url = "/reply/getPagingReply/" + bno + "/1/10";
			
			MockHttpServletRequestBuilder mhsrb = MockMvcRequestBuilders.get(url);
			ResultActions resultActions = mockMvc.perform(mhsrb);
			MvcResult rst = resultActions.andReturn();
			MockHttpServletResponse mhsr = rst.getResponse();
			String data = mhsr.getContentAsString();
			System.out.println(data);
			resultActions.andExpect(MockMvcResultMatchers.status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test5CreateReply() {
		try {
			/*
			 * 본 클래스는 REST 방식 테스트 기능입니다. 
			 * 여기서 HTTP Client에 의한 선택된 게시글 번호를 빠르게 획득하기 위하여 
			 * 임시방편적으로 BoardMapper를 직접 참조하여 활용하도록 했습니다.
			 */
			BoardVO boardVO = boardMapper.getBoardLatest();
			
			ReplyVO replyVO = new ReplyVO(boardVO.getBno(), "나 댓글", "홍길동");
			String jsonMsg = new Gson().toJson(replyVO);
			System.out.println(jsonMsg);
			
			MockHttpServletRequestBuilder mhsrb = MockMvcRequestBuilders.post("/reply/createReply");
			mhsrb = mhsrb.contentType(MediaType.APPLICATION_JSON_UTF8);
			mhsrb = mhsrb.content(jsonMsg);
			ResultActions resultActions = mockMvc.perform(mhsrb);
			resultActions.andExpect(MockMvcResultMatchers.status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
