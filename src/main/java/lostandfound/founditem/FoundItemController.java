package lostandfound.founditem;

import lostandfound.requestmodels.FoundItemRequest;
import lostandfound.requestmodels.FoundItemSendEmailRequest;
import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foundItem")
public class FoundItemController extends Controller {

    @Autowired
    private FoundItemService foundItemService;

    @RequestMapping(value = "/allItems", method = RequestMethod.GET)
    public ResponseEntity getFoundItems() {
        //TODO request method when the frontend is built so login can happen
        //pre(request);
        return wrap(foundItemService.getAllFoundItemsWithTags());
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity add(@RequestBody FoundItemRequest request) {
        pre(request);
        return wrap(foundItemService.createFoundItem(request));
    }

    @RequestMapping(value = "/allItemsByUser/{uniqueId}", method = RequestMethod.GET)
    public ResponseEntity getFoundItems(@PathVariable String uniqueId) {
        //TODO request method when the frontend is built so auth can happen
        //pre(request);
        return wrap(foundItemService.getAllFoundItemsWithTags(uniqueId));
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public ResponseEntity send(@RequestBody FoundItemSendEmailRequest request){
        pre(request);
        return wrap(foundItemService.sendEmail(request));
    }

}
