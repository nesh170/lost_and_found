package data.accessors;

import data.User;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

import static db.tables.AuthTable.AUTH_TABLE;

public class AuthAccessor extends Accessor {

    public AuthAccessor(){
        super();
    }

    public List<User> getAllUsers(){
        Result<Record> userRecord = myContext.select().from(AUTH_TABLE).fetch();
        List<User> userList = new ArrayList<>();
        userRecord.forEach(record -> userList.add(new User(record.getValue(AUTH_TABLE.GIVEN_NAME),record.getValue(AUTH_TABLE.UNIQUE_ID),record.getValue(AUTH_TABLE.NET_ID),record.getValue(AUTH_TABLE.EMAIL))));
        return userList;
    }

    public User getUserByUniqueID(String uniqueID){
        Record record = myContext.select().from(AUTH_TABLE).where(AUTH_TABLE.UNIQUE_ID.equal(uniqueID)).fetchOne();
        if(record==null) throw new NullPointerException();
        User user = new User(record.getValue(AUTH_TABLE.GIVEN_NAME),record.getValue(AUTH_TABLE.UNIQUE_ID),record.getValue(AUTH_TABLE.NET_ID),record.getValue(AUTH_TABLE.EMAIL));
        return user;
    }

    public int createUser(User user){
        return myContext
                .insertInto(AUTH_TABLE,AUTH_TABLE.UNIQUE_ID,AUTH_TABLE.GIVEN_NAME,AUTH_TABLE.NET_ID,AUTH_TABLE.EMAIL)
                .values(user.uniqueId,user.name,user.netId,user.email).execute();
    }
}
