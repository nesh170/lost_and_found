package lostandfound.dukecard;

public class DukeCardResponse {
    public String name;
    public String email;
    public boolean emailSent;

    public DukeCardResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
