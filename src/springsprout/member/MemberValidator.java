package springsprout.member;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import springsprout.domain.Member;


@Component
public class MemberValidator {

	public void validate(Member member, Errors errors) {
		String name = member.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("email", "required", "required");
			errors.rejectValue("password", "required", "required");
			errors.rejectValue("name", "required", "required");
		}
	}
}
