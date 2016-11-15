package lostandfound.lostitem;

import data.FoundItem;
import data.LostItem;
import lostandfound.requestmodels.LostItemRequest;
import lostandfound.requestmodels.MarkItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class LostItemService extends Service {

    public StdResponse getAllLostItemsWithTags() {
        List<LostItem> lostItems = itemAccessor.getAllLostItemsWithTags();
        return new StdResponseWithBody(200,true,"Successfully Obtained List Of Lost Items",lostItems);
    }

    public StdResponse getAllLostItemsWithTags(String uniqueId) {
        List<LostItem> lostItems = itemAccessor.getAllLostItemsWithTags(uniqueId);
        return new StdResponseWithBody(200,true,String.format("Successfully Obtained List Of Lost Items for User %s",uniqueId), lostItems);
    }

    public StdResponse createLostItem(LostItemRequest lostItemRequest){
        LostItem lostItem = new LostItem(lostItemRequest.geolocation,lostItemRequest.timestamp,lostItemRequest.uniqueId,lostItemRequest.tags);
        lostItem.id = itemAccessor.insertLostItemWithTags(lostItem);
        Map<Integer,List<FoundItem>> matchedFoundItem = new HashMap<>();
        itemAccessor.getAllFoundItemsWithTags().forEach(item -> {
            int matchTags = item.tagMatching(lostItem.tags);
            if (matchedFoundItem.containsKey(matchTags)) {
                matchedFoundItem.get(matchTags).add(item);
            }
            else{
                matchedFoundItem.put(matchTags, Stream.of(item).collect(Collectors.toList()));
            }
        });
        return new StdResponseWithBody(200,true,String.format("Successfully Created a New Lost Items and obtained %1$d matching items",matchedFoundItem.size()),matchedFoundItem);
    }

    public StdResponse markItemAsFound(MarkItemRequest request) {
        itemAccessor.markItemAsFound(request.foundId,request.lostId);
        return new StdResponse(200,true,String.format("Successfully marked found:%1$d lost:%2$d as found",request.foundId,request.lostId));
    }
}
