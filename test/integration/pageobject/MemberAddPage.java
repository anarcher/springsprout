package integration.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MemberAddPage extends MemberForm {

	private WebDriver driver;

	public MemberAddPage(WebDriver driver) {
		this.driver = driver;
	}

	public MemberAddPage addFail() {
		name.sendKeys("keesun");
		submit.submit();
		return PageFactory.initElements(driver, MemberAddPage.class);
	}

	public MemberListPage addSuccess() {
		password.sendKeys("123");
		email.sendKeys("keesun@whiteship.me");
		submit.submit();
		return PageFactory.initElements(driver, MemberListPage.class);
	}

}
