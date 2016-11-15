package data;

import java.sql.Timestamp;
import java.util.List;

public class LostItem extends Item {
    private int foundId = -1;

    public LostItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags, String pictureURL, Integer foundId) {
        super(id,geolocation,timestamp,uniqueID,tags,pictureURL);
        this.foundId = foundId;
    }

    public LostItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags, String pictureURL) {
        super(id,geolocation,timestamp,uniqueID,tags,pictureURL);
    }

    public LostItem(String geolocation, Timestamp timestamp, String uniqueID, List<String> tags){
        super(geolocation,timestamp,uniqueID,tags);
    }

    @Override
    public int getMatchedID() {
        return foundId;
    }

}
