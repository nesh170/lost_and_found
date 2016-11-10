package lostandfound.founditem;

import data.FoundItem;
import data.LostItem;
import data.User;
import lostandfound.requestmodels.FoundItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;
import utilities.aws.SESClient;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class FoundItemService extends Service {

    public StdResponse getAllFoundItemsWithTags() {
        List<FoundItem> foundItems = itemAccessor.getAllFoundItemsWithTags();
        return new StdResponseWithBody(200, true, "Successfully Obtained List Of Found Items", foundItems);
    }

    public StdResponse createFoundItem(FoundItemRequest request) {
        //TODO check to see whether this method can take in the picture and handle the S3
        FoundItem foundItem = new FoundItem(request.geolocation, request.timestamp, request.uniqueId, request.tags);
        int id = itemAccessor.commitFoundItemWithTags(foundItem);
        itemAccessor.getAllLostItemsWithTags().parallelStream()
                .filter(lostItem -> Math.abs(foundItem.tagMatching(lostItem.tags) - foundItem.tags.size()) <= Integer.parseInt(generalProperties.getProperty("matching.difference")))
                .forEach(lostItem -> sendItemToLostPerson(lostItem,foundItem));
        return new StdResponseWithBody(200, true, "Successfully Created a New Found Item", id);
    }

    private void sendItemToLostPerson(LostItem lostItem, FoundItem foundItem) {
        User lostUser = authAccessor.getUserByUniqueID(lostItem.uniqueId);
        User foundUser = authAccessor.getUserByUniqueID(foundItem.uniqueId);
        String emailHTML = emailTemplateClient.createEmail(lostUser.name,foundUser.email,foundUser.name,foundItem.pictureURL,new Locale("US"));
        try{
            sesClient.sendEmail(Stream.of(lostUser.email).collect(Collectors.toList()),"Your Lost Item Is Found",emailHTML);
        }
        catch (Exception e){
            //TODO enable logging in the future but for now :-
            e.printStackTrace();
        }
    }


}
