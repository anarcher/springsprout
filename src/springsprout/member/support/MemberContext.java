package springsprout.member.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import springsprout.paging.PageParam;

@Component
public class MemberContext {

	PageParam pageParam;

	OrderParam orderParam;

	SearchParam searchParam;

	public PageParam getPageParam() {
		return pageParam;
	}

	public OrderParam getOrderParam() {
		return orderParam;
	}

	public SearchParam getSearchParam() {
		return searchParam;
	}

	private void bindOrderParam(HttpServletRequest request)
			throws ServletRequestBindingException {
		OrderParam orderParam = new OrderParam();
		orderParam.setDirection(ServletRequestUtils.getStringParameter(request,
				"o_direction"));
		orderParam.setField(ServletRequestUtils.getStringParameter(request,
				"o_field"));
		this.orderParam = orderParam;
	}

	private void bindSearchParam(HttpServletRequest request)
			throws ServletRequestBindingException {
		SearchParam searchParam = new SearchParam();
		searchParam.setEmail(ServletRequestUtils.getStringParameter(request,
				"s_email"));
		searchParam.setName(ServletRequestUtils.getStringParameter(request,
				"s_name"));
		this.searchParam = searchParam;
	}

	private void bindPageParam(HttpServletRequest request) {
		PageParam pageParam = new PageParam();
		pageParam.setPage(ServletRequestUtils.getIntParameter(request,
				"p_page", PageParam.DEFAULT_PAGE));
		pageParam.setSize(ServletRequestUtils.getIntParameter(request,
				"p_size", PageParam.DEFAULT_SIZE));
		this.pageParam = pageParam;
	}

	public void bindParams(HttpServletRequest request)
			throws ServletRequestBindingException {
		bindOrderParam(request);
		bindSearchParam(request);
		bindPageParam(request);
	}

	public void setTotalRowsCount(int totalRowsCount) {
		pageParam.setTotalRowsCount(totalRowsCount);
	}

	public String getRedirectToListURL() {
		return "redirect:/member/list.do?" + getAllParam();
	}

	public String getAllParam(){
		return pageParam + "&" + searchParam + "&" + orderParam;
	}

}
