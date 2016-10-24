package data;


import com.google.gson.JsonElement;

import java.sql.Timestamp;
import java.util.List;

public class LostItem extends Item {
    private int myFoundID;

    public LostItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags,Integer foundID) {
        super(id,geolocation,timestamp,uniqueID,tags);
    }

    public LostItem(JsonElement lostItemJson) {
        super(lostItemJson);
    }

    @Override
    public int getFoundID() {
        return myFoundID;
    }

}
