package web;

import org.junit.Test;

import springsprout.test.exception.TestDataDeleteException;
import springsprout.test.exception.TestDataInputException;
import springsprout.test.exception.TestException;
import springsprout.test.web.DataManager;
import springsprout.test.web.WarManager;

public class WebIntegTestRunner {

	@Test
	public void testTemplate() {
		WarManager wm = null;
		DataManager dm = null;
		try {
			wm = new WarManager("springsprout");
			dm = new DataManager("/web/testData.xml");
			wm.packaging();
			wm.deploy();
			dm.insertTestData();
			doTest();
		} catch (TestDataInputException e) {
			throw new TestException("TEST ERROR WHILE INPUT TEST DATA!");
		} finally {
			try {
				dm.deleteTestData();
			} catch (TestDataDeleteException e) {
				throw new TestException("TEST ERROR WHILE DELETE TEST DATA!");
			} finally {
				wm.undeploy();
			}
			wm.undeploy();
		}
	}

	private void doTest() {

	}

}
