package integration.sample;

import org.junit.runner.RunWith;

import springsprout.test.web.DataManager;
import springsprout.test.web.DefaultDataManager;
import springsprout.test.web.WebTestRunner;
import springsprout.test.web.annotation.WarConfiguration;
import springsprout.test.web.annotation.WebTest;
import springsprout.test.web.enumeration.DataType;

@RunWith(WebTestRunner.class)
@WarConfiguration(appName="springsprout")
//@DataConfiguration(dataType=DataType.XML, location="integration/sample/testData.xml")
public class SampleWebTest {

	@WebTest
	public void sinario1(){
		DataManager dm = new DefaultDataManager("integration/sample/testData1.xml", DataType.XML);
		dm.insertTestData();
		System.out.println("test1");
	}

	@WebTest
	public void sinario2(){
		DataManager dm = new DefaultDataManager("integration/sample/testData1.xml", DataType.XML);
		dm.insertTestData();
		System.out.println("test2");
	}


}
