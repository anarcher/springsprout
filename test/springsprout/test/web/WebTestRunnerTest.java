package springsprout.test.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.ContextConfiguration;

import springsprout.test.web.WebTestRunner;
import springsprout.test.web.annotation.WebTest;
import springsprout.test.web.annotation.WebTestConfiguration;

@RunWith(WebTestRunner.class)
@WebTestConfiguration(testDataLocation = "/web/testData.xml", appName = "springsprout")
public class WebTestRunnerTest {

	WebTestRunner runner;

	@Before
	public void setUp() throws InitializationError{
		runner = new WebTestRunner(this.getClass());
	}

	@WebTest
	public void findWebTest() throws Exception {
		runner.computeTestMethods();
		assertTrue(runner.getTestMethodName().contains("webTestA"));
		assertTrue(runner.getTestMethodName().contains("webTestB"));
		assertFalse(runner.getTestMethodName().contains("testB"));
	}

	@WebTest
	@Ignore
	public void runWebTest() throws Exception {
		WarManager mockWarManager = mock(WarManager.class);
		DataManager mockDataManager = mock(DataManager.class);
		RunNotifier mockRN = mock(RunNotifier.class);

		runner.setWarManager(mockWarManager);
		runner.setDataManager(mockDataManager);

//		mockWarManager.packaging();
//		mockWarManager.deploy();
//		mockDataManager.insertTestData();
//		mockDataManager.deleteTestData();
//		mockWarManager.undeploy();

		runner.run(mockRN);

		verify(mockWarManager).packaging();
		verify(mockWarManager).deploy();
		verify(mockDataManager).insertTestData();
		verify(mockDataManager).deleteTestData();
		verify(mockWarManager).undeploy();
	}

	@Test
	public void testB(){}

	@WebTest
	public void webTestA(){System.out.println("webTestA");}

	@WebTest
	public void webTestB(){System.out.println("webTestB");}

	@WebTest
	@Ignore
	public void webTestC(){System.out.println("webTestC");}

}
