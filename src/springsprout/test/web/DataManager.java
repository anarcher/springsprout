package springsprout.test.web;

import java.io.InputStream;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import springsprout.test.exception.DataManagerSettingException;
import springsprout.test.exception.TestDataDeleteException;
import springsprout.test.exception.TestDataInputException;

public class DataManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	DataSource dataSource;
	IDataSet dataset;
	DatabaseConnection databaseConnection;

	public DataManager(String resourceClassPath) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		dataSource = applicationContext.getBean("dataSource", DataSource.class);
		InputStream sourceStream;
		try {
			sourceStream = new ClassPathResource(resourceClassPath).getInputStream();
			dataset = new FlatXmlDataSet(sourceStream);
			databaseConnection = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
		} catch (Exception e) {
			logger.debug("DATA MANAGER SETTING ERROR", e);
			throw new DataManagerSettingException("DATA MANAGER SETTING ERROR");
		}
	}

	public void insertTestData() {
		try {
			DatabaseOperation operation = DatabaseOperation.CLEAN_INSERT;
			operation.execute(databaseConnection, dataset);
			dataSource.getConnection().commit();
			logger.debug("TEST DATA INPUT OK....");
		} catch (Exception e) {
			logger.debug("TEST DATA INPUT ERROR", e);
			throw new TestDataInputException("TEST DATA INPUT ERROR");
		}
	}

	public void deleteTestData() {
		try {
			DatabaseOperation operation = DatabaseOperation.DELETE_ALL;
			operation.execute(databaseConnection, dataset);
			logger.debug("TEST DATA DELETE OK....");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("TEST DATA DELETE ERROR", e);
			throw new TestDataDeleteException("TEST DATA DELETE ERROR");
		}
	}

}
