package utilities.aws;


import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;

public class Email {
    private String message;
    private String to;
    private String subject;
    private static final String FROM = "ankitkayastha@gmail.com";
    public Email(String message, String to, String subject) {
        this.message = message;
        this.to = to;
        this.subject = subject;
    }

    public void sendEmail() {
        String[] toAddresses = {this.to};
        Destination destination = new Destination().withToAddresses(toAddresses);
        Content subject = new Content().withData(this.subject);
        Content content = new Content().withData(this.message);
        Body body = new Body().withText(content);
        Message message = new Message().withSubject(subject).withBody(body);
        SendEmailRequest request = new SendEmailRequest().withSource(this.FROM).withDestination(destination).withMessage(message);
        System.out.println("About to try to send email using AWS SES");
        AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
        Region REGION = Region.getRegion(Regions.US_WEST_2);
        client.setRegion(REGION);
        client.sendEmail(request);
        client.verifyEmailIdentity()
        System.out.println("Email has sent");
    }


    public static void main(String[] args) {
        Email email = new Email("Hi there", "sl290@duke.edu", "wassup");
        email.sendEmail();
    }




}
