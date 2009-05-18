package integration.pageobject;

import org.openqa.selenium.How;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MemberViewPage {

	private WebDriver driver;

	@FindBy(how=How.LINK_TEXT, using="수정")
	private WebElement updateButton;

	@FindBy(how=How.LINK_TEXT, using="목록으로")
	private WebElement listButton;

	@FindBy(how=How.LINK_TEXT, using="삭제")
	private WebElement deketeButton;

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
		deketeButton.click();
		return PageFactory.initElements(driver, MemberListPage.class);
	}

	public MemberListPage toListPage() {
		listButton.click();
		return PageFactory.initElements(driver, MemberListPage.class);
	}

}
