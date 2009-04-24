package paging;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import springsprout.paging.PageParam;

public class PageParamTest {

	PageParam pageParam;

	@Before
	public void setUp(){
		pageParam = new PageParam();
	}

	@Test
	public void beginPage() throws Exception {
		assertBeginPage(13, 11);
		assertBeginPage(11, 11);
		assertBeginPage(29, 21);
		assertBeginPage(10, 1);
		assertBeginPage(20, 11);
	}

	private void assertBeginPage(int page, int beginPage) {
		pageParam.setPage(page);
		assertEquals(beginPage, pageParam.getBeginPage());
	}

	@Test
	public void endPage() throws Exception {
		assertEndPage(5, 9, 1, 2);
		assertEndPage(10, 9, 1, 1);
		assertEndPage(5, 1000, 29, 30);
		assertEndPage(5, 1000, 10, 10);
	}

	private void assertEndPage(int size, int totalRowsCount, int page, int endPage) {
		pageParam.setSize(size);
		pageParam.setTotalRowsCount(totalRowsCount);
		pageParam.setPage(page);
		assertEquals(endPage, pageParam.getEndPage());
	}

	@Test
	public void totalPage() throws Exception {
		assertTotalPage(5, 9, 2);
		assertTotalPage(10, 9, 1);
		assertTotalPage(5, 20, 4);
	}

	private void assertTotalPage(int size, int totalRowsCount, int totalPage) {
		pageParam.setSize(size);
		pageParam.setTotalRowsCount(totalRowsCount);
		assertEquals(totalPage, pageParam.getTotalPage());
	}

}
