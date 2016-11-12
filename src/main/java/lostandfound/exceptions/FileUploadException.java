package lostandfound.exceptions;

/**
 * Created by nesh170 on 11/11/2016.
 */
public class FileUploadException extends RuntimeException {
    public String message;

    public FileUploadException(String message) {
        this.message = message;
    }
}
