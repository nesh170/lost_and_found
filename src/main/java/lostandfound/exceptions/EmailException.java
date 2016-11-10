package lostandfound.exceptions;

public class EmailException extends RuntimeException {
    public String message;

    public EmailException(String message) {
        this.message = message;
    }
}
