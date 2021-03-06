DROP SCHEMA public CASCADE ;
CREATE SCHEMA public;

-- Authentication(uniqueID,name,netID)
CREATE TABLE IF NOT EXISTS auth_table(
  unique_id  VARCHAR(7) ,
  given_name VARCHAR(50) NOT NULL ,
  net_id     VARCHAR(10) NOT NULL UNIQUE,
  email      VARCHAR(256) NOT NULL UNIQUE,
  auth_token TEXT NOT NULL UNIQUE,
  CONSTRAINT PK_auth_table PRIMARY KEY (unique_id)
);

-- LostItems(lostID, geoLocation, timestamp, user.uniqueID, found_id)
CREATE TABLE IF NOT EXISTS lost_items(
  id SERIAL ,
  time_stamp TIMESTAMP NOT NULL,
  geolocation VARCHAR(100) NOT NULL,
  user_unique_id VARCHAR(7) NOT NULL,
  found_id INTEGER,
  picture_url VARCHAR(2000) UNIQUE,
  CONSTRAINT PK_lost_items PRIMARY KEY (id) ,
  CONSTRAINT FK_lost_items_user_unique_id FOREIGN KEY (user_unique_id) REFERENCES auth_table(unique_id)
);

-- LostTags(lostID REFERENCES LostItems.lostID, tags)
CREATE TABLE IF NOT EXISTS lost_tags(
  id INTEGER ,
  tags varchar(30) NOT NULL ,
  CONSTRAINT FK_lost_tags_id FOREIGN KEY (id) REFERENCES lost_items (id)
);

-- FoundItems(foundID, geolocation, timestamp, user.uniqueID, lost_id)
CREATE TABLE IF NOT EXISTS found_items(
  id SERIAL ,
  time_stamp TIMESTAMP NOT NULL,
  geolocation VARCHAR(100) NOT NULL,
  user_unique_id VARCHAR(7) REFERENCES auth_table(unique_id) ,
  lost_id INTEGER,
  picture_url VARCHAR(2000) UNIQUE,
  CONSTRAINT PK_found_items PRIMARY KEY (id) ,
  CONSTRAINT FK_found_items_user_unique_id FOREIGN KEY (user_unique_id) REFERENCES auth_table(unique_id)
);

-- FoundTags(foundID REFERENCES FoundItems.foundID, tags)
CREATE TABLE IF NOT EXISTS found_tags(
  id INTEGER ,
  tags varchar(30) NOT NULL ,
  CONSTRAINT FK_found_tags_id FOREIGN KEY (id) REFERENCES found_items (id)
);