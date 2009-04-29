package springsprout.member.support;

import springsprout.common.System;
import springsprout.web.URLBuilder;

public class SearchParam {

	private String name;

	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		URLBuilder builder = new URLBuilder(System.ENCODING);
		builder.addParameter("s_name", name, "");
		builder.addParameter("s_email", email, "");
		return builder.toString();
	}

}
