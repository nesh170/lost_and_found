package lostandfound.requestmodels;

import lostandfound.std.models.StdRequest;

import java.sql.Timestamp;
import java.util.List;

public class LostItemRequest extends StdRequest {

    public String geolocation;
    public Timestamp timestamp;
    public List<String> tags;
    public String picture_url;

}
