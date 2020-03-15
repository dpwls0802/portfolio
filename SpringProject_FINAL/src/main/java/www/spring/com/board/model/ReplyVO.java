package www.spring.com.board.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyVO {
	private int rno;
	private int bno;
	private String content;
	private String replyer;
	private Date regdate;
	private Date updatedate;
	
	public ReplyVO(int bno, String content, String replyer) {
		this.bno = bno;
		this.content = content;
		this.replyer = replyer;
	}
}
