package lostandfound.admin;

import lostandfound.std.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/admin")
public class AdminController extends Controller {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/dbUpgrade", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity dbUpgrade(@PathVariable String sqlFileName) throws IOException {
        return wrap(adminService.upgradeDb(sqlFileName));
    }
}
