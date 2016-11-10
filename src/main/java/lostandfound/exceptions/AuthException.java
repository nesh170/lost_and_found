package lostandfound.exceptions;

public class AuthException extends RuntimeException {
    public String message;

    public AuthException(String message) {
        this.message = message;
    }
}
