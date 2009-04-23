package springsprout.member.support;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;


public class TestMemberContextTest {

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
