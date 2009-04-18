package springsprout.member;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import springsprout.domain.Member;
import springsprout.member.support.OrderParam;
import springsprout.member.support.SearchParam;
import springsprout.paging.PageParam;

@Repository
public class MemberRepository {

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void add(Member member) {
		getCurrentSession().save(member);
	}

	@SuppressWarnings("unchecked")
	public List<Member> getMemberList() {
		return getCurrentSession().createQuery("from Member order by name").list();
	}

	public Member getMemberById(int id) {
		return (Member) getCurrentSession().load(Member.class, id);
	}

	public void delete(int id) {
		getCurrentSession().delete(getMemberById(id));
	}

	public void update(Member member) {
		getCurrentSession().update(member);
	}

	public int getTotalRowsCountBy(SearchParam searchParam) {
		Criteria c = getCurrentSession().createCriteria(Member.class);

		applySearchParam(searchParam, c);

		return c.list().size();
	}

	@SuppressWarnings("unchecked")
	public List<Member> getMemberListByPageAndSearchParam(PageParam pageParam,
			SearchParam searchParam, OrderParam orderParam) {
		Criteria c = getCurrentSession().createCriteria(Member.class);

		applySearchParam(searchParam, c);
		applyPageParam(pageParam, c);
		applyOrderParam(orderParam, c);

		return c.list();
	}

	private void applySearchParam(SearchParam searchParam, Criteria c) {
		if (StringUtils.hasText(searchParam.getName())) {
			c.add(Restrictions.ilike("name", searchParam.getName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.hasText(searchParam.getEmail())) {
			c.add(Restrictions.ilike("email", searchParam.getEmail(), MatchMode.ANYWHERE));
		}
	}

	private void applyOrderParam(OrderParam orderParam, Criteria c) {
		String field = orderParam.getField();
		if(!StringUtils.hasText(field))
			c.addOrder(Order.asc("id")); //apply default
		else if(orderParam.getDirection().equals("desc"))
			c.addOrder(Order.desc(field));
		else
			c.addOrder(Order.asc(field));
	}

	private void applyPageParam(PageParam pageParam, Criteria c) {
		c.setFirstResult(pageParam.getFirstRowNumber() - 1);
		c.setMaxResults(pageParam.getSize());
	}

}
