package web;

import java.io.InputStream;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import springsprout.test.exception.TestDataInputException;

public class DataManager {

	public void insertTestData() {
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
	}

}
