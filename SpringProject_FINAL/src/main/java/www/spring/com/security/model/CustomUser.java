package www.spring.com.security.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import www.spring.com.party.model.PartyVO;

@Getter
public class CustomUser extends User {
	private PartyVO party;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUser(PartyVO party) {
		super(party.getUserId(), party.getUserPwd(),
				party.getListAuth().stream().map(
					auth->new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList()));
		this.party = party;
	}
}


