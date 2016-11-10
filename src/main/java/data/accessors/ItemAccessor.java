package data.accessors;

import data.FoundItem;
import data.Item;
import data.LostItem;
import db.tables.records.FoundItemsRecord;
import db.tables.records.FoundTagsRecord;
import db.tables.records.LostItemsRecord;
import db.tables.records.LostTagsRecord;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static db.Tables.FOUND_ITEMS;
import static db.Tables.FOUND_TAGS;
import static db.Tables.LOST_TAGS;
import static db.tables.LostItems.LOST_ITEMS;

public class ItemAccessor extends Accessor {

    public ItemAccessor(){
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
                            lostTags.get(result.getValue(LOST_ITEMS.ID)),result.getValue(LOST_ITEMS.PICTURE_URL),-1));
        });
        return lostItemsList;
    }

    public int commitLostItemWithTags(Item lostItem){
        LostItemsRecord outputLostItem =  myContext.insertInto(LOST_ITEMS,
                LOST_ITEMS.GEOLOCATION,LOST_ITEMS.TIME_STAMP,LOST_ITEMS.USER_UNIQUE_ID)
                .values(lostItem.location,lostItem.timestamp,lostItem.uniqueId).returning(LOST_ITEMS.ID).fetchOne();
        lostItem.id = outputLostItem.value1();
        lostItem.tags.forEach(tag -> myContext.insertInto(LOST_TAGS,LOST_TAGS.ID,LOST_TAGS.TAGS).values(lostItem.id, tag).execute());
        return lostItem.id;
    }

    public List<FoundItem> getAllFoundItemsWithTags(){
        Result<Record> foundItemsRecord = myContext.select().from(FOUND_ITEMS).where(FOUND_ITEMS.LOST_ID.isNull()).fetch();
        Map<Integer, Result<FoundTagsRecord>> foundTagsRecord = myContext.selectFrom(FOUND_TAGS).fetch().intoGroups(FOUND_TAGS.ID);
        Map<Integer, List<String>> foundTags = new HashMap<>();
        foundTagsRecord.entrySet().forEach(entry -> foundTags.put(entry.getKey(),entry.getValue().getValues(FOUND_TAGS.TAGS)));
        List<FoundItem> foundItemsList = new ArrayList<>();
        foundItemsRecord.forEach(result -> {
            foundItemsList.add(
                    new FoundItem(
                            result.getValue(FOUND_ITEMS.ID), result.getValue(FOUND_ITEMS.GEOLOCATION),
                            result.getValue(FOUND_ITEMS.TIME_STAMP), result.getValue(FOUND_ITEMS.USER_UNIQUE_ID),
                            foundTags.get(result.getValue(FOUND_ITEMS.ID)),result.getValue(FOUND_ITEMS.PICTURE_URL),-1));
        });
        return foundItemsList;
    }

    public int commitFoundItemWithTags(Item foundItem){
        FoundItemsRecord outputFoundItem =  myContext.insertInto(FOUND_ITEMS,
                FOUND_ITEMS.GEOLOCATION,FOUND_ITEMS.TIME_STAMP,FOUND_ITEMS.USER_UNIQUE_ID)
                .values(foundItem.location,foundItem.timestamp,foundItem.uniqueId).returning(FOUND_ITEMS.ID).fetchOne();
        foundItem.id = outputFoundItem.value1();
        foundItem.tags.forEach(tag -> myContext.insertInto(FOUND_TAGS,FOUND_TAGS.ID,FOUND_TAGS.TAGS).values(foundItem.id, tag).execute());
        return foundItem.id;
    }

    public void markItemAsFound(int foundId, int lostId){
        myContext.update(LOST_ITEMS).set(LOST_ITEMS.FOUND_ID,foundId).execute();
        myContext.update(FOUND_ITEMS).set(FOUND_ITEMS.LOST_ID,lostId).execute();
    }

}