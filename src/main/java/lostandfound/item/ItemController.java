package lostandfound.item;

import lostandfound.requestmodels.MarkItemRequest;
import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController extends Controller {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/markItem", method = RequestMethod.POST)
    public ResponseEntity markItem(@RequestBody MarkItemRequest request) {
        pre(request);
        return wrap(itemService.markItemAsFound(request));
    }

}
