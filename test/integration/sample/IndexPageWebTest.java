package integration.sample;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

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
	public void sample(){
		WebDriver driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/springsprout/index.do");
		assertTrue(driver.getTitle().equals("SpringSprout"));
	}

}
