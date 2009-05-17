package springsprout.modules.member.support;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import springsprout.common.web.PageParam;
import springsprout.modules.member.support.MemberContext;
import springsprout.modules.member.support.OrderParam;
import springsprout.modules.member.support.SearchParam;


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
	public void getAllParam() throws Exception {
		assertEquals("p_page=1&p_size=5&s_name=&s_email=&o_field=&o_direction=",
				context.getAllParam());
	}

	@Test
	public void getPageParam() throws Exception {
		assertEquals("p_page=1&p_size=5", context.getPageParam().toString());
	}

	@Test
	public void getSearchParam() throws Exception {
		assertEquals("s_name=&s_email=", context.getSearchParam().toString());
	}


	@Test
	public void getOrderParam() throws Exception {
		assertEquals("o_field=&o_direction=", context.getOrderParam().toString());
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
