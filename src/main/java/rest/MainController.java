package rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data.LostItem;
import data.User;
import data.accessors.Accessor;
import data.accessors.AuthAccessor;
import data.accessors.LostItemAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ankit on 10/10/2016.
 * This class serves more as a proof of concept. Implementation is not there yet because we recently decided to use Spring
 * to build our RESTful service. The idea behind the endpoints below is to just show how it will work in general with Spring. Of course,
 * there will be many more endpoints in our application for the different screens and behaviors users can excpet to access.
 */


@RestController
public class MainController {
    @RequestMapping("/")
    public String hi() {
        return "Hi this is an endpoint, lol hi Ankit";
    }

    @RequestMapping("/login/{userID}")
    public String login(@PathVariable String userID) {
        /*
        This method will handle the code to check the userID. The user will enter id and password, and this method will query
        our database to ensure that the user login is indeed valid and the user is a registered user.
         */
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

    @RequestMapping(value = "/lostItems", method = RequestMethod.GET)
    public String getAllLostItems(){
        LostItemAccessor access = new LostItemAccessor();
        List<LostItem> lostItems = access.getAllLostItemsWithTags();
        JsonArray output = new JsonArray();
        lostItems.forEach(item -> output.add(item.createJSON()));
        return output.toString();
    }

    @RequestMapping(value = "/lostItemSubmission", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String lostItemSubmissionActivity(@RequestBody String message){
        JsonElement lostItemJson = new JsonParser().parse(message);
        LostItem lostItem = new LostItem(lostItemJson);
        LostItemAccessor access = new LostItemAccessor();
        access.commitLostItemWithTags(lostItem);
        JsonObject response = new JsonObject();
        response.addProperty("status","Success");
        return response.toString();
    }

}
