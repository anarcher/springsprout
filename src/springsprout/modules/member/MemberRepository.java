package springsprout.modules.member;

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

import springsprout.common.web.PageParam;
import springsprout.domain.Member;
import springsprout.modules.member.support.MemberContext;
import springsprout.modules.member.support.OrderParam;
import springsprout.modules.member.support.SearchParam;

@Repository
public class MemberRepository {

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void add(Member member) {
		getCurrentSession().save(member);
	}

	@SuppressWarnings("unchecked")
	public List<Member> getMemberList() {
		return getCurrentSession().createQuery("from Member order by name")
				.list();
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

	private void applySearchParam(SearchParam searchParam, Criteria c) {
		if (StringUtils.hasText(searchParam.getName())) {
			c.add(Restrictions.ilike("name", searchParam.getName(),
					MatchMode.ANYWHERE));
		}
		if (StringUtils.hasText(searchParam.getEmail())) {
			c.add(Restrictions.ilike("email", searchParam.getEmail(),
					MatchMode.ANYWHERE));
		}
	}

	public int getTotalRowsCount(SearchParam searchParam) {
		Criteria c = getCurrentSession().createCriteria(Member.class);

		applySearchParam(searchParam, c);

		return c.list().size();
	}

	private void applyOrderParam(OrderParam orderParam, Criteria c) {
		String field = orderParam.getField();
		if (!StringUtils.hasText(field))
			c.addOrder(Order.asc("id")); // apply default
		else if (orderParam.getDirection().equals("desc"))
			c.addOrder(Order.desc(field));
		else
			c.addOrder(Order.asc(field));
	}

	private void applyPageParam(PageParam pageParam, Criteria c) {
		c.setFirstResult(pageParam.getFirstRowNumber() - 1);
		c.setMaxResults(pageParam.getSize());
	}

	@SuppressWarnings("unchecked")
	public List<Member> getMemberListByContext(MemberContext context) {
		Criteria c = getCurrentSession().createCriteria(Member.class);

		applySearchParam(context.getSearchParam(), c);
		applyPageParam(context.getPageParam(), c);
		applyOrderParam(context.getOrderParam(), c);

		return c.list();
	}

	public void flush() {
		getCurrentSession().flush();
	}

	public void deleteAll() {
		getCurrentSession().createSQLQuery("delete * from Member");
	}

}
