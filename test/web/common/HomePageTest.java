package web.common;

import static org.junit.Assert.*;

import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.container.tomcat.Tomcat6xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.Tomcat6xStandaloneLocalConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HomePageTest {

	InstalledLocalContainer container;

	@Test
	@Ignore
	public void loginLink() throws Exception {
		Deployable war = new WAR("target/springsprout.war");
		LocalConfiguration configuration = new Tomcat6xStandaloneLocalConfiguration("target/springsprout");
		configuration.addDeployable(war);
		container = new Tomcat6xInstalledLocalContainer(configuration);
		container.setHome("c:/apps/apache-tomcat-6.0.18");
		container.start();

		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/springsprout/index.do");
		WebElement element = driver.findElement(By.linkText("Login"));
		assertNotNull(element);
		assertEquals("/login.do", element.getAttribute("href"));

		container.stop();
	}
}
