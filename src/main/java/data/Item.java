package data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Item {

    public int myID;
    public String myLocation;
    public Timestamp myTimestamp;
    public String myUniqueID;
    public List<String> myTags;

    public Item(Integer id, String geolocation, Timestamp timestamp, String uniqueID, List<String> tags){
        myID = id;
        myLocation = geolocation;
        myTimestamp = timestamp;
        myUniqueID = uniqueID;
        myTags = tags;
    }

    public int tagMatching(List<String> tags){
        Set<String> tagSet = tags.stream().collect(Collectors.toSet());
        List<String> commonTags = myTags.stream().filter(tagSet::contains).collect(Collectors.toList());
        return commonTags.size();
    }


}
