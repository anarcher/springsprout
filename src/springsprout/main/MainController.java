package springsprout.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // index
    @RequestMapping("/index")
    public void index(){
    }

    // login
    @RequestMapping("/login")
    public void login(){
    }

}
