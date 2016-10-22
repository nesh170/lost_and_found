package data.accessors;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager  {
    private static final String userName = "bitnami";
    private static final String password = "password";
    private static final String url = "jdbc:postgresql://colab-sbx-122.oit.duke.edu:5432/lost_and_found_db";

    private DSLContext myDBQuery;
    private Connection myConnection;

    private static DatabaseManager instance = null;

    protected DatabaseManager(){
        connectDatabase();
        getDBQueryTool();
    }

    public static DatabaseManager getInstance(){
        if(instance==null){
            instance = new DatabaseManager();
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
