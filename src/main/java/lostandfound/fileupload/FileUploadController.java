package lostandfound.fileupload;

import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileUploadController extends Controller {

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multiPartFile ) {
        return wrap(fileUploadService.addFile(multiPartFile));
    }


}
