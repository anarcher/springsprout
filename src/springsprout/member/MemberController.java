package springsprout.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import springsprout.domain.Member;
import springsprout.member.support.MemberContext;

@Controller
@SessionAttributes("member")
public class MemberController {

	@Autowired
	MemberService service;

	@Autowired
	MemberValidator validator;

	@Autowired
	MemberContext context;

	// Create
	@RequestMapping(value = "/member/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		Member member = new Member();
		model.addAttribute(member);
		return "member/add";
	}

	@RequestMapping(value = "/member/add", method = RequestMethod.POST)
	public String addForm(Member member, BindingResult result,
			SessionStatus status) {
		validator.validate(member, result);
		if (result.hasErrors()) {
			return "member/add";
		} else {
			service.add(member);
			status.isComplete();
			return "redirect:/member/list.do";
		}
	}

	// Read
	@RequestMapping("/member/list")
	public ModelMap list(HttpServletRequest request,
			HttpServletResponse response) throws ServletRequestBindingException {
		System.out.println("url: "+ request.getQueryString());
		context.bindParams(request);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute(service.getMemberListByContext(context));
		modelMap.addAttribute("c", context);
		return modelMap;
	}

	@RequestMapping("/member/{id}")
	public String view(@PathVariable int id, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		context.bindParams(request);
		model.addAttribute(service.getMemberById(id));
		model.addAttribute("c", context);
		return "member/view";
	}

	// Update
	@RequestMapping(value = "/member/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable int id, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		context.bindParams(request);
		model.addAttribute(service.getMemberById(id));
		model.addAttribute("c", context);
		return "member/update";
	}

	@RequestMapping(value = "/member/update/{id}", method = RequestMethod.POST)
	public String updateForm(Member member, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) throws ServletRequestBindingException {
		context.bindParams(request);
		validator.validate(member, result);
		if (result.hasErrors()) {
			return "member/update";
		} else {
			service.update(member);
			status.isComplete();
			return context.getRedirectToListURL();
		}
	}

	// Delete
	@RequestMapping("/member/delete/{id}")
	public String delete(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) throws ServletRequestBindingException {
		context.bindParams(request);
		service.deleteById(id);
		return context.getRedirectToListURL();
	}

}
