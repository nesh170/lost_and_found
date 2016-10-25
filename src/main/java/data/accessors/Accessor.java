package data.accessors;


import org.jooq.DSLContext;

public class Accessor {
    protected DSLContext myContext;

    public Accessor(){
        DBManager myDatabaseController = DBManager.getInstance();
        myContext = myDatabaseController.getDBQueryTool();
    }

}
