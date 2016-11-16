package lostandfound.lostitem;

import lostandfound.requestmodels.LostItemRequest;
import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lostItem")
public class LostItemController extends Controller {

    @Autowired
    private LostItemService lostItemService;


    @RequestMapping(value = "/allItems", method = RequestMethod.GET)
    public ResponseEntity getLostItems() {
        //TODO request method when the frontend is built so auth can happen
        //pre(request);
        return wrap(lostItemService.getAllLostItemsWithTags());
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity add(@RequestBody LostItemRequest request) {
        pre(request);
        return wrap(lostItemService.createLostItem(request));
    }

    @RequestMapping(value = "/allItemsByUser/{uniqueId}", method = RequestMethod.GET)
    public ResponseEntity getLostItems(@PathVariable String uniqueId) {
        //TODO request method when the frontend is built so auth can happen
        //pre(request);
        return wrap(lostItemService.getAllLostItemsWithTags(uniqueId));
    }

}
