package rest;

import com.google.gson.*;
import data.LostItem;
import data.accessors.Accessor;
import data.accessors.DBManager;
import org.jooq.tools.json.JSONObject;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.web.bind.annotation.*;

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
        return "Login Succeeded with userID: " + userID;
    }

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
