package www.flopo.com.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MapServiceTests {
	@Setter(onMethod_= {@Autowired})
	private MapService service;
	
//	@Test
//	public void testExist() {
//		log.info(service);
//		assertNotNull(service);
//	}
	
//	@Test
//	public void testGetList() {
//		service.getList().forEach(map->log.info(map));
//	}
//	
	@Test
	public void testGet() {
		log.info(service.get(1));
	}
}
