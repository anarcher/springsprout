package integration.sample;

import org.junit.runner.RunWith;
import org.opensprout.webtest.DataManager;
import org.opensprout.webtest.DefaultDataManager;
import org.opensprout.webtest.WebTestRunner;
import org.opensprout.webtest.configuration.DataType;
import org.opensprout.webtest.configuration.WarConfiguration;
import org.opensprout.webtest.configuration.WebTest;

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
		DataManager dm = new DefaultDataManager("integration/sample/testData2.xml", DataType.XML);
		dm.insertTestData();
		System.out.println("test2");
	}


}
