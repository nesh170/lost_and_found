-- Authentication(uniqueID,name,netID)
CREATE TABLE auth_table(
  unique_id VARCHAR(7) PRIMARY KEY ,
  name      VARCHAR(50) NOT NULL ,
  net_id    VARCHAR(10) NOT NULL UNIQUE
);

-- LostItems(lostID, geoLocation, timestamp, user.uniqueID REFERENCES Authentication.uniqueID)
CREATE TABLE lost_items(
  id SERIAL PRIMARY KEY ,
  time_stamp TIMESTAMP ,
  geolocation VARCHAR(100) ,
  user_unique_id VARCHAR(7) REFERENCES auth_table(unique_id)
);

-- LostTags(lostID REFERENCES LostItems.lostID, tags)
CREATE TABLE lost_tags(
  id INTEGER REFERENCES lost_items(id),
  tags varchar(30) NOT NULL
);

-- FoundItems(foundID, geolocation, timestamp, user.uniqueID REFERENCES Authentication.uniqueID)
CREATE TABLE found_items(
  id SERIAL PRIMARY KEY ,
  time_stamp TIMESTAMP ,
  geolocation VARCHAR(100) ,
  user_unique_id VARCHAR(7) REFERENCES auth_table(unique_id)
);

-- FoundTags(foundID REFERENCES FoundItems.foundID, tags)
CREATE TABLE found_tags(
  id INTEGER REFERENCES found_items(id),
  tags varchar(30) NOT NULL
);

