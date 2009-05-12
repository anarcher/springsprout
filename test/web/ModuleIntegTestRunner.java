package web;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sql.DataSource;

import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.container.deployer.Deployer;
import org.codehaus.cargo.container.tomcat.Tomcat6xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.Tomcat6xStandaloneLocalConfiguration;
import org.codehaus.cargo.container.tomcat.TomcatCopyingInstalledLocalDeployer;
import org.codehaus.cargo.container.tomcat.internal.TomcatManager;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import springsprout.test.exception.TestDataInputException;
import springsprout.test.exception.WARDeployingException;
import springsprout.test.exception.WARPackgingException;

public class ModuleIntegTestRunner {

	@Test
	@Ignore
	public void testname() throws Exception {

		// WAR 패키징 OK
		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt
					.exec("/apps/apache-maven-2.0.10/bin/mvn.bat package");

			ResultPrint errprint = new ResultPrint(p.getErrorStream());
			ResultPrint okprint = new ResultPrint(p.getInputStream());

			errprint.start();
			okprint.start();

			if (p.waitFor() == 0)
				System.out.println("WAR PACKAGING OK....");
		} catch (Exception e) {
			e.printStackTrace();
			throw new WARPackgingException();
		}

		// 서버에 deploy using Cargo TomcatManager OK
		TomcatManager manager;
		String appList;
		try {
			manager = new TomcatManager(new URL("http://localhost:8080/manager/"));
			manager.deploy("/springsprout", new URL("file:target/springsprout.war"), true);
			appList = manager.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WARDeployingException();
		}
		assertTrue(appList.contains("/springsprout:running:0:springsprout"));

		// 테스트 데이터 넣기 OK
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
			DataSource dataSource = applicationContext.getBean("dataSource",
					DataSource.class);
			InputStream sourceStream = new ClassPathResource(
					"/web/testData.xml").getInputStream();
			IDataSet dataset = new FlatXmlDataSet(sourceStream);
			DatabaseOperation operation = DatabaseOperation.CLEAN_INSERT;
			operation.execute(new DatabaseConnection(DataSourceUtils
					.getConnection(dataSource)), dataset);
			dataSource.getConnection().commit();

			System.out.println("TEST DATA INPUT OK....");

		} catch (Exception e) {
			e.printStackTrace();
			throw new TestDataInputException();
		}

		// 테스트
		System.out.println("test");

		// 테스트 데이터 삭제

		// 서버에서 undeploy
//		manager.undeploy("/springsprout");
//		appList = manager.list();
//		assertFalse(appList.contains("/springsprout:running:0:springsprout"));
	}

}
