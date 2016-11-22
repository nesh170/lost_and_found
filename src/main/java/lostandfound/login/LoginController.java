package lostandfound.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

import lostandfound.exceptions.AuthException;

/**
 * Created by jimmymosca on 11/14/16.
 */

@RequestMapping("/")
@RestController
public class LoginController {

    @Autowired
    private LoginService service;

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
        if (response.getStatusCodeValue() != 200) {
            throw new AuthException("The request for the user info was bad");
        }
        service.processLoginResponse(response, access_token);
        return response.toString();
    }

    @RequestMapping("/authconfirm")
    public String authconfirm(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "authorize";
    }

    @RequestMapping(value = "/loginfailure")
    public String loginFailure(@RequestParam(value="status", required=false, defaultValue="World") String status, Model model) {
        model.addAttribute("status", status);
        return "Login failed";
    }

    @RequestMapping("/loginwelcome")
    public String loginwelcome(@RequestParam(value="netid", required=false, defaultValue="NOT FOUND") String netid) {
        /*
        Show the welcome/home page
        */
        return "loginwelcome";

    }

}
