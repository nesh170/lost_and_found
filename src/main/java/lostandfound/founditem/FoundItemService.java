package lostandfound.founditem;

import data.FoundItem;
import data.LostItem;
import data.User;
import lostandfound.requestmodels.FoundItemRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class FoundItemService extends Service {
    private final static String EMAIL_TEMPLATE = "email_lost_template";

    public StdResponse getAllFoundItemsWithTags() {
        List<FoundItem> foundItems = foundItemAccessor.getAllFoundItemsWithTags();
        return new StdResponseWithBody(200, true, "Successfully Obtained List Of Found Items", foundItems);
    }

    public StdResponse getAllFoundItemsWithTags(String uniqueId) {
        List<FoundItem> foundItems = foundItemAccessor.getAllFoundItemsWithTags(uniqueId);
        return new StdResponseWithBody(200, true, String.format("Successfully Obtained List Of Found Items for User %s", uniqueId), foundItems);
    }

    public StdResponse createFoundItem(FoundItemRequest request) {
        //TODO check to see whether this method can take in the picture and handle the S3
        FoundItem foundItem = new FoundItem(request.geolocation, request.timestamp, request.uniqueId, request.tags);
        int id = foundItemAccessor.insertFoundItemWithTags(foundItem);
        lostItemAccessor.getAllLostItemsWithTags().parallelStream()
                .filter(lostItem -> Math.abs(foundItem.tagMatching(lostItem.tags) - foundItem.tags.size()) <= Integer.parseInt(generalProperties.getProperty("matching.difference")))
                .forEach(lostItem -> sendItemToLostPerson(lostItem, foundItem));
        return new StdResponseWithBody(200, true, "Successfully Created a New Found Item", id);
    }


    private void sendItemToLostPerson(LostItem lostItem, FoundItem foundItem) {
        User lostUser = authAccessor.getUserByUniqueID(lostItem.uniqueId);
        User foundUser = authAccessor.getUserByUniqueID(foundItem.uniqueId);
        String emailHTML = emailTemplateClient.createEmail(lostUser.name, foundUser.email, foundUser.name, foundItem.pictureURL, EMAIL_TEMPLATE, new Locale("US"));
        try {
            sesClient.sendEmail(Stream.of(lostUser.email).collect(Collectors.toList()), "Your Lost Item Is Found", emailHTML);
        } catch (Exception e) {
            //TODO enable logging in the future but for now :-
            e.printStackTrace();
        }
    }

}
