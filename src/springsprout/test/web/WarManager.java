package springsprout.test.web;

import java.awt.geom.IllegalPathStateException;
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
import org.codehaus.cargo.container.tomcat.internal.TomcatManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import springsprout.test.exception.WarDeployingException;
import springsprout.test.exception.WarPackgingException;
import springsprout.test.exception.WarUnDeployingException;
import springsprout.test.web.annotation.WarConfiguration;

public class WarManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	TomcatManager manager;
	String appBaseName;
	int port;

	public WarManager(WarConfiguration wc) {
		this((String) AnnotationUtils.getValue(wc, "appName"),
				(Integer) AnnotationUtils.getValue(wc, "port"));
	}

	public WarManager(String appName, int port) {
		this.appBaseName = "/" + appName;
		this.port = port;
		try {
			manager = new TomcatManager(new URL(
					"http://localhost:" + port + "/manager/"));
		} catch (MalformedURLException e) {
			logger.debug("TOMCAT MANAGER SETTING ERROR", e);
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
					logger.debug(((Exception) result.getExceptions().get(0))
							.getMessage());
					throw new WarPackgingException("WAR NOT CREATED ERROR");
				}
				logger.debug("WAR PACKAGING OK....");
			} catch (MavenEmbedderException e) {
				logger.debug("MAVEN WAR:WAR ERROR", e);
				throw new WarPackgingException("MAVEN WAR:WAR ERROR");
			}
		} else {
			throw new WarPackgingException("MAVEN CONFIGURATION ERROR");
		}
	}

	public void deploy() {
		String appList = "";
		try {
			manager.deploy(appBaseName, new URL("file:target" + appBaseName
					+ ".war"), true);
			manager.start(appBaseName);
			appList = manager.list();
		} catch (Exception e) {
			logger.debug("WAR DEPLOYING ERROR", e);
			throw new WarDeployingException("WAR DEPLOYING ERROR");
		}
		if (!containAppIn(appList))
			throw new WarDeployingException("WAR NOT DEPOLOYED ERROR");
		logger.debug("WAR DEPLOYING OK....");
	}

	private boolean containAppIn(String appList) {
		return appList.contains(appBaseName + ":running:0:springsprout");
	}

	public void undeploy() {
		String appList = "";
		try {
			manager.stop(appBaseName);
			manager.undeploy(appBaseName);
			appList = manager.list();
		} catch (TomcatManagerException e) {
			if (e.getMessage().contains("No context exitsts"))
				logger.debug("WAR UNDEPLOYING NOT NESSACERY", e);
		} catch (Exception e) {
			logger.debug("WAR UNDEPLOYING ERROR", e);
			throw new WarUnDeployingException("WAR UNDEPLOYING ERROR");
		}
		if (containAppIn(appList))
			throw new WarUnDeployingException("WAR NOT UNDEPLOYED ERROR");
		logger.debug("WAR UNDEPLOYING OK....");
	}
}
