package lostandfound.dukecard;

import data.User;
import lostandfound.exceptions.EmailException;
import lostandfound.exceptions.StreamerOitException;
import lostandfound.exceptions.UserNotFoundException;
import lostandfound.requestmodels.FoundDukeCardRequest;
import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import lostandfound.std.models.StdResponseWithBody;
import utilities.streameroitclient.response.ComplexStreamerOitInfoResponse;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class DukeCardService extends Service {

    private final String DUKECARD_IMAGE = generalProperties.getProperty("dukecard.image.link");
    private final String DUKECARD_EMAIL_TEMPLATE = generalProperties.getProperty("dukecard.email.template");
    private final String DUKECARD_SUBJECT_LINE = generalProperties.getProperty("dukecard.email.subject");

    public StdResponse createLostDukeCard(FoundDukeCardRequest request) {
        User founder = authAccessor.getUserByUniqueID(request.uniqueId);
        DukeCardResponse loster;
        try {
            String netId = streamerOitClient.getInfoByUniqueId(request.dukeCardUniqueId).netid;
            ComplexStreamerOitInfoResponse oitResponse = streamerOitClient.getInfoByNetId(netId);
            loster = new DukeCardResponse(oitResponse.display_name, oitResponse.emails.get(0));
        } catch (StreamerOitException e) {
            throw new UserNotFoundException(String.format("%s is not found", request.dukeCardUniqueId));
        }
        String emailMessage = emailTemplateClient.createEmail(loster.name, founder.email, founder.name, DUKECARD_IMAGE, DUKECARD_EMAIL_TEMPLATE, new Locale("US"));
        try {
            sesClient.sendEmail(Stream.of(loster.email).collect(Collectors.toList()), DUKECARD_SUBJECT_LINE, emailMessage);
            loster.emailSent = true;
        } catch (EmailException e) {
            //TODO use a logger to log this message
            e.printStackTrace();
            loster.emailSent = false;
        }
        return new StdResponseWithBody(200, true, "Obtained User from the database", loster);
    }

}
