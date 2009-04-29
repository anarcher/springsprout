package springsprout.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class URLBuilder {

	private StringBuilder builder;
	String encoding;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public URLBuilder() {
		this(new java.io.OutputStreamWriter(System.out).getEncoding());
	}

	public URLBuilder(String encoding) {

		builder = new StringBuilder();
		this.encoding = encoding;
	}

	public void addParameter(String parameterName, Object value, String defaultValue) {
		StringBuilder localBuilder = makeForehead(parameterName);

		if(value != null)
			localBuilder.append(value);
		else if(defaultValue != null)
			localBuilder.append(encode(defaultValue));
		else
			return;
		builder.append(localBuilder.toString());
	}

	private String encode(String string)  {
		try {
			return URLEncoder.encode(string, encoding);
		} catch (UnsupportedEncodingException e) {
			logger.debug("[SpringSprout]encoding field's value is: <" + encoding + ">");
			throw new EncodingException();
		}
	}

	private StringBuilder makeForehead(String parameterName) {
		StringBuilder localBuilder = new StringBuilder();

		if(builder.length() > 0)
			localBuilder.append("&");

		localBuilder.append(parameterName);
		localBuilder.append("=");
		return localBuilder;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
