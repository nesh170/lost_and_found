package lostandfound.auth;

import data.User;
import lostandfound.std.Service;
import org.springframework.http.ResponseEntity;
import lostandfound.exceptions.AuthException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James Mosca on 11/14/16.
 */

@org.springframework.stereotype.Service
public class AuthService extends Service{

    protected void processLoginResponse(ResponseEntity<String> response, String authToken) {
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
            throw new AuthException("A valid auth token could not be found");
        }
        User user = new User(name, uniqueId, netId, email, authToken);
        return user;
    }

}
