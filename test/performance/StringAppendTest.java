package performance;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

public class StringAppendTest {

	StopWatch stopWatch;

	@Before
	public void setUp(){
		stopWatch = new StopWatch();
		stopWatch.start();
	}

	@Test
	public void appendByPlus() throws Exception {
		int i = 0;
		while(i < 100000){
			runAppendByPlusExample();
			i++;
		}
		stopWatch.stop();
		System.out.println("by Plus: " + stopWatch.getLastTaskTimeMillis());
	}

	@Test
	public void appendByContinuousPlus() throws Exception {
		int i = 0;
		while(i < 100000){
			runAppendByContinuousPlusExample();
			i++;
		}
		stopWatch.stop();
		System.out.println("by Continuous Plus: " + stopWatch.getLastTaskTimeMillis());
	}

	@Test
	public void appendByBuilder() throws Exception {
		int i = 0;
		while(i < 100000){
			runAppendByBuilderExample();
			i++;
		}
		stopWatch.stop();
		System.out.println("by Builder: " + stopWatch.getLastTaskTimeMillis());
	}

	@Test
	public void appendByBuffer() throws Exception {
		int i = 0;
		while(i < 100000){
			runAppendByBufferExample();
			i++;
		}
		stopWatch.stop();
		System.out.println("by Buffer: " + stopWatch.getLastTaskTimeMillis());
	}

	private void runAppendByContinuousPlusExample() {
		String result = "a";
		result += "bc" + "de";
	}

	private void runAppendByBufferExample() {
		StringBuffer buffer = new StringBuffer("a");
		buffer.append("bc");
		buffer.append("de");

	}

	private void runAppendByBuilderExample() {
		StringBuilder builder = new StringBuilder("a");
		builder.append("bc");
		builder.append("de");
	}

	private void runAppendByPlusExample() {
		String result = "a";
		result += "bc";
		result += "de";
	}


}
