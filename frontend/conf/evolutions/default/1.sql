# Docker hosts schema

# !--- !Ups

CREATE TABLE `Users` (
  `UserID` VARCHAR(254),
  `ProviderID` VARCHAR(254),
  `ProviderKey` VARCHAR(254),
  `FirstName` VARCHAR(254),
  `LastName` VARCHAR(254),
  `FullName` VARCHAR(254),
  `Email` VARCHAR(254),
  `AvatarURL` VARCHAR(254),
  `Activated` BOOLEAN,
  PRIMARY KEY(`ProviderID`, `ProviderKey`)
);

create table `Tools` (
  `ToolID` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `UserID` VARCHAR(254) NOT NULL,
  `ReviewerID` VARCHAR(254),
  `Variables` VARCHAR(1024),
  `Name` VARCHAR(254) NOT NULL,
  `Description` VARCHAR(254) NOT NULL,
  `Script` VARCHAR(2048) NOT NULL,
  `OutputExt` VARCHAR(254) NOT NULL,
  `Created` TIMESTAMP NOT NULL
);

# !-- !Downs

drop table Tools;
drop table Users;
