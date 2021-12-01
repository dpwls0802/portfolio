package www.spring.com.restSample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import www.spring.com.sample.rest.model.SampleVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class SampleControllerTests {
	@Autowired
	private WebApplicationContext wac;

	//Web Browser 대행기, 즉 가짜 브라우저
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testConvert() {
		try {
			SampleVO obj = new SampleVO(1, "kildong", "홍");
			String jsonMsg = new Gson().toJson(obj);
			System.out.println(jsonMsg);
			
			MockHttpServletRequestBuilder mhsrb = MockMvcRequestBuilders.post("/sample/ticket");
			mhsrb = mhsrb.contentType(MediaType.APPLICATION_JSON_UTF8);
			mhsrb = mhsrb.content(jsonMsg);
			ResultActions resultActions = mockMvc.perform(mhsrb);
			resultActions.andExpect(MockMvcResultMatchers.status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
