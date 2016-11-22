package data.accessors;

import static db.Tables.FOUND_ITEMS;
import static db.tables.LostItems.LOST_ITEMS;

public class ItemAccessor extends Accessor {

    public ItemAccessor() {
        super();
    }

    public boolean markItemAsFound(int foundId, int lostId) {
        int lostSuccess = (lostId == -1) ? -1 : myContext.update(LOST_ITEMS).set(LOST_ITEMS.FOUND_ID, foundId).where(LOST_ITEMS.ID.equal(lostId)).execute();
        int foundSuccess = (foundId == -1) ? -1 : myContext.update(FOUND_ITEMS).set(FOUND_ITEMS.LOST_ID, lostId).where(FOUND_ITEMS.ID.equal(foundId)).execute();
        return lostSuccess == 1 || foundSuccess == 1;
    }

}
