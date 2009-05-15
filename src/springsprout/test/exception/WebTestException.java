package springsprout.test.exception;

import org.junit.runner.notification.StoppedByUserException;

public class WebTestException extends RuntimeException {

	public WebTestException() {
	}

	public WebTestException(String message) {
		super(message);
	}

	public WebTestException(Throwable e) {
		super(e);
	}
}
