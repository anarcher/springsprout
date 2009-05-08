package web;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.container.tomcat.Tomcat6xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.Tomcat6xStandaloneLocalConfiguration;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import springsprout.member.MemberRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "memberContext.xml")
public class MemberWebIntegTest {

	InstalledLocalContainer container;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@Ignore
	public void listMember() throws Exception {
		insertXMLData();
		assertEquals(2, memberRepository.getMemberList().size());

		Deployable war = new WAR("target/springsprout.war");
		LocalConfiguration configuration = new Tomcat6xStandaloneLocalConfiguration("target/springsprout");
		configuration.addDeployable(war);
		container = new Tomcat6xInstalledLocalContainer(configuration);
		container.setHome("c:/apps/apache-tomcat-6.0.18");
		container.start();

		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/springsprout/member/list.do");

		// logging
		System.out.println(driver.getTitle());
		assertEquals(2, memberRepository.getMemberList().size());
		System.out.println(driver.getPageSource());

		WebElement element = driver.findElement(By.linkText("keesun@whiteship.me"));
		assertNotNull(element);
		element.getAttribute("href");
		container.stop();
	}

	private void insertXMLData() throws IOException, DataSetException, DatabaseUnitException, SQLException {
		InputStream sourceStream = new ClassPathResource("testData.xml", getClass()).getInputStream();
		IDataSet dataset = new FlatXmlDataSet(sourceStream);
		DatabaseOperation operation = DatabaseOperation.CLEAN_INSERT;
		operation.execute(new DatabaseConnection(DataSourceUtils.getConnection(dataSource)), dataset);
	}

}
