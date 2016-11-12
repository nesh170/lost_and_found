package lostandfound.dukecard;

import lostandfound.requestmodels.FoundDukeCardRequest;
import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dukecard")
public class DukeCardController extends Controller {

    @Autowired
    private DukeCardService dukeCardService;

    @RequestMapping(value = "/createLostDukeCard", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity add(@RequestBody FoundDukeCardRequest request) {
        pre(request);
        return wrap(dukeCardService.createLostDukeCard(request));
    }

}
