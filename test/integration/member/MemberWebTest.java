package integration.member;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import springsprout.test.web.WebTestRunner;
import springsprout.test.web.annotation.DataConfiguration;
import springsprout.test.web.annotation.WarConfiguration;
import springsprout.test.web.annotation.WebTest;
import springsprout.test.web.enumeration.DataType;

@RunWith(WebTestRunner.class)
@WarConfiguration(appName="springsprout")
@DataConfiguration(dataType=DataType.XML, location="integration/member/testData.xml")
public class MemberWebTest {

	@WebTest
	@Ignore
	public void crud(){
		//add page load
		WebDriver driver = new FirefoxDriver();
//		WebDriver driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/springsprout/member/add.do");
		assertEquals("SpringSprout", driver.getTitle());

		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement name = driver.findElement(By.id("name"));
		WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));

		// submit fail
		email.sendKeys("keesun@test.me");
		password.sendKeys("123");
		submit.submit();

		WebElement nameError = driver.findElement(By.id("name.errors"));
		assertEquals("required", nameError.getText());

		// submit success
		password = driver.findElement(By.id("password"));
		name = driver.findElement(By.id("name"));
		submit = driver.findElement(By.xpath("//input[@type='submit']"));

		name.sendKeys("keesun");
		password.sendKeys("123");
		submit.submit();

		// list page
		WebElement addedData = driver.findElement(By.linkText("keesun@test.me"));
		assertNotNull(addedData);

		// go to view page
		WebElement testData = driver.findElement(By.linkText("test@data.me"));
		testData.click();

		// view page
		// <member id="100" name="test" email="test@data.me" password="123" />
//		String contents = driver.getPageSource();
//		assertTrue(contents.contains("이름: test"));
//		assertTrue(contents.contains("이메일: test@data.me"));

		// go to update page
		WebElement modifyLink = driver.findElement(By.linkText("수정"));
		modifyLink.click();

		// update page
		email = driver.findElement(By.id("email"));
		email.clear();
		email.sendKeys("testComplete@keesun.me");

		submit = driver.findElement(By.xpath("//input[@type='submit']"));
		submit.click();

		// list page again
		testData = driver.findElement(By.linkText("testComplete@keesun.me"));
		assertNotNull(addedData);

		// view page amain
		testData.click();

		// delete
		WebElement deleteLink = driver.findElement(By.linkText("삭제"));
		deleteLink.click();

		// list page again
		List<WebElement> datas = driver.findElements(By.xpath("//tr"));
		assertEquals(3, datas.size()); // 1 head + 2 data
	}

}
