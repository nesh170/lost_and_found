package lostandfound.std;

import lostandfound.std.models.StdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Calendar;

@RestController
public class Controller {

    @Autowired
    private Service service;


    protected ResponseEntity wrap(StdResponse stdResponse) {
        stdResponse.timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        switch (stdResponse.status) {
            case 200:
                return ResponseEntity.status(HttpStatus.OK).body(stdResponse);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(stdResponse);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stdResponse);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(stdResponse);
        }
    }

}
