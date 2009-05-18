package integration.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MemberListPage {

	private WebDriver driver;

	private WebElement addButton;

	public WebElement getAddButton() {
		return addButton;
	}

	public void setAddButton(WebElement addButton) {
		this.addButton = addButton;
	}

	public MemberListPage(WebDriver driver) {
		this.driver = driver;
	}

	public MemberAddPage toAddForm() {
		addButton.click();
		return PageFactory.initElements(driver, MemberAddPage.class);
	}

	public int getTableRows() {
		return driver.findElements(By.xpath("//tr")).size() - 1;
	}

	public boolean contains(String value) {
		return driver.getPageSource().contains(value);
	}

	public MemberViewPage veiwMember(String email) {
		driver.findElement(By.linkText(email)).click();
		return PageFactory.initElements(driver, MemberViewPage.class);
	}
}
