package springsprout.test.web;

import java.util.List;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import springsprout.test.exception.TestDataDeleteException;
import springsprout.test.exception.TestDataInputException;
import springsprout.test.exception.WarDeployingException;
import springsprout.test.exception.WebTestException;
import springsprout.test.web.annotation.DataConfiguration;
import springsprout.test.web.annotation.WarConfiguration;
import springsprout.test.web.annotation.WebTest;

public class WebTestRunner extends BlockJUnit4ClassRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DataManager dataManager;
	private WarManager warManager;

	public WebTestRunner(Class<?> klass) throws InitializationError {
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
		return getTestClass().getAnnotatedMethods(WebTest.class);
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
			super.run(arg0);
		} catch (TestDataInputException e) {
			try {
				dataManager.deleteTestData();
				throw new WebTestException("TEST ERROR WHILE INPUT TEST DATA!");
			} catch (TestDataDeleteException e2) {
				throw new WebTestException("TEST ERROR WHILE DELETE TEST DATA!");
			} finally {
				warManager.undeploy();
			}
		} catch (WarDeployingException e) {
			throw new WebTestException("TEST ERROR WHILE DEPLOYING WAR!");
		}
	}

	private void runWithoutDataManager(RunNotifier arg0) {
		logger.debug("RUNNING WEB TEST WITHOUT DATA MANAGER....");
		try {
			warManager.packaging();
			warManager.deploy();
			super.run(arg0);
			warManager.undeploy();
		} catch (WebTestException e) {
			logger.debug("ERROR WHEN TEST WITHOUT DATA MANAGER....");
			throw new WebTestException();
		}
	}

}
