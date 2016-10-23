package data.accessors;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String userName = "bitnami";
    private static final String password = "password";
    private static final String url = "jdbc:postgresql://colab-sbx-122.oit.duke.edu:5432/lost_and_found_db";

    private DSLContext myDBQuery;
    private Connection myConnection;

    private static DBManager instance = null;

    protected DBManager(){
        connectDatabase();
        getDBQueryTool();
    }

    public static DBManager getInstance(){
        if(instance==null){
            instance = new DBManager();
        }
        return instance;
    }

    private void connectDatabase(){
        try {
            myConnection = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DSLContext getDBQueryTool(){
        if(myDBQuery == null) {
            myDBQuery = DSL.using(myConnection, SQLDialect.POSTGRES);
        }
        return myDBQuery;
    }




}
