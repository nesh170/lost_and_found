package lostandfound.founditem;

import data.FoundItem;
import data.LostItem;
import lostandfound.requestmodels.FoundItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;

import java.util.List;

@org.springframework.stereotype.Service
public class FoundItemService extends Service {

    public StdResponse getAllFoundItemsWithTags() {
        List<FoundItem> foundItems = itemAccessor.getAllFoundItemsWithTags();
        return new StdResponseWithBody(200, true, "Successfully Obtained List Of Found Items", foundItems);
    }

    public StdResponse createFoundItem(FoundItemRequest request) {
        FoundItem foundItem = new FoundItem(request.geolocation, request.timestamp, request.uniqueId, request.tags);
        int id = itemAccessor.commitFoundItemWithTags(foundItem);
        itemAccessor.getAllLostItemsWithTags().parallelStream()
                .filter(lostItem -> Math.abs(foundItem.tagMatching(lostItem.tags) - foundItem.tags.size()) <= Integer.parseInt(generalProperties.getProperty("matching.difference")))
                .forEach(lostItem -> sendItemToLostPerson(lostItem,foundItem));
        return new StdResponseWithBody(200, true, "Successfully Created a New Found Item", id);
    }

    private void sendItemToLostPerson(LostItem lostItem, FoundItem foundItem) {
        //TODO fill up email stuff later
    }


}
