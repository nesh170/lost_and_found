package dataaccessors;


import static nu.studer.db.tables.Customers.CUSTOMERS;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class DataAccessors  {
    String userName = "bitnami";
    String password = "password";
    String url = "jdbc:postgresql://colab-sbx-122.oit.duke.edu:5432/testdb";



    public List<String> connectDatabase(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> lol = create.select().from(CUSTOMERS).fetch();

        return lol.map(rec -> rec.getValue(CUSTOMERS.NAME));
    }




}
