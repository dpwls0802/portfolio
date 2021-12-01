package www.flopo.com.domain;

import lombok.Data;

@Data
public class UserVO {
	private int bno;
	private String name; // 이름
	private String email; // 이메일
	private String ageRange; // 나이대
	private String gender; // 성별
	//private String birthday; // 생일
}
