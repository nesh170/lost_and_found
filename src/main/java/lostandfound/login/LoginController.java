package lostandfound.login;

import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jimmymosca on 11/14/16.
 */

@RestController
public class LoginController extends Controller {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/loginsuccess")
    public ResponseEntity loginSuccess(@RequestParam String access_token) {
        ResponseEntity<String> response = loginService.createColabApiCall(access_token);
        return wrap(loginService.processLoginResponse(response, access_token));
    }


}
