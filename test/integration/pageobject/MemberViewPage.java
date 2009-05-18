package integration.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MemberViewPage {

	private WebDriver driver;

	private WebElement updateButton;

	private WebElement listButton;

	private WebElement deleteButton;

	public MemberViewPage(WebDriver driver) {
		this.driver = driver;
	}

	public MemberUpdatePage toUpdateForm() {
		updateButton.click();
		return PageFactory.initElements(driver, MemberUpdatePage.class);
	}

	public boolean contains(String value) {
		return driver.getPageSource().contains(value);
	}

	public MemberListPage deleteMember() {
		deleteButton.click();
		return PageFactory.initElements(driver, MemberListPage.class);
	}

	public MemberListPage toListPage() {
		listButton.click();
		return PageFactory.initElements(driver, MemberListPage.class);
	}

}
