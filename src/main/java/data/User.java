package data;

public class User {
    public String name;
    public String uniqueId;
    public String netId;
    public String email;
    public String auth_token;

    public User(String name, String uniqueId, String netId, String email, String auth_token){
        this.name = name;
        this.uniqueId = uniqueId;
        this.netId = netId;
        this.email = email;
        this.auth_token = auth_token;
    }

    public boolean isSameUser(String id) {
        if (this.uniqueId.equals(id)) {
            return true;
        }
        return false;
    }

}
