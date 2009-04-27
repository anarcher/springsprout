package springsprout.member.support;

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
		String result = "";

		result += "s_name=";
		if (name != null)
			result += name;

		result += "&s_email=";
		if (email != null)
			result += email;

		return result;
	}

}
