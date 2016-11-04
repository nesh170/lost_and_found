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
        return wrap(lostItemService.getAllLostItemsWithTags());
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity add(@RequestBody LostItemRequest request){
        return wrap(lostItemService.createLostItem(request));
    }



}
