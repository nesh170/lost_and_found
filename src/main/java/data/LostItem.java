package data;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.List;

public class LostItem extends Item {
    private int myFoundID;

    public LostItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags,Integer foundID) {
        super(id,geolocation,timestamp,uniqueID,tags);
        myFoundID = foundID;
    }

    public LostItem(JsonElement lostItemJson) {
        super(lostItemJson);
    }

    @Override
    public int getFoundID() {
        return myFoundID;
    }

    @Override
    public JsonObject createJSON() {
        JsonObject lostItemJSON = new JsonObject();
        lostItemJSON.addProperty("id",myID);
        lostItemJSON.addProperty("location",myLocation);
        lostItemJSON.addProperty("uniqueID",myUniqueID);
        lostItemJSON.addProperty("timestamp",myTimestamp.toString());
        JsonArray tagArrayJSON = new JsonArray();
        myTags.forEach(tagArrayJSON::add);
        lostItemJSON.addProperty("tags",tagArrayJSON.toString());
        return lostItemJSON;
    }

}
