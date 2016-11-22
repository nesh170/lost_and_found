package lostandfound.exceptions;

/**
 * Created by nesh170 on 11/15/2016.
 */
public class ItemNotFoundException extends RuntimeException {
    public String message;

    public ItemNotFoundException(String message) {
        this.message = message;
    }
}
