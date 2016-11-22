package lostandfound.item;

import lostandfound.exceptions.ItemNotFoundException;
import lostandfound.requestmodels.MarkItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;

@org.springframework.stereotype.Service
public class ItemService extends Service {

    public StdResponse markItemAsFound(MarkItemRequest request) {
        boolean success = itemAccessor.markItemAsFound(request.foundId, request.lostId);
        if (!success)
            throw new ItemNotFoundException(String.format("found:%1$d lost:%2$d is not in database", request.foundId, request.lostId));
        return new StdResponse(200, true, String.format("Successfully marked found:%1$d lost:%2$d as found", request.foundId, request.lostId));
    }

}
