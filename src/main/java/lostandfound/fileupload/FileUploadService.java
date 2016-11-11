package lostandfound.fileupload;

import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;

@org.springframework.stereotype.Service
public class FileUploadService extends Service{

    public StdResponse addFile(MultipartFile multiPartFile) {
        String originalFileName = multiPartFile.getOriginalFilename();
        File file = new File(originalFileName);
        try {
            multiPartFile.transferTo(file);
            String key = generateKey(file);
            s3Client.addFile(key, file);
            return new StdResponseWithBody(200, true, "Successfully uploaded a file", key);
        }
        catch (Exception exception) { //IOException and IllegalStateExceptions can possibly be thrown
            exception.printStackTrace();
            return new StdResponse(500, false, "File failed to upload");
        }
    }

    private String generateKey(File file) {
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        return timestamp.toString() + file.getPath();
    }

}
