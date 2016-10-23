package data.accessors;


import data.Item;
import data.LostItem;
import db.tables.records.LostTagsRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static db.Tables.LOST_TAGS;
import static db.tables.LostItems.LOST_ITEMS;

public class Accessor {
    private DSLContext myContext;

    public Accessor(){
        DBManager myDatabaseController = DBManager.getInstance();
        myContext = myDatabaseController.getDBQueryTool();
    }

    public List<LostItem> getAllLostItemsWithTags(){
        Result<Record> lostItems = myContext.select().from(LOST_ITEMS).fetch();
        Map<Integer, Result<LostTagsRecord>> lostTagsRecord = myContext.selectFrom(LOST_TAGS).fetch().intoGroups(LOST_TAGS.ID);
        Map<Integer, List<String>> lostTags = new HashMap<>();
        lostTagsRecord.entrySet().forEach(entry -> lostTags.put(entry.getKey(),entry.getValue().getValues(LOST_TAGS.TAGS)));
        List<LostItem> lostItemsList = new ArrayList<>();
        lostItems.forEach(result -> {
            lostItemsList.add(
                    new LostItem(
                            result.getValue(LOST_ITEMS.ID), result.getValue(LOST_ITEMS.GEOLOCATION),
                            result.getValue(LOST_ITEMS.TIME_STAMP), result.getValue(LOST_ITEMS.USER_UNIQUE_ID),
                            lostTags.get(result.getValue(LOST_ITEMS.ID))));
        });
        return lostItemsList;
    }

    public int commitLostItemWithTags(Item lostItem){
        return myContext.insertInto(LOST_ITEMS,
                LOST_ITEMS.GEOLOCATION,LOST_ITEMS.TIME_STAMP,LOST_ITEMS.USER_UNIQUE_ID)
                .values(lostItem.myLocation,lostItem.myTimestamp,lostItem.myUniqueID).execute();
    }

}
