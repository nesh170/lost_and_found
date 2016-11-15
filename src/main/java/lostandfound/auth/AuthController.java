package lostandfound.auth;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import lostandfound.exceptions.AuthException;

/**
 * Created by jimmymosca on 11/14/16.
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

    @ResponseBody
    @RequestMapping("/loginsuccess")
    public String loginSuccess(@RequestParam String access_token) {
        /*
        This method will get the access token then request netID from colab API
        Then redirects to welcome page
         */
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + access_token);
        headers.add("x-api-key", "lost-and-found");

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String apiURL = "https://api.colab.duke.edu/identity/v1/";

        ResponseEntity<String> response = restTemplate.exchange(apiURL, HttpMethod.GET, entity, String.class);
        String[] bodyArr = response.getBody().split(",");
        String netID = "";
        for (int i = 0; i < bodyArr.length; i++) {
            if (bodyArr[i].contains("netid")) {
                String[] association = bodyArr[i].split(":");
                netID = association[1].trim().replace("\"", "");
            }
        }
        if (netID.equals("")) {
            throw new AuthException("A valid netid could not be found");
        }
        if (response.getStatusCodeValue() != 200) {
            throw new AuthException("The request for the netID was bad");
        }
        return netID;
    }

    @RequestMapping("/authconfirm")
    public String authconfirm(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "authorize";
    }

    @RequestMapping(value = "/loginfailure")
    public String loginFailure(@RequestParam(value="status", required=false, defaultValue="World") String status, Model model) {
        model.addAttribute("status", status);
        return "index";
    }

    @RequestMapping("/loginwelcome")
    /*
    Show the welcome/home page
     */
    public String loginwelcome(@RequestParam(value="netid", required=false, defaultValue="NOT FOUND") String netid) {
        return "loginwelcome";

    }

}
