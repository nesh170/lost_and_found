package data;

import com.google.gson.JsonObject;

public class User {
    public String myName;
    public String myUniqueID;
    public String myNetID;

    public User(String name, String uniqueID, String netID){
        myName = name;
        myUniqueID = uniqueID;
        myNetID = netID;
    }

    public JsonObject createJSON(){
        JsonObject userJSON = new JsonObject();
        userJSON.addProperty("name",myName);
        userJSON.addProperty("uniqueID",myUniqueID);
        userJSON.addProperty("netID",myNetID);
        return userJSON;
    }

}
