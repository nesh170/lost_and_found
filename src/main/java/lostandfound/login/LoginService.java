package lostandfound.login;

import data.User;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;
import org.springframework.http.*;
import lostandfound.exceptions.AuthException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James Mosca on 11/14/16.
 */

@org.springframework.stereotype.Service
public class LoginService extends Service{

    public StdResponse processLoginResponse(ResponseEntity<String> response, String authToken) {
        String[] bodyArr = response.getBody().split(",");
        HashMap<String, String> responseMap = new HashMap<String, String>();
        for (int i = 0; i < bodyArr.length; i++) {
            if (bodyArr[i].contains(":")) {
                String[] association = bodyArr[i].split(":");
                String key = association[0].trim().replace("\"", "");
                String value = association[1].trim().replace("\"", "");
                if (!responseMap.containsKey(key)) {
                    responseMap.put(key, value);
                }
            }
        }
        User currentUser = buildUser(responseMap, authToken);
        User storedUser;
        try {
            storedUser = authAccessor.getUserByUniqueID(currentUser.uniqueId);
        } catch(NullPointerException e) {
            authAccessor.createUser(currentUser);
            storedUser = currentUser;
        }
        if (currentUser.isSameUser(storedUser.uniqueId)) {
            authAccessor.updateAuthToken(storedUser, authToken);
        }
        return new StdResponseWithBody(200,true,"Successfully Stored Auth Credentials to Database",currentUser);
    }

    private User buildUser(Map<String, String> map, String authToken) {
        String netId = map.get("netid");
        String uniqueId = map.get("duDukeID");
        String name = map.get("displayName");
        String email = map.get("mail");
        if (netId.isEmpty() || netId.equals("")) {
            throw new AuthException("A valid netid could not be found");
        }
        if (uniqueId.isEmpty() || uniqueId.equals("")) {
            throw new AuthException("A valid uniqueID could not be found");
        }
        if (name.isEmpty() || name.equals("")) {
            throw new AuthException("A valid name could not be found");
        }
        if (email.isEmpty() || email.equals("")) {
            throw new AuthException("A valid email could not be found");
        }
        if (authToken.isEmpty() || authToken.equals("")) {
            throw new AuthException("A valid login token could not be found");
        }
        User user = new User(name, uniqueId, netId, email, authToken);
        return user;
    }

    public ResponseEntity<String> createColabApiCall(String access_token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + access_token);
        headers.add("x-api-key", "lost-and-found");

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String apiURL = "https://api.colab.duke.edu/identity/v1/";

        ResponseEntity<String> response = restTemplate.exchange(apiURL, HttpMethod.GET, entity, String.class);
        if (response.getStatusCodeValue() != 200) {
            throw new AuthException("The request for the user info was bad");
        }
        return response;
    }

}
