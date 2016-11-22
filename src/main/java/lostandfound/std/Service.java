package lostandfound.std;

import data.User;
import data.accessors.AuthAccessor;
import data.accessors.FoundItemAccessor;
import data.accessors.ItemAccessor;
import data.accessors.LostItemAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import utilities.PropertiesLoader;
import utilities.ThymeleafEmailClient;
import utilities.aws.S3Client;
import utilities.aws.SESClient;
import utilities.streameroitclient.StreamerOitClient;

import java.util.Properties;

@org.springframework.stereotype.Service
public class Service {

    public final AuthAccessor authAccessor = new AuthAccessor();
    public final ItemAccessor itemAccessor = new ItemAccessor();
    public final LostItemAccessor lostItemAccessor = new LostItemAccessor();
    public final FoundItemAccessor foundItemAccessor = new FoundItemAccessor();
    public final S3Client s3Client = new S3Client();
    public final SESClient sesClient = new SESClient();
    public final ThymeleafEmailClient emailTemplateClient = new ThymeleafEmailClient();
    public final StreamerOitClient streamerOitClient = new StreamerOitClient();

    public final Properties generalProperties = PropertiesLoader.loadPropertiesFromPackage("general.properties");

    @Autowired
    public JdbcTemplate jt;

    public boolean authorizeUser(String uniqueId, String accessToken) {
        User currentUser;
        try {
            currentUser = authAccessor.getUserByUniqueID(uniqueId);
        } catch (NullPointerException e) {
            return false;
        }
        return currentUser.auth_token.equals(accessToken);
    }
}
