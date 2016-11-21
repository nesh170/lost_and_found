package lostandfound.exceptions;

public class UserNotFoundException extends RuntimeException {
    public String message;

    public UserNotFoundException(String message){
        this.message = message;
    }
}
