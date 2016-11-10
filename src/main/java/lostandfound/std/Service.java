package lostandfound.std;

import data.accessors.AuthAccessor;
import data.accessors.ItemAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import utilities.PropertiesLoader;
import utilities.aws.S3Client;
import utilities.aws.SESClient;

import java.util.Properties;

@org.springframework.stereotype.Service
public class Service {

    public final AuthAccessor authAccessor = new AuthAccessor();
    public final ItemAccessor itemAccessor = new ItemAccessor();
    public final S3Client s3Client = new S3Client();
    public final SESClient sesClient = new SESClient();

    public final Properties generalProperties = PropertiesLoader.loadPropertiesFromPackage("general.properties");

    @Autowired
    public JdbcTemplate jt;

    public boolean authorizeUser(String uniqueId, String accessToken) {
        //TODO if contained in the database then allow user to use api, if then probably redirect to the lock screen?
        return true;
    }
}
