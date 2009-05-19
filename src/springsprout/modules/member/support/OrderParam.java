package springsprout.modules.member.support;

import springsprout.common.System;
import springsprout.common.web.URLBuilder;

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
		URLBuilder builder = new URLBuilder(System.ENCODING);
		builder.addParameter("o_field", field, "");
		builder.addParameter("o_direction", direction, "");
		return builder.toString();
	}

}
