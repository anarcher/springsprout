package springsprout.member.support;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import springsprout.paging.PageParam;


public class MemberContextTest {

	MemberContext context;

	@Before
	public void setUp(){
		context = new MemberContext();
		SearchParam searchParam = new SearchParam();
		PageParam pageParam = new PageParam();
		OrderParam orderParam = new OrderParam();
		context.searchParam = searchParam;
		context.orderParam = orderParam;
		context.pageParam = pageParam;
	}

	@Test
	public void getAllParamURL() throws Exception {
		assertEquals("p_page=1&p_size=5&s_name=&s_email=&o_field=&o_direction=",
				context.getAllParamURL());
	}

	@Test
	public void getPageParamURL() throws Exception {
		assertEquals("p_page=1&p_size=5", context.getPageParamURL());
	}

	@Test
	public void getSearchParamURL() throws Exception {
		assertEquals("&s_name=&s_email=", context.getSearchParamURL());
	}


	@Test
	public void getOrderParamURL() throws Exception {
		assertEquals("&o_field=&o_direction=", context.getOrderParamURL());
	}

	@Test
	public void bindParams() throws Exception {
		MemberContext context = new MemberContext();
		MockHttpServletRequest mockReqeust = new MockHttpServletRequest();
		mockReqeust.addParameter("o_direction", "asc");
		mockReqeust.addParameter("o_field", "name");

		context.bindParams(mockReqeust);
		OrderParam orderParam = context.getOrderParam();
		assertEquals("asc", orderParam.getDirection());
		assertEquals("name", orderParam.getField());
	}
}
