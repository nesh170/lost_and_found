package data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Item {

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
    

    public Item(JsonElement itemJson) {
        myID = -1;
        myLocation = itemJson.getAsJsonObject().get("location").getAsString();
        myTimestamp = new Timestamp(new Date().getTime());
        myUniqueID = itemJson.getAsJsonObject().get("uniqueID").getAsString();
        myTags = getList(itemJson.getAsJsonObject().get("tags").getAsJsonArray().iterator());
    }

    private List<String> getList(Iterator<JsonElement> tags) {
        List<String> list = new ArrayList<>();
        tags.forEachRemaining(tag -> list.add(tag.getAsString()));
        return list;
    }

    public int tagMatching(List<String> tags){
        Set<String> tagSet = tags.stream().collect(Collectors.toSet());
        List<String> commonTags = myTags.stream().filter(tagSet::contains).collect(Collectors.toList());
        return commonTags.size();
    }

    public abstract int getFoundID();

    public abstract JsonObject createJSON();
}
