package springsprout.member.support;

import springsprout.web.URLBuilder;

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
		URLBuilder builder = new URLBuilder();
		builder.addParameter("o_field", field, "");
		builder.addParameter("o_direction", direction, "");
		return builder.toString();
	}

}
