package www.spring.com.party.mapper;

import www.spring.com.party.model.AuthVO;
import www.spring.com.party.model.PartyVO;

public interface PartyMapper {
	public PartyVO getParty(String userId);
	public int insertParty(PartyVO party);
	
	public int insertAuthority(AuthVO auth);
}
