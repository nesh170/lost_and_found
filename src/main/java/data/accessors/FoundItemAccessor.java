package data.accessors;

import data.FoundItem;
import data.Item;
import db.tables.records.FoundItemsRecord;
import db.tables.records.FoundTagsRecord;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static db.Tables.FOUND_ITEMS;
import static db.Tables.FOUND_TAGS;

public class FoundItemAccessor extends Accessor {

    public FoundItemAccessor() {
        super();
    }

    public List<FoundItem> getAllFoundItemsWithTags() {
        Result<Record> foundItemsRecord = myContext.select().from(FOUND_ITEMS).where(FOUND_ITEMS.LOST_ID.isNull()).fetch();
        return getFoundItems(foundItemsRecord);
    }

    public List<FoundItem> getAllFoundItemsWithTags(String userUniqueId) {
        Result<Record> foundItems = myContext.select().from(FOUND_ITEMS).where(FOUND_ITEMS.LOST_ID.isNull()).and(FOUND_ITEMS.USER_UNIQUE_ID.equal(userUniqueId)).fetch();
        return getFoundItems(foundItems);
    }

    public FoundItem getFoundItemById(Integer id){
        Record foundItem = myContext.select().from(FOUND_ITEMS).where(FOUND_ITEMS.ID.equal(id)).fetchOne();
        Map<Integer, Result<FoundTagsRecord>> foundTagsRecord = myContext.selectFrom(FOUND_TAGS).where(FOUND_TAGS.ID.equal(id)).fetch().intoGroups(FOUND_TAGS.ID);
        Map<Integer, List<String>> foundTags = new HashMap<>();
        foundTagsRecord.entrySet().forEach(entry -> foundTags.put(entry.getKey(), entry.getValue().getValues(FOUND_TAGS.TAGS)));
        return generateFoundItem(foundItem,foundTags);
    }

    private List<FoundItem> getFoundItems(Result<Record> foundItemsRecord) {
        Map<Integer, Result<FoundTagsRecord>> foundTagsRecord = myContext.selectFrom(FOUND_TAGS).fetch().intoGroups(FOUND_TAGS.ID);
        Map<Integer, List<String>> foundTags = new HashMap<>();
        foundTagsRecord.entrySet().forEach(entry -> foundTags.put(entry.getKey(), entry.getValue().getValues(FOUND_TAGS.TAGS)));
        List<FoundItem> foundItemsList = new ArrayList<>();
        foundItemsRecord.forEach(result -> foundItemsList.add(generateFoundItem(result, foundTags)));
        return foundItemsList;
    }

    private FoundItem generateFoundItem(Record result, Map<Integer, List<String>> foundTags) {
        return new FoundItem(
                result.getValue(FOUND_ITEMS.ID), result.getValue(FOUND_ITEMS.GEOLOCATION),
                result.getValue(FOUND_ITEMS.TIME_STAMP), result.getValue(FOUND_ITEMS.USER_UNIQUE_ID),
                foundTags.get(result.getValue(FOUND_ITEMS.ID)), result.getValue(FOUND_ITEMS.PICTURE_URL));
    }

    public int insertFoundItemWithTags(Item foundItem) {
        FoundItemsRecord outputFoundItem = myContext.insertInto(FOUND_ITEMS,
                FOUND_ITEMS.GEOLOCATION, FOUND_ITEMS.TIME_STAMP, FOUND_ITEMS.USER_UNIQUE_ID, FOUND_ITEMS.PICTURE_URL)
                .values(foundItem.location, foundItem.timestamp, foundItem.uniqueId, foundItem.pictureURL).returning(FOUND_ITEMS.ID).fetchOne();
        foundItem.id = outputFoundItem.value1();
        foundItem.tags.forEach(tag -> myContext.insertInto(FOUND_TAGS, FOUND_TAGS.ID, FOUND_TAGS.TAGS).values(foundItem.id, tag).execute());
        return foundItem.id;
    }


}
