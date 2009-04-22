package springsprout.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springsprout.domain.Member;
import springsprout.member.support.MemberContext;

@Service
@Transactional
public class MemberService {

	@Autowired
	MemberRepository repository;

	public void add(Member member) {
		repository.add(member);
	}

	@Transactional(readOnly=true)
	public List<Member> getMemberList() {
		return repository.getMemberList();
	}

	public Member getMemberById(int id) {
		return repository.getMemberById(id);
	}

	public void deleteById(int id) {
		repository.delete(id);
	}

	public void update(Member member) {
		repository.update(member);
	}

	public List<Member> getMemberListByContext(MemberContext context) {
		context.setTotalRowsCount(repository.getTotalRowsCount(context.getSearchParam()));
		return repository.getMemberListByContext(context);
	}

}
