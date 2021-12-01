package www.spring.com.sample.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleVO {
	private int mno;
	private String firstName;
	private String secondName;
	/* 첫 문자가 소문자 뒤이어 바로 대문자는 스프링 내부적으로 setsName과 같이 호출. 
	 * 연동 시 오류 발생. 이는 Lombok에서의 문제점입니다.
	private String sName;
	*/
}
