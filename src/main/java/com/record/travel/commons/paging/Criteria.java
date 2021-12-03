package com.record.travel.commons.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int page;
	private int perPageNum;
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 8;
	}

	//추가
	/*
	 * public Criteria(int page, int perPageNum) { this.page = page; this.perPageNum
	 * = perPageNum; } public Criteria(Criteria cri) { this(cri.page,
	 * cri.perPageNum); }
	 */
	
	public Criteria(int page, int perPageNum) {
		this.page = page;
		this.perPageNum = perPageNum;
	}

	//기존
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page <=0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if(perPageNum <=0 || perPageNum > 80) {
			this.perPageNum = 8;
			return;
		}
		this.perPageNum = perPageNum;
	}

	public int getPageStart() {
		return (this.page -1) * perPageNum;
	}
	
	
	
	
}
