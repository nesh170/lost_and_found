package lostandfound.founditem;

import data.FoundItem;
import lostandfound.requestmodels.FoundItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;

import java.util.List;

@org.springframework.stereotype.Service
public class FoundItemService extends Service{

    public StdResponse getAllFoundItemsWithTags(){
        List<FoundItem> foundItems = itemAccessor.getAllFoundItemsWithTags();
        return new StdResponseWithBody(200,true,"Successfully Obtained List Of Lost Items",foundItems);
    }

    public StdResponse createFoundItem(FoundItemRequest request){
        FoundItem foundItem = new FoundItem(-1,request.geolocation,request.timestamp,request.uniqueId,request.tags,-1);
        int id = itemAccessor.commitFoundItemWithTags(foundItem);
        //TODO do email handing in here :/
        return new StdResponseWithBody(200,true,"Successfully Created a New Found Item",id);
    }

}
