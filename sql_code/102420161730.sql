--Add foundID to lost table
ALTER TABLE lost_items ADD COLUMN found_id INTEGER REFERENCES found_items(id);

--Add foundID to lost table
ALTER TABLE found_items ADD COLUMN lost_id INTEGER REFERENCES lost_items(id);
