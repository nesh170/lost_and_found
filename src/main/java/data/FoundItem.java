package data;

import com.google.gson.JsonElement;

import java.sql.Timestamp;
import java.util.List;

public class FoundItem extends Item {
    private int myLostID = -1;

    public FoundItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags, Integer lostID) {
        super(id,geolocation,timestamp,uniqueID,tags);
        myLostID = lostID;
    }

    public FoundItem(JsonElement foundItemJson) {
        super(foundItemJson);
    }

    @Override
    public int getMatchedID() {
        return myLostID;
    }

}
