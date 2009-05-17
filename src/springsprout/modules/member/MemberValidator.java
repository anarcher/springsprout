package springsprout.modules.member;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import springsprout.domain.Member;

@Component
public class MemberValidator {

	public void validate(Member member, Errors errors) {
		if (!StringUtils.hasLength(member.getName())) {
			errors.rejectValue("name", "required", "required");
		}
		if (!StringUtils.hasLength(member.getEmail())) {
			errors.rejectValue("email", "required", "required");
		}
		if (!StringUtils.hasLength(member.getPassword())) {
			errors.rejectValue("password", "required", "required");
		}
	}
}
