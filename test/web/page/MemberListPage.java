package web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MemberListPage {

	private WebDriver driver;

	public MemberListPage(WebDriver driver) {
		this.driver = driver;
	}

	public MemberViewPage clickLink(int id){
		driver.findElement(By.linkText("keesun@whiteship.me")).click();
		return new MemberViewPage(driver);
	}

}
