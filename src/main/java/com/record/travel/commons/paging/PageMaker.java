package com.record.travel.commons.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageMaker {

	private int startPage; // 시작 페이지 번호
	private int endPage; // 끝 페이지 번호
	private boolean hasPrev;
	private boolean hasNext; // 이전, 다음 링크
	private int totalCnt; // 전체 게시글 개수
	private int displayPageNum = 10; // 하단의 페이지 번호 개수

	private Criteria criteria;

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
		calcData();
	}
	
	private void calcData() {
		endPage = (int) (Math.ceil(criteria.getPage() / (double) displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1 ;
		
		int realEnd = (int) Math.ceil(totalCnt / (double) criteria.getPerPageNum());
		endPage = realEnd < endPage ? realEnd : endPage;
		
		hasPrev = startPage > 1;
		hasNext = realEnd > endPage;
	}
	
		
}
