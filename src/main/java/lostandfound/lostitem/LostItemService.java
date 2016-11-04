package lostandfound.lostitem;

import data.FoundItem;
import data.LostItem;
import data.accessors.ItemAccessor;
import lostandfound.requestmodels.LostItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;

import java.util.ArrayList;
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

    public StdResponse createLostItem(LostItemRequest lostItemRequest){
        LostItem lostItem = new LostItem(-1,lostItemRequest.geolocation,lostItemRequest.timestamp,lostItemRequest.uniqueId,lostItemRequest.tags,-1);
        Integer id = itemAccessor.commitLostItemWithTags(lostItem);
        Map<Integer,List<FoundItem>> matchedFoundItem = new HashMap<>();
        itemAccessor.getAllFoundItemsWithTags().forEach(item -> {
            int matchTags = item.tagMatching(lostItem.myTags);
            if (matchedFoundItem.containsKey(matchTags)) {
                matchedFoundItem.get(matchTags).add(item);
            }
            else{
                matchedFoundItem.put(matchTags, Stream.of(item).collect(Collectors.toList()));
            }
        });
        return new StdResponseWithBody(200,true,"Successfully Created a New Lost Items",matchedFoundItem);
    }

}
