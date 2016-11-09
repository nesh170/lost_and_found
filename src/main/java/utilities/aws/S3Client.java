package utilities.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;

public class S3Client extends AWSClient {

    private final AmazonS3 client = new AmazonS3Client();
    private final String PICTURE_BUCKET = property.getProperty("picture.bucket");

    public String addFile(String key, File file){
        PutObjectResult result = client.putObject(PICTURE_BUCKET,key,file);
        return String.format("http://lostandfoundspringfinderzpicture.s3.amazonaws.com/%1s",key);
    }

}
