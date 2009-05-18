package integration.member;

import static org.junit.Assert.*;
import integration.pageobject.MemberAddPage;
import integration.pageobject.MemberListPage;
import integration.pageobject.MemberUpdatePage;
import integration.pageobject.MemberViewPage;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;
import org.opensprout.webtest.WebTestRunner;
import org.opensprout.webtest.configuration.DataConfiguration;
import org.opensprout.webtest.configuration.DataType;
import org.opensprout.webtest.configuration.WarConfiguration;
import org.opensprout.webtest.configuration.WebTest;

@RunWith(WebTestRunner.class)
@WarConfiguration(appName="springsprout")
@DataConfiguration(dataType=DataType.XML, location="integration/member/testData.xml")
public class MemberWebTest {

	@WebTest
	public void memberPages(){
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/springsprout/member/list.do");
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

}
