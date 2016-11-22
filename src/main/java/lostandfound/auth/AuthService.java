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
        String[] parameterVals = {"token",generalProperties.getProperty("auth.redirect.url"), "lost-and-found", "basic identity:netid:read", "12345"};
        String[] parameterValsTest = {"token", generalProperties.getProperty("auth.redirect.url.local"), "lost-and-found", "basic identity:netid:read", "12345"};
        String[] parameterValsDev = {"token", generalProperties.getProperty("auth.redirect.url.dev"), "lost-and-found", "basic identity:netid:read", "12345"};
        if (test.equals("test")) {
            parameterVals = parameterValsTest;
        }
        else if(test.equals("dev")) {
            parameterVals = parameterValsDev;
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
