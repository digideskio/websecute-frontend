# Docker hosts schema

# !--- !Ups

CREATE TABLE Users (
  UserID VARCHAR(255),
  ProviderID VARCHAR(255),
  ProviderKey VARCHAR(255),
  FirstName VARCHAR(2048),
  LastName VARCHAR(2048),
  FullName VARCHAR(2048),
  Email VARCHAR(2048),
  AvatarURL VARCHAR(2048),
  Activated BOOLEAN,
  PRIMARY KEY(ProviderID, ProviderKey)
);

# INSERT INTO BlogPostings VALUES(...);

# !-- !Downs

drop table Users;