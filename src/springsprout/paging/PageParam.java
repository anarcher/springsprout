package springsprout.paging;

import springsprout.web.URLBuilder;

public class PageParam {

	public static final int DEFAULT_SIZE = 5;
	public static final int DEFAULT_PAGE = 1;

	private int size; // number of items in one page.
	private int page; // page number.
	private int totalRowsCount;

	public PageParam() {
		size = DEFAULT_SIZE;
		page = DEFAULT_PAGE;
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
		if(page%10 == 0)
			return page - 9;

		return page/10 * 10 + 1;
	}

	public int getEndPage() {
		if(page%10 == 0)
			return page;

		int endPage = 10 * (page/10 + 1);
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

	@Override
	public String toString() {
		URLBuilder builder = new URLBuilder();
		builder.addParameter("p_page", page, "");
		builder.addParameter("p_size", size, "");
		return builder.toString();
	}

	public int[] getPageSizes(){
		return new int[]{5, 10, 20, 50, 100};
	}

}
