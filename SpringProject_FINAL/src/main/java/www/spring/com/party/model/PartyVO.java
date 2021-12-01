package www.spring.com.party.model;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import lombok.Data;

@Data
public class PartyVO {
	private String userId;
	private String userPwd;
	private String name;
	private Date regDate;
	private Date updateDate;
	private boolean enabled;
	
	//권한 목록 정보
	private List<AuthVO> listAuth;

	@Override
	public String toString() {
		return "PartyVO [userId=" + userId + ", name=" + name + ", regDate=" + regDate + ", updateDate=" + updateDate
				+ ", enabled=" + enabled + "]";
	}
	
	public String getJsonName() {
		Gson gson = new Gson();
		String ret = gson.toJson(name);
		return ret;
	}
	
}
