package utilities.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;

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
    public boolean sendEmail(String[] toAddresses, String subjectLine, String emailMessage) {
        Destination destination = new Destination().withToAddresses(toAddresses);
        Content subject = new Content().withData(subjectLine);
        Content content = new Content().withData(emailMessage);
        Body body = new Body().withText(content);
        Message message = new Message().withSubject(subject).withBody(body);
        SendEmailRequest request = new SendEmailRequest().withSource(this.FROM).withDestination(destination).withMessage(message);
        try {
            this.client.sendEmail(request);
            return true;
        }
        catch (Exception e) {
            System.out.println("Attempt to send email did not work");
            return false;
        }
    }
}
