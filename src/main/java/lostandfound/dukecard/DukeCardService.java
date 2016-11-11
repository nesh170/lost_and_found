package lostandfound.dukecard;

import data.User;
import lostandfound.exceptions.EmailException;
import lostandfound.requestmodels.FoundDukeCardRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class DukeCardService extends Service {

    private final String DUKECARD_IMAGE = generalProperties.getProperty("dukecard.image.link") ;
    private final String DUKECARD_EMAIL_TEMPLATE = generalProperties.getProperty("dukecard.email.template");
    private final String DUKECARD_SUBJECT_LINE = generalProperties.getProperty("dukecard.email.subject");

    public StdResponse createLostDukeCard(FoundDukeCardRequest request) {
        User loster = authAccessor.getUserByUniqueID(request.dukeCardUniqueId);
        User founder = authAccessor.getUserByUniqueID(request.uniqueId);
        String emailMessage = emailTemplateClient.createEmail(loster.name,founder.email,founder.name,DUKECARD_IMAGE,DUKECARD_EMAIL_TEMPLATE,new Locale("US"));
        try {
            sesClient.sendEmail(Stream.of(loster.email).collect(Collectors.toList()), DUKECARD_SUBJECT_LINE, emailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException(loster.email + "cannot be send");
        }
        return new StdResponse(200,true,"Successfully Send");
    }

}
