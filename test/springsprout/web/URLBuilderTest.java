package springsprout.web;

import static org.junit.Assert.*;

import org.junit.Test;


public class URLBuilderTest {

	@Test
	public void addIntParameter() throws Exception {
		URLBuilder builder = new URLBuilder();
		assertEquals("", builder.toString());

		builder.addParameter("p_page", 1, "0");
		assertEquals("p_page=1", builder.toString());

		builder.addParameter("p_size", null, null);
		assertEquals("p_page=1", builder.toString());

		builder.addParameter("p_size", null, "5");
		assertEquals("p_page=1&p_size=5", builder.toString());
	}

}
