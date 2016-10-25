package data;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.List;

public class LostItem extends Item {
    private int myFoundID = -1;

    public LostItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags,Integer foundID) {
        super(id,geolocation,timestamp,uniqueID,tags);
        myFoundID = foundID;
    }

    public LostItem(JsonElement lostItemJson) {
        super(lostItemJson);
    }

    @Override
    public int getMatchedID() {
        return myFoundID;
    }

}
