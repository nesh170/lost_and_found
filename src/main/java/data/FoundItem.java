package data;

import java.sql.Timestamp;
import java.util.List;

public class FoundItem extends Item {
    private int lostId = -1;

    public FoundItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags, String pictureURL, Integer lostId) {
        super(id,geolocation,timestamp,uniqueID,tags,pictureURL);
        this.lostId = lostId;
    }

    public FoundItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags, String pictureURL) {
        super(id,geolocation,timestamp,uniqueID,tags,pictureURL);
    }

    public FoundItem(String geolocation, Timestamp timestamp, String uniqueID, List<String> tags, String pictureURL){
        super(geolocation,timestamp,uniqueID,tags,pictureURL);
    }

    @Override
    public int getMatchedID() {
        return lostId;
    }

}
