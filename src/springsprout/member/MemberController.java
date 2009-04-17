package springsprout.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import springsprout.domain.Member;
import springsprout.member.support.OrderParam;
import springsprout.member.support.SearchParam;
import springsprout.paging.PageParam;

@Controller
@SessionAttributes("member")
public class MemberController {

	@Autowired
	MemberService service;

	@Autowired
	MemberValidator validator;

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
	public ModelMap list(PageParam pageParam, SearchParam searchParam,
			OrderParam orderParam) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute(service
				.getMemberListByPageAndSearchAndOrderParam(pageParam,
						searchParam, orderParam));
		modelMap.addAttribute(pageParam);
		modelMap.addAttribute(searchParam);
		modelMap.addAttribute(orderParam);
		return modelMap;
	}

	@RequestMapping("/member/{id}")
	public String view(@PathVariable int id, Model model, PageParam pageParam,
			SearchParam searchParam, OrderParam orderParam) {
		model.addAttribute(service.getMemberById(id));
		model.addAttribute(pageParam);
		model.addAttribute(searchParam);
		model.addAttribute(orderParam);
		return "member/view";
	}

	// Update
	@RequestMapping(value = "/member/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable int id, Model model,
			PageParam pageParam, SearchParam searchParam, OrderParam orderParam) {
		model.addAttribute(service.getMemberById(id));
		model.addAttribute(pageParam);
		model.addAttribute(searchParam);
		model.addAttribute(orderParam);
		return "member/update";
	}

	@RequestMapping(value = "/member/update/{id}", method = RequestMethod.POST)
	public String updateForm(PageParam pageParam, SearchParam searchParam,
			OrderParam orderParam, Member member, BindingResult result,
			SessionStatus status) {
		validator.validate(member, result);
		if (result.hasErrors()) {
			return "member/update";
		} else {
			service.update(member);
			status.isComplete();
			return redirectURLWithPageAndSearchAndOrderParam(pageParam,
					searchParam, orderParam);
		}
	}

	// Delete
	@RequestMapping("/member/delete/{id}")
	public String delete(@PathVariable int id, PageParam pageParam,
			SearchParam searchParam, OrderParam orderParam) {
		service.deleteById(id);
		return redirectURLWithPageAndSearchAndOrderParam(pageParam, searchParam, orderParam);
	}

	private String redirectURLWithPageAndSearchAndOrderParam(
			PageParam pageParam, SearchParam searchParam, OrderParam orderParam) {
		return redirectURLWithPageAndSearchParam(pageParam, searchParam)
				+ "&field=" + orderParam.getField() + "&direction="
				+ orderParam.getDirection();
	}

	private String redirectURLWithPageAndSearchParam(PageParam pageParam,
			SearchParam searchParam) {
		return pagedListURL(pageParam) + "&name=" + searchParam.getName()
				+ "&email=" + searchParam.getEmail();
	}

	private String pagedListURL(PageParam pageParam) {
		return "redirect:/member/list.do?size=" + pageParam.getSize()
		+ "&page=" + pageParam.getPage();
	}

}
