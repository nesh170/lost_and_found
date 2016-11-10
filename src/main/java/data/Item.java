package data;

import com.google.gson.JsonElement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Item {

    public int id = -1;
    public String location;
    public Timestamp timestamp;
    public String uniqueId;
    public List<String> tags;
    public String pictureURL;

    public Item(String location, Timestamp timestamp, String uniqueId, List<String> tags){
        this.location = location;
        this.timestamp = timestamp;
        this.uniqueId = uniqueId;
        this.tags = tags;
    }

    public Item(Integer id, String location, Timestamp timestamp, String uniqueId, List<String> tags, String pictureURL){
        this(location,timestamp,uniqueId,tags);
        this.id = id;
        this.pictureURL = pictureURL;
    }

    public int tagMatching(List<String> tags){
        Set<String> tagSet = tags.stream().collect(Collectors.toSet());
        List<String> commonTags = this.tags.stream().filter(tagSet::contains).collect(Collectors.toList());
        return commonTags.size();
    }

    public abstract int getMatchedID();

}
