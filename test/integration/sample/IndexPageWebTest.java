package integration.sample;

import org.junit.runner.RunWith;

import springsprout.test.web.WebTestRunner;
import springsprout.test.web.annotation.WebTest;
import springsprout.test.web.annotation.WebTestConfiguration;

@RunWith(WebTestRunner.class)
@WebTestConfiguration(appName = "springsprout", testDataLocation = "/integration/sample/testData.xml")
public class IndexPageWebTest {

	@WebTest
	public void sample(){
		System.out.println("doing web(driver) test");
	}

}
