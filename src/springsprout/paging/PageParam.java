package springsprout.paging;

public class PageParam {

	private int size; // number of items in one page.
	private int page; // page number.
	private int totalRowsCount;

	public PageParam() {
		size = 5; // default
		page = 1; // default
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalRowsCount() {
		return totalRowsCount;
	}

	public void setTotalRowsCount(int totalRowsCount) {
		this.totalRowsCount = totalRowsCount;
	}

	public int getFirstRowNumber() {
		if (page == 1)
			return 1;
		return (page - 1) * size + 1;
	}

	public int getBeginPage(){
		return page/10 * 10 + 1;
	}

	public int getEndPage() {
		int endPage = page/10 * 10 + 9;
		if(getTotalPage() < endPage)
			return getTotalPage();
		else
			return endPage;
	}

	public int getTotalPage() {
		int totalPage = totalRowsCount/size;
		if(totalRowsCount%size != 0)
			totalPage += 1;
		return totalPage;
	}

}
