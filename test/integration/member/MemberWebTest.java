package integration.member;

import static org.junit.Assert.*;
import integration.pageobject.MemberAddPage;
import integration.pageobject.MemberListPage;
import integration.pageobject.MemberUpdatePage;
import integration.pageobject.MemberViewPage;

import java.util.List;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.opensprout.webtest.WebTestRunner;
import org.opensprout.webtest.configuration.DataConfiguration;
import org.opensprout.webtest.configuration.DataType;
import org.opensprout.webtest.configuration.WarConfiguration;
import org.opensprout.webtest.configuration.WebTest;

@RunWith(WebTestRunner.class)
@WarConfiguration(appName="springsprout", port=8090)
@DataConfiguration(dataType=DataType.XML, location="integration/member/testData.xml")
public class MemberWebTest {

	@WebTest
	public void memberPages(){
		WebDriver driver = new InternetExplorerDriver();
		driver.get("http://localhost:8090/springsprout/member/list.do");
		MemberListPage listPage = PageFactory.initElements(driver, MemberListPage.class);
		assertEquals(2, listPage.getTableRows());

		MemberAddPage addPage = listPage.toAddForm();
		assertNotNull(addPage);

		MemberAddPage failedAddPage = addPage.addFail();
		assertNotNull(failedAddPage);
		assertEquals("required", failedAddPage.getEmailError().getText());
		assertEquals("required", failedAddPage.getPasswordError().getText());

		listPage = failedAddPage.addSuccess();
		assertNotNull(listPage);
		assertEquals(3, listPage.getTableRows());

		//<member id="100" name="test" email="test@data.me" password="123" />
		MemberViewPage viewPage = listPage.veiwMember("test@data.me");
		assertTrue(viewPage.contains("test"));
		assertTrue(viewPage.contains("test@data.me"));

		listPage = viewPage.toListPage();
		assertEquals(3, listPage.getTableRows());

		viewPage = listPage.veiwMember("test@data.me");
		listPage = viewPage.deleteMember();
		assertEquals(2, listPage.getTableRows());

		//<member id="101" name="toby" email="toby@epril.com" password="123" />
		viewPage = listPage.veiwMember("toby@epril.com");
		assertTrue(viewPage.contains("toby"));
		assertTrue(viewPage.contains("toby@epril.com"));

		MemberUpdatePage updatePage = viewPage.toUpdateForm();
		assertEquals("toby", updatePage.getName().getValue());
		assertEquals("toby@epril.com", updatePage.getEmail().getValue());

		updatePage = updatePage.updateFail();
		assertEquals("required", updatePage.getNameError().getText());

		listPage = updatePage.updateSuccess();
		assertTrue(listPage.contains("Toby Lee"));
	}

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
