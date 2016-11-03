package lostandfound.std.models;

public class StdResponseWithBody extends StdResponse {
    public Object body;

    public StdResponseWithBody(int status, boolean success, String message, Object body) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.body = body;
    }
}