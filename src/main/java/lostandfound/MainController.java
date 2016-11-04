package lostandfound;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data.LostItem;
import data.User;
import data.accessors.AuthAccessor;
import data.accessors.ItemAccessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ankit on 10/10/2016.
 * This class serves more as a proof of concept. Implementation is not there yet because we recently decided to use Spring
 * to build our RESTful service. The idea behind the endpoints below is to just show how it will work in general with Spring. Of course,
 * there will be many more endpoints in our application for the different screens and behaviors users can excpet to access.
 */

@Controller
public class MainController {

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
        This method will get the access token and redirect to front end
         */
        return "Login Succeeded: " + access_token;
    }

    @RequestMapping("/authconfirm")
    public String authconfirm(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "authconfirm";
    }

    @ResponseBody
    @RequestMapping("/loginfailure")
    public String loginFailure(@RequestParam String access_token) {
        /*
        This method will get the access token and redirect to front end
         */
        return "Login Failed: Redirect to the app login page";
    }

    @ResponseBody
    @RequestMapping("/login/{userID}")
    public String login(@PathVariable String userID) {
        AuthAccessor accessor = new AuthAccessor();
        List<String> allUsers = new ArrayList<>();
        accessor.getAllUsers().forEach(user -> allUsers.add(user.createJSON().toString()));
        String allUsersStr = "";
        for(String k:allUsers){
            allUsersStr+=k;
        }
        User user;
        try {
            user = accessor.getUserByUniqueID(userID);
        }
        catch(Exception e){
            return "Login Failed";
        }
        return "Login Succeeded with userID: " + user.createJSON().toString() + allUsersStr;
    }

}
