# Docker hosts schema

# !--- !Ups

CREATE TABLE `Users` (
  `UserID` VARCHAR(254),
  `ProviderID` VARCHAR(254),
  `ProviderKey` VARCHAR(254),
  `Name` VARCHAR(254),
  `Handle` VARCHAR(254) UNIQUE,
  `Bio` VARCHAR(254),
  `Email` VARCHAR(254),
  `AvatarURL` VARCHAR(254),
  `Activated` BOOLEAN,
  PRIMARY KEY(`ProviderID`, `ProviderKey`)
);

create table `Tools` (
  `Name` VARCHAR(254) NOT NULL,
  `OwnerHandle` VARCHAR(254) NOT NULL,
  `ReviewerHandle` VARCHAR(254),
  `SourceCode` VARCHAR(2048) NOT NULL,
  `ReadmeMd` VARCHAR(2048) NOT NULL,
  `ReadmeHtml` VARCHAR(4096) NOT NULL,
  `Title` VARCHAR(254) NOT NULL,
  `Created` TIMESTAMP NOT NULL,
  PRIMARY KEY(`OwnerHandle`, `Name`)
);

# !-- !Downs

drop table Tools;
drop table Users;
