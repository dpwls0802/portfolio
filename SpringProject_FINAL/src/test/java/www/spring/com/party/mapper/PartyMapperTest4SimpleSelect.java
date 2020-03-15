package www.spring.com.party.mapper;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.spring.com.party.model.AuthVO;
import www.spring.com.party.model.PartyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
	})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyMapperTest4SimpleSelect {
	@Autowired
	private PartyMapper partyMapper;

	@Test
	public void test3FindParty() {
		PartyVO partyVO = partyMapper.getParty("admin");
		System.out.println(partyVO);
		partyVO.getListAuth().forEach(auth->System.out.println(auth));
	}
}











