package rest;

import com.google.gson.*;
import data.LostItem;
import data.accessors.Accessor;
import data.accessors.DBManager;
import org.jooq.tools.json.JSONObject;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Ankit on 10/10/2016.
 * This class serves more as a proof of concept. Implementation is not there yet because we recently decided to use Spring
 * to build our RESTful service. The idea behind the endpoints below is to just show how it will work in general with Spring. Of course,
 * there will be many more endpoints in our application for the different screens and behaviors users can excpet to access.
 */

@Controller
public class MainController {

    @ResponseBody
    @RequestMapping("/")
    public String hi() {
        return "Hi this is an endpoint, lol hi Ankit";
    }

    @RequestMapping(value = "/login/{test}", method = RequestMethod.GET)
    public ModelAndView login(@PathVariable String test) {
        /*
        This method will redirect to OAuth login page
        */
        StringBuilder loginURI = new StringBuilder();
        String oauthURL = "https://oauth.oit.duke.edu/oauth/authorize.php";
        String[] parameterNames = {"response_type=", "redirect_uri=", "client_id=", "scope=", "state="};
        String[] parameterVals = {"token", "http://colab-sbx-122.oit.duke.edu:8080/authconfirm", "lost-and-found", "basic", "12345"};
        String[] parameterValsTest = {"token", "http://localhost:8080/authconfirm", "lost-and-found", "basic", "12345"};
        if (test.equals("test")) {
            parameterVals = parameterValsTest;
        }
        //loginURI.append("redirect:");
        loginURI.append(oauthURL);
        loginURI.append('?');
        for (int i = 0; i < parameterNames.length; i++) {
            loginURI.append(parameterNames[i]);
            loginURI.append(parameterVals[i]);
            if (i < parameterNames.length-1) {
                loginURI.append('&');
            }
        }
        //return "hi";
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
    @RequestMapping(value = "/lostItemSubmission", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String lostItemSubmissionActivity(@RequestBody String message){
        JsonElement lostItemJson = new JsonParser().parse(message);
        LostItem lostItem = new LostItem(
                -1,
                lostItemJson.getAsJsonObject().get("location").getAsString(),
                new Timestamp(new Date().getTime()),
                lostItemJson.getAsJsonObject().get("uniqueID").getAsString(),
                getList(lostItemJson.getAsJsonObject().get("tags").getAsJsonArray().iterator())
        );
        Accessor access = new Accessor();
        access.commitLostItemWithTags(lostItem);
        JsonObject response = new JsonObject();
        response.addProperty("status","Success");
        return response.toString();
    }

    private List<String> getList(Iterator<JsonElement> tags) {
        List<String> list = new ArrayList<>();
        tags.forEachRemaining(tag -> list.add(tag.getAsString()));
        return list;
    }


}
