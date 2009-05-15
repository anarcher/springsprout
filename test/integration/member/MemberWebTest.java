package integration.member;

import org.junit.runner.RunWith;

import springsprout.test.web.WebTestRunner;
import springsprout.test.web.annotation.WarConfiguration;
import springsprout.test.web.annotation.WebTest;

@RunWith(WebTestRunner.class)
@WarConfiguration(appName="springsprout")
public class MemberWebTest {

	@WebTest
	public void crud(){

	}

}
