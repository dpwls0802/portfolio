package www.spring.com.party.model;

import lombok.Data;

@Data
public class AuthVO {
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_MEMBER = "ROLE_MEMBER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	private String partyId;
	private String auth;
	@Override
	public String toString() {
		return auth;
	}
}
