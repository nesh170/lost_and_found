package data.accessors;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import utilities.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    private DSLContext dbQuery;
    private Connection connection;

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

    private void connectDatabase() {
        Properties properties = PropertiesLoader.loadPropertiesFromPackage("application.properties");
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("spring.datasource.url"),
                    properties.getProperty("spring.datasource.username"),
                    properties.getProperty("spring.datasource.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DSLContext getDBQueryTool(){
        if(dbQuery == null) {
            dbQuery = DSL.using(connection, SQLDialect.POSTGRES);
        }
        return dbQuery;
    }




}
