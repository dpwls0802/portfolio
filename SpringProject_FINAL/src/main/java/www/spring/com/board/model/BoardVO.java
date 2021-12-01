package www.spring.com.board.model;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import www.spring.com.framework.fileupload.model.AttachVO;

public class BoardVO {
	/** 첨부 파일 관리 모듈이 독립적으로 만들어져 많은 Master들과 결합될 수 있도록 개발함.
	 * 따라서 첨부 정보 중 어디에 달린 첨부 내용인지 관리하기 위하여 각종 jsp 및 mybatis xml에서
	 * 필요한 상수를 이곳에서 정의합니다. */
	public static final String MASTER_NAME = "board";
	
	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;

	private int replyCount;
	//연관 속성 - association property
	private List<AttachVO> listAttach;

	public BoardVO() {}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public List<AttachVO> getListAttach() {
		return listAttach;
	}

	public void setListAttach(List<AttachVO> listAttach) {
		this.listAttach = listAttach;
	}

	public String getJsonOfListAttach() {
		Gson gson = new Gson();
		String ret = gson.toJson(listAttach);
		return ret;
	}
	
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + ", updatedate=" + updatedate + ", replyCount=" + replyCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bno;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardVO other = (BoardVO) obj;
		if (bno != other.bno)
			return false;
		return true;
	}
	
	
}
