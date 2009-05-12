package web;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.apache.maven.embedder.Configuration;
import org.apache.maven.embedder.ConfigurationValidationResult;
import org.apache.maven.embedder.DefaultConfiguration;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.codehaus.cargo.container.tomcat.internal.TomcatManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import springsprout.test.exception.WARDeployingException;
import springsprout.test.exception.WARPackgingException;
import springsprout.test.exception.WARUnDeployingException;

public class WarManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	TomcatManager manager;

	public WarManager() {
		try {
			manager = new TomcatManager(new URL(
					"http://localhost:8080/manager/"));
		} catch (MalformedURLException e) {
			logger.error("TOMCAT MANAGER SETTING ERROR", e);
			throw new IllegalPathStateException();
		}
	}

	public void packaging() {
		Configuration configuration = new DefaultConfiguration()
			.setClassLoader(Thread.currentThread().getContextClassLoader());

		ConfigurationValidationResult validationResult = MavenEmbedder
				.validateConfiguration(configuration);

		if (validationResult.isValid()) {
			try {
				MavenEmbedder embedder = new MavenEmbedder(configuration);
				MavenExecutionRequest request = new DefaultMavenExecutionRequest();
				request.setGoals(Arrays.asList(new String[] { "war:war" }));
				MavenExecutionResult result = embedder.execute(request);
				if (result.hasExceptions()) {
					System.out.println("ddcc");
					logger.debug(((Exception) result.getExceptions().get(0))
							.getMessage());
					throw new WARPackgingException();
				}
			} catch (MavenEmbedderException e) {
				e.printStackTrace();
				throw new WARPackgingException();
			}
		} else {
			throw new WARPackgingException();
		}
	}

	public boolean deploy() {
		String appList = "";
		try {
			manager.deploy("/springsprout", new URL(
					"file:target/springsprout.war"), true);
			manager.start("/springsprout");
			appList = manager.list();
		} catch (Exception e) {
			logger.error("WAR DEPLOYING ERROR", e);
			throw new WARDeployingException();
		}
		return appList.contains("/springsprout:running:0:springsprout");
	}

	public boolean undeploy() {
		String appList = "";
		try {
			manager.stop("/springsprout");
			manager.undeploy("/springsprout");
			appList = manager.list();
		} catch (Exception e) {
			logger.error("WAR UNDEPLOYING ERROR", e);
			throw new WARUnDeployingException();
		}
		return !appList.contains("/springsprout:running:0:springsprout");
	}

}
