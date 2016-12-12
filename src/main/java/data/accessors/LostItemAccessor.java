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

    public LostItemAccessor() {
        super();
    }

    /*
   Gets the items that has not been found. If they are found, there will be a found_id associated with them
  */
    public List<LostItem> getAllLostItemsWithTags() {
        Result<Record> lostItems = myContext.select().from(LOST_ITEMS).where(LOST_ITEMS.FOUND_ID.isNull()).fetch();
        return getLostItems(lostItems);
    }

    public int insertLostItemWithTags(Item lostItem) {
        LostItemsRecord outputLostItem = myContext.insertInto(LOST_ITEMS,
                LOST_ITEMS.GEOLOCATION, LOST_ITEMS.TIME_STAMP, LOST_ITEMS.USER_UNIQUE_ID, LOST_ITEMS.PICTURE_URL)
                .values(lostItem.location, lostItem.timestamp, lostItem.uniqueId, lostItem.pictureURL).returning(LOST_ITEMS.ID).fetchOne();
        lostItem.id = outputLostItem.value1();
        lostItem.tags.forEach(tag -> myContext.insertInto(LOST_TAGS, LOST_TAGS.ID, LOST_TAGS.TAGS).values(lostItem.id, tag).execute());
        return lostItem.id;
    }

    public List<LostItem> getAllLostItemsWithTags(String userUniqueId) {
        Result<Record> lostItems = myContext.select().from(LOST_ITEMS).where(LOST_ITEMS.FOUND_ID.isNull()).and(LOST_ITEMS.USER_UNIQUE_ID.equal(userUniqueId)).fetch();
        return getLostItems(lostItems);
    }

    private List<LostItem> getLostItems(Result<Record> lostItems) {
        Map<Integer, Result<LostTagsRecord>> lostTagsRecord = myContext.selectFrom(LOST_TAGS).fetch().intoGroups(LOST_TAGS.ID);
        Map<Integer, List<String>> lostTags = new HashMap<>();
        lostTagsRecord.entrySet().forEach(entry -> lostTags.put(entry.getKey(), entry.getValue().getValues(LOST_TAGS.TAGS)));
        List<LostItem> lostItemsList = new ArrayList<>();
        lostItems.forEach(result -> lostItemsList.add(generateLostItem(result, lostTags)));
        return lostItemsList;
    }

    private LostItem generateLostItem(Record result, Map<Integer, List<String>> lostTags) {
        return new LostItem(
                result.getValue(LOST_ITEMS.ID), result.getValue(LOST_ITEMS.GEOLOCATION),
                result.getValue(LOST_ITEMS.TIME_STAMP), result.getValue(LOST_ITEMS.USER_UNIQUE_ID),
                lostTags.get(result.getValue(LOST_ITEMS.ID)), result.getValue(LOST_ITEMS.PICTURE_URL));
    }

}
