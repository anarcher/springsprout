package springsprout.test.web;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import springsprout.test.exception.TestDataDeleteException;
import springsprout.test.exception.TestDataInputException;
import springsprout.test.exception.TestException;
import springsprout.test.web.annotation.WebTest;
import springsprout.test.web.annotation.WebTestConfiguration;

public class WebTestRunner extends BlockJUnit4ClassRunner {

	private DataManager dataManager;
	private WarManager warManager;

	public WebTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
		WebTestConfiguration wtc = klass.getAnnotation(WebTestConfiguration.class);
		if(wtc == null)
			throw new IllegalStateException();

		dataManager = createDataManager((String)getValue(wtc, "testDataLocation"));
		warManager = createWarManager((String)getValue(wtc, "appName"));
	}

	private Object getValue(Annotation annotation, String attributeName) {
		try {
			return annotation.annotationType().getDeclaredMethod(attributeName, new Class[0]).invoke(annotation);
		}
		catch (Exception ex) {
			return null;
		}
	}

	private WarManager createWarManager(String appName) {
		return new WarManager(appName);
	}

	private DataManager createDataManager(String testDataLocation) {
		return new DataManager(testDataLocation);
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
		try {
			warManager.packaging();
			warManager.deploy();
			dataManager.insertTestData();
			super.run(arg0);
		} catch (TestDataInputException e) {
			throw new TestException("TEST ERROR WHILE INPUT TEST DATA!");
		} finally {
			try {
				dataManager.deleteTestData();
			} catch (TestDataDeleteException e) {
				throw new TestException("TEST ERROR WHILE DELETE TEST DATA!");
			} finally {
				warManager.undeploy();
			}
		}
	}
}
