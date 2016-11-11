package lostandfound.fileupload;

import lostandfound.exceptions.FileUploadException;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Calendar;

@org.springframework.stereotype.Service
public class FileUploadService extends Service{

    public StdResponse addFile(MultipartFile multiPartFile) {
        String originalFileName = multiPartFile.getOriginalFilename();
        File file = new File(originalFileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multiPartFile.getBytes());
            fos.close();
            String key = generateKey(file);
            String url = s3Client.addFile(key, file);
            file.delete();
            return new StdResponseWithBody(200, true, "Successfully uploaded a file", url);
        }
        catch (Exception exception) { //IOException and IllegalStateExceptions can possibly be thrown
            throw new FileUploadException("File Upload failed for " + originalFileName);
        }
    }

    private String generateKey(File file) {
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        return timestamp.toString() + file.getPath();
    }

}
