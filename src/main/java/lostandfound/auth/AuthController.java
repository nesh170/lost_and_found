package lostandfound.auth;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James Mosca on 11/14/16.
 */

@RequestMapping("/")
@Controller
public class AuthController {

    @RequestMapping(value = "/authenticate/{test}", method = RequestMethod.GET)
    public ModelAndView authenticate(@PathVariable String test) {
        /*
        This method will redirect to OAuth login page
        */
        StringBuilder loginURI = new StringBuilder();
        String oauthURL = "https://oauth.oit.duke.edu/oauth/authorize.php";
        String[] parameterNames = {"response_type=", "redirect_uri=", "client_id=", "scope=", "state="};
        String[] parameterVals = {"token", "http://colab-sbx-122.oit.duke.edu:8080/authconfirm", "lost-and-found", "basic identity:netid:read", "12345"};
        String[] parameterValsTest = {"token", "http://localhost:8080/authconfirm", "lost-and-found", "basic identity:netid:read", "12345"};
        if (test.equals("test")) {
            parameterVals = parameterValsTest;
        }
        loginURI.append(oauthURL);
        loginURI.append('?');
        for (int i = 0; i < parameterNames.length; i++) {
            loginURI.append(parameterNames[i]);
            loginURI.append(parameterVals[i]);
            if (i < parameterNames.length-1) {
                loginURI.append('&');
            }
        }
        return new ModelAndView("redirect:" + loginURI.toString());
    }

}
