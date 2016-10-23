package data;


import java.sql.Timestamp;
import java.util.List;

public class LostItem extends Item {

    public LostItem(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags) {
        super(id,geolocation,timestamp,uniqueID,tags);
    }

}
