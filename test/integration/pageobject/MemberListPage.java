package integration.pageobject;

import org.openqa.selenium.WebDriver;

public class MemberListPage {

	private WebDriver driver;

	public MemberListPage(WebDriver driver) {
		this.driver = driver;
	}

	public MemberViewPage veiwMember(int id) {
		driver.navigate().to("http://localhost:8080/springsprout/member/" + id);
		return new MemberViewPage(driver);
	}
}
