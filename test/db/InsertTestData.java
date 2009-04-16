package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springsprout.domain.Member;


public class InsertTestData {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		for(int i = 0 ; i < 100 ; i++){
			Member member = new Member();
			member.setEmail("whiteship@gmail.com" + i);
			member.setName("keesun" + i);
			member.setPassword("123");
			session.save(member);
		}
		transaction.commit();
		session.close();
	}
}
