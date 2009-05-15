package springsprout.test.web;

import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import springsprout.test.exception.TestDataDeleteException;
import springsprout.test.exception.TestDataInputException;
import springsprout.test.exception.WarDeployingException;
import springsprout.test.exception.WarPackagingException;
import springsprout.test.web.annotation.DataConfiguration;
import springsprout.test.web.annotation.WarConfiguration;
import springsprout.test.web.annotation.WebTest;

public class WebTestRunnerStub extends BlockJUnit4ClassRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	String testMethodName;
	private DataManager dataManager;
	private WarManager warManager;

	public WebTestRunnerStub(Class<?> klass) throws InitializationError {
		super(klass);

		logger.debug("LOADING TEST CONFIGURATIONS....");
		WarConfiguration wc = klass.getAnnotation(WarConfiguration.class);
		if (wc == null)
			throw new IllegalStateException("WEB TEST MUST HAVE WAR CONFIGURATION!");
		logger.debug("SUCCESS! WAR CONFIGURATIONS LOADING....");

		DataConfiguration dc = klass.getAnnotation(DataConfiguration.class);
		if (dc != null) {
			logger.debug("SUCCESS! DATA CONFIGURATIONS LOADING....");
			dataManager = createDataManager(dc);
		}

		warManager = createWarManager(wc);
	}

	private WarManager createWarManager(WarConfiguration wc) {
		return new WarManager(wc);
	}

	private DataManager createDataManager(DataConfiguration dc) {
		return new DataManager(dc);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> annotatedMethods = getTestClass()
				.getAnnotatedMethods(WebTest.class);
		for (FrameworkMethod method : annotatedMethods)
			testMethodName += method.getName();
		return annotatedMethods;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public WarManager getWarManager() {
		return warManager;
	}

	public void setWarManager(WarManager warManager) {
		this.warManager = warManager;
	}

	public String getTestMethodName() {
		return testMethodName;
	}

	@Override
	public void run(RunNotifier arg0) {
		if (hasDataManager())
			runWithDataManager(arg0);
		else
			runWithoutDataManager(arg0);
	}

	private boolean hasDataManager() {
		return dataManager != null;
	}

	private void runWithDataManager(RunNotifier arg0) {
		logger.debug("RUNNING WEB TEST WITH DATA MANAGER....");
		try {
			warManager.packaging();
			warManager.deploy();
			dataManager.insertTestData();
			runTest(arg0);
			dataManager.deleteTestData();
			warManager.undeploy();
		} catch (WarPackagingException e) {
			throw e;
		} catch (WarDeployingException e) {
			throw e;
		} catch (TestDataInputException e) {
			throw e;
		}catch (RuntimeException e) { // test, delete, undeploy
			try {
				logger.debug("TEST ERROR");
				dataManager.deleteTestData();
			} finally {
				warManager.undeploy();
			}
		}
	}

	private void runWithoutDataManager(RunNotifier arg0) {
		logger.debug("RUNNING WEB TEST WITHOUT DATA MANAGER....");
		try {
			warManager.packaging();
			warManager.deploy();
			super.run(arg0);
			warManager.undeploy();
		} catch (WarPackagingException e) {
			throw e;
		} catch (WarDeployingException e) {
			throw e;
		} catch (RuntimeException e) { // test, undeploy
			try{
				logger.debug("TEST ERROR");
			} finally {
				warManager.undeploy();
			}
		}
	}

	private void runTest(RunNotifier notifier) {
		EachTestNotifier testNotifier= new EachTestNotifier(notifier,
				getDescription());
		try {
			Statement statement= classBlock(notifier);
			statement.evaluate();
		} catch (AssumptionViolatedException e) {
			testNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			testNotifier.addFailure(e);
		}
	}
}
