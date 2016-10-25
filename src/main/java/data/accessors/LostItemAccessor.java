package data.accessors;

import data.Item;
import data.LostItem;
import db.tables.records.LostItemsRecord;
import db.tables.records.LostTagsRecord;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static db.Tables.LOST_TAGS;
import static db.tables.LostItems.LOST_ITEMS;

public class LostItemAccessor extends Accessor {

    public LostItemAccessor(){
        super();
    }

    /*
    Gets the items that has not been found. If they are found, there will be a found_id associated with them
   */
    public List<LostItem> getAllLostItemsWithTags(){
        Result<Record> lostItems = myContext.select().from(LOST_ITEMS).where(LOST_ITEMS.FOUND_ID.isNull()).fetch();
        Map<Integer, Result<LostTagsRecord>> lostTagsRecord = myContext.selectFrom(LOST_TAGS).fetch().intoGroups(LOST_TAGS.ID);
        Map<Integer, List<String>> lostTags = new HashMap<>();
        lostTagsRecord.entrySet().forEach(entry -> lostTags.put(entry.getKey(),entry.getValue().getValues(LOST_TAGS.TAGS)));
        List<LostItem> lostItemsList = new ArrayList<>();
        lostItems.forEach(result -> {
            lostItemsList.add(
                    new LostItem(
                            result.getValue(LOST_ITEMS.ID), result.getValue(LOST_ITEMS.GEOLOCATION),
                            result.getValue(LOST_ITEMS.TIME_STAMP), result.getValue(LOST_ITEMS.USER_UNIQUE_ID),
                            lostTags.get(result.getValue(LOST_ITEMS.ID)),-1));
        });
        return lostItemsList;
    }

    public int commitLostItemWithTags(Item lostItem){
        LostItemsRecord outputLostItem =  myContext.insertInto(LOST_ITEMS,
                LOST_ITEMS.GEOLOCATION,LOST_ITEMS.TIME_STAMP,LOST_ITEMS.USER_UNIQUE_ID)
                .values(lostItem.myLocation,lostItem.myTimestamp,lostItem.myUniqueID).returning(LOST_ITEMS.ID).fetchOne();
        lostItem.myID = outputLostItem.value1();
        lostItem.myTags.forEach(tag -> myContext.insertInto(LOST_TAGS,LOST_TAGS.ID,LOST_TAGS.TAGS).values(lostItem.myID, tag).execute());
        return lostItem.myID;
    }

}
