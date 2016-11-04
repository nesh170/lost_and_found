package lostandfound.admin;

import lostandfound.std.Service;
import lostandfound.std.models.StdResponse;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Transactional
@org.springframework.stereotype.Service
public class AdminService extends Service {

    public StdResponse upgradeDb(String sqlFileName) {
        try {
            String query = readQuery("sql/" + sqlFileName + ".sql");
            jt.execute(query);

            return new StdResponse(200, true, "Successfully upgraded database");
        } catch (Exception e) {
            e.printStackTrace();
            return new StdResponse(500, false, "Failed to upgrade database");
        }
    }

    private String readQuery(String file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            int index;
            // removes single-line comments
            if ((index = str.indexOf("--")) >= 0) {
                str = str.substring(0, index);
            }
            sb.append(str).append(" ");
        }
        in.close();

        return sb.toString();
    }

}
