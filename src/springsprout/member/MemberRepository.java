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

	public List<Member> getMemberListByPageAndSearchParam(PageParam pageParam,
			SearchParam searchParam) {
		Criteria c = getCurrentSession().createCriteria(Member.class);

		applySearchParam(searchParam, c);

		//apply pageParam
		c.setFirstResult(pageParam.getFirstRowNumber() - 1);
		c.setMaxResults(pageParam.getSize());

		//apply orderParam
		c.addOrder(Order.asc("name")); //TODO

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

	public int getTotalRowsCountBy(SearchParam searchParam) {
		Criteria c = getCurrentSession().createCriteria(Member.class);

		applySearchParam(searchParam, c);

		return c.list().size();
	}

}
