package integration.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MemberUpdatePage extends MemberForm {

	private WebDriver driver;

	public MemberUpdatePage(WebDriver driver) {
		this.driver = driver;
	}

	public MemberUpdatePage updateFail() {
		name.clear();
		submit.submit();
		return PageFactory.initElements(driver, MemberUpdatePage.class);
	}

	public MemberListPage updateSuccess() {
		name.sendKeys("Toby Lee");
		submit.submit();
		return PageFactory.initElements(driver, MemberListPage.class);
	}

}
