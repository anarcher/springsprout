package springsprout.member.support;

public class OrderParam {

	private String field;

	private String direction;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		String result = "";

		result += "o_field=";
		if (field != null)
			result += field;

		result += "&o_direction=";
		if (direction != null)
			result += direction;

		return result;
	}

}
