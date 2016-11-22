package lostandfound.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/authenticate/{test}", method = RequestMethod.GET)
    public ModelAndView authenticate(@PathVariable String test) {
        /*
        This method will redirect to OAuth login page
        */
        String loginURI = authService.generateLoginURL(test);
        return new ModelAndView("redirect:" + loginURI);
    }

}