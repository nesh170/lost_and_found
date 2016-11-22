package utilities.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import lostandfound.exceptions.EmailException;
import org.jooq.util.derby.sys.Sys;

import java.util.Arrays;
import java.util.List;

public class SESClient extends AWSClient {
    private AmazonSimpleEmailServiceClient client;
    private static final String FROM = property.getProperty("source.email");

    public SESClient() {
        this.client = new AmazonSimpleEmailServiceClient();
        Region REGION = Region.getRegion(Regions.US_WEST_2);
        this.client.setRegion(REGION);
    }

    public void verifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest identityRequest = new VerifyEmailIdentityRequest();
        this.client.verifyEmailIdentity(identityRequest.withEmailAddress(emailAddress));
    }
    public void sendEmail(List<String> toAddresses, String subjectLine, String emailMessage) {
        Destination destination = new Destination().withToAddresses(toAddresses);
        Content subject = new Content().withData(subjectLine);
        Content content = new Content().withData(emailMessage);
        Body body = new Body().withHtml(content);
        Message message = new Message().withSubject(subject).withBody(body);
        SendEmailRequest request = new SendEmailRequest().withSource(this.FROM).withDestination(destination).withMessage(message);
        try{
            client.sendEmail(request);
        }
        catch (Exception exception) {
            throw new EmailException("Email is not verified " + Arrays.toString(toAddresses.toArray()));
        }
    }
}
