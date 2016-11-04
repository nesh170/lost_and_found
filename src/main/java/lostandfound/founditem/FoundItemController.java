package lostandfound.founditem;

import lostandfound.requestmodels.FoundItemRequest;
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
    public ResponseEntity getLostItems() {
        return wrap(foundItemService.getAllFoundItemsWithTags());
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity add(@RequestBody FoundItemRequest request){
        return wrap(foundItemService.createFoundItem(request));
    }


}
