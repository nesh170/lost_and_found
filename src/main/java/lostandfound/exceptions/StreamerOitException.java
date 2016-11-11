package lostandfound.exceptions;

public class StreamerOitException extends RuntimeException {
    public String message;

    public StreamerOitException(String message) {
        this.message = message;
    }
}
