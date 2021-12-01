package www.flopo.com.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import www.flopo.com.domain.MapVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MapMapperTests {
	@Setter(onMethod_ = @Autowired)
	private MapMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(map->log.info(map));
	}
	
	@Test
	public void testRead() {
		MapVO map = mapper.read(1);
		log.info(map);
	}
	
}
