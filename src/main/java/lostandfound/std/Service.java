package lostandfound.std;

import data.accessors.AuthAccessor;
import data.accessors.ItemAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Service
public class Service {

    public final AuthAccessor authAccessor = new AuthAccessor();
    public final ItemAccessor itemAccessor = new ItemAccessor();

    @Autowired
    public JdbcTemplate jt;

}
