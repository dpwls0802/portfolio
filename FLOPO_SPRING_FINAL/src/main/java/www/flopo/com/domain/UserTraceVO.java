package www.flopo.com.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserTraceVO {
	private int bno;
	private String latitude; //����
	private String longitude; //�浵
	private String settingTime; //설정시간
	private String email; // 이메일
}
