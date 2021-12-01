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
public class PartyMapperTest4CUD {
	@Autowired
	private PartyMapper partyMapper;

	@Autowired
	private PasswordEncoder pwdEncoder;

	@Test
	public void test1CreateParty() {
		PartyVO partyVO = new PartyVO();
	
		partyVO.setUserId("user"); partyVO.setUserPwd(pwdEncoder.encode("user"));
		partyVO.setName("사용자"); partyMapper.insertParty(partyVO);
		
		partyVO.setUserId("member"); partyVO.setUserPwd(pwdEncoder.encode("member"));
		partyVO.setName("운영자"); partyMapper.insertParty(partyVO);
		 
		partyVO.setUserId("admin"); partyVO.setUserPwd(pwdEncoder.encode("admin"));
		partyVO.setName("관리자"); partyMapper.insertParty(partyVO);
		
	}

	@Test
	public void test2CreateAuth() {
		AuthVO authVO = new AuthVO();
		authVO.setPartyId("user");
		authVO.setAuth(AuthVO.ROLE_USER);
		partyMapper.insertAuthority(authVO);
		
		authVO = new AuthVO(); authVO.setPartyId("member");
		authVO.setAuth(AuthVO.ROLE_MEMBER); partyMapper.insertAuthority(authVO);
		 
		authVO = new AuthVO(); authVO.setPartyId("admin");
		authVO.setAuth(AuthVO.ROLE_ADMIN); partyMapper.insertAuthority(authVO);
		authVO.setAuth(AuthVO.ROLE_MEMBER); partyMapper.insertAuthority(authVO);
		 
	}
}











