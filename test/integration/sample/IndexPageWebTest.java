package integration.sample;

import org.junit.runner.RunWith;

import springsprout.test.web.WebTestRunner;
import springsprout.test.web.annotation.DataConfiguration;
import springsprout.test.web.annotation.WarConfiguration;
import springsprout.test.web.annotation.WebTest;
import springsprout.test.web.enumeration.DataType;

@RunWith(WebTestRunner.class)
@WarConfiguration(appName="springsprout")
@DataConfiguration(dataType=DataType.XML, location="integration/sample/testData.xml")
public class IndexPageWebTest {

	@WebTest
	public void test1(){
		System.out.println("===================================");
		System.out.println("test1");
		System.out.println("===================================");
	}

	@WebTest
	public void test2(){
		System.out.println("***********************************");
		System.out.println("test2");
		System.out.println("***********************************");
	}

}
