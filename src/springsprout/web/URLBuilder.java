package springsprout.web;

public class URLBuilder {

	private StringBuilder builder;

	public URLBuilder() {
		builder = new StringBuilder();
	}

	public void addParameter(String parameterName, Integer value, String defaultValue) {
		StringBuilder localBuilder = makeForehead(parameterName);

		if(value != null)
			localBuilder.append(value);
		else if(defaultValue != null)
			localBuilder.append(defaultValue);
		else
			return;

		builder.append(localBuilder.toString());
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
