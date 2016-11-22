package lostandfound.auth;

import lostandfound.std.Service;

/**
 * Created by James Mosca on 11/14/16.
 */

@org.springframework.stereotype.Service
public class AuthService extends Service{

    public String generateLoginURL(String test) {
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
            if (i < parameterNames.length - 1) {
                loginURI.append('&');
            }
        }
        return loginURI.toString();
    }

}
