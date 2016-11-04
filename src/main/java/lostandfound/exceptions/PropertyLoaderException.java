package lostandfound.exceptions;

public class PropertyLoaderException extends RuntimeException {
    public String message;

    public PropertyLoaderException(String message) {
        this.message = message;
    }
}
