package www.spring.com.framework;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO {
	private static final int PAGE_NAV_SIZE = 10;
	private int pageNum;
	private int amount;

	private int startPage;
	private int endPage;
	private boolean hasPrev, hasNext;
	
	private int totalCnt;
	
	public PageDTO() {
		this.pageNum = 1;
		this.amount = 10;
	}
	
	public PageDTO(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public PageDTO(PageDTO cri, int totalCnt) {
		this(cri.pageNum, cri.amount);
		this.totalCnt = totalCnt;
		
		endPage = (int) (Math.ceil(pageNum / (float) PAGE_NAV_SIZE) * PAGE_NAV_SIZE);
		startPage = endPage - PAGE_NAV_SIZE + 1 ;
		int realEnd = (int) Math.ceil(((float) totalCnt) / amount);
		endPage = realEnd < endPage ? realEnd : endPage;
		hasPrev = startPage > 1;
		hasNext = realEnd > endPage;
	}
}









