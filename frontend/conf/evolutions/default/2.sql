# --- !Ups

INSERT INTO Users (UserID,ProviderID,ProviderKey,Name,Handle,Bio,Email,AvatarURL,Activated) VALUES
(X'5EAB4829ACC9487E881F0317FD518AAA','credentials', 'GrantZvolsky@gmail.com', 'Grant Zvolsky','grant','Grant Zvolský','GrantZvolsky@gmail.com','https://secure.gravatar.com/avatar/47e1664c23915996febe3ed86342e6ad?d=404','1');


INSERT INTO Tools (`Name`,`OwnerHandle`,`ReviewerHandle`,`SourceCode`,`ReadmeMd`,`ReadmeHtml`,`Title`,`Created`) VALUES
('t1name', 'grant', NULL, '#!/bin/bash\r\nMIN=5\r\nMAX=15\r\nseq \"$MIN\" \"$MAX\"', 'Generate numbers.', 'Generate <strong>number</strong> list', 'first', TIMESTAMP '2015-04-08 20:34:57.331'),
('t2name', 'grant', 'grant', '#!/bin/bash\r\nMIN=5\r\nMAX=15\r\nseq \"$MIN\" \"$MAX\"', 'Generate numbers.', '<strong>Generate </strong> number list', 'second', TIMESTAMP '2015-04-08 20:34:57.331'),
('t3name', 'grant', 'grant', '#!/bin/bash\r\nMIN=5\r\nMAX=15\r\nseq \"$MIN\" \"$MAX\"', 'Generate numbers.', 'Generate number <strong>list</strong>', 'third', TIMESTAMP '2015-04-08 20:34:57.331')

# --- !Downs

DELETE FROM Users;
DELETE FROM Tools;