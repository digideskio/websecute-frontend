# --- !Ups

INSERT INTO Users (UserID,ProviderID,ProviderKey,FirstName,LastName,FullName,Email,AvatarURL,Activated) VALUES
(X'5EAB4829ACC9487E881F0317FD518AAA','credentials', 'GrantZvolsky@gmail.com', 'Grant','Zvolský','Grant Zvolský','GrantZvolsky@gmail.com','https://secure.gravatar.com/avatar/47e1664c23915996febe3ed86342e6ad?d=404','1');


INSERT INTO Tools (`ToolID`,`UserID`,`ReviewerID`,`Variables`,`Name`,`Description`,`Script`,`OutputExt`,`Created`) VALUES
('1', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', 'MIN=5\r\nMAX=15', 'Generate numbers.', 'Generate number list', '#!/bin/bash\r\n\r\nseq \"$MIN\" \"$MAX\"', 'txt', TIMESTAMP '2015-04-08 20:34:57.331'),
('2', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', '# The video URL.\r\n# Make sure the URL does not contain optional parameters.\r\nYOUTUBEURL=https://www.youtube.com/watch?v=oHg5SJYRHA0', 'YouTube to MP3', 'Extracts audio from a YouTube video.', '#!/bin/bash\r\nyoutube-dl "$YOUTUBEURL" -q -x --no-warnings --audio-format mp3 -o /tmp/file.mp3\r\ncat /tmp/file.mp3', 'mp3', TIMESTAMP '2015-04-24 23:48:21'),
('3', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, 'Reverse gif', 'imagemagick example', '#!/bin/bash\r\nconvert gif:- -coalesce -reverse -quiet -layers OptimizePlus -loop 0 gif:-', 'gif', TIMESTAMP '2015-04-08 03:54:03.36'),
('4', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, 'Reverse gif with memory limit', 'imagemagick example with memory limit', '#!/bin/bash\r\nenv MAGICK_TMPDIR=/tmp convert -limit memory 50 -limit map 50 gif:- -coalesce -reverse -quiet -layers OptimizePlus -loop 0 gif:-', 'gif', TIMESTAMP '2015-04-08 03:54:03.36'),
('5', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, 'GIFURL=http://i.imgur.com/Bfa2L.gif', 'Reverse GIF from URL', 'V elipse spí lev.','#!/bin/bash\r\ncurl \"$GIFURL\" | convert gif:- -coalesce -reverse -quiet -layers OptimizePlus -loop 0 gif:-', 'gif', '2015-04-24 23:48:21'),
('6', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, NULL, 'PDF to PNG', 'Convert a PDF document to a PNG image.','#!/bin/bash\r\nconvert pdf:- png:-', 'png', '2015-04-24 23:48:21'),
('7', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, '# Time format: HH:MM:SS\r\nSTART=00:00:00\r\nDURATION=00:00:00', 'Trim audio file', 'A simple tool to trim an audio file using avconv.', '#!/bin/bash\r\n\r\navconv -i pipe: -ss \"$START\" -t \"$DURATION\" -f mp3 pipe:', 'mp3', '2015-05-02 15:47:26'),
('8', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', 'TEXT=', 'Echo tool', 'This tool is used for testing.', '#!/bin/bash\r\n\r\necho \"$TEXT\"', 'txt', TIMESTAMP '2015-04-08 20:34:57.331'),
('9', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, 'Playground', 'This tool is empty. Use it to debug scripts.', '#!/bin/bash\r\n', 'txt', TIMESTAMP '2015-04-08 20:34:57.331'),
('10', X'5EAB4829ACC9487E881F0317FD518AAA', X'5EAB4829ACC9487E881F0317FD518AAA', NULL, 'EPUB to PDF', 'A simple tool used to convert an EPUB book to the PDF format.', '#!/bin/bash\r\ncd /tmp;;\r\ncat > file.epub;;\r\nsudo ebook-convert file.epub file.pdf > /dev/null;;\r\ncat file.pdf;;\r\n', 'pdf', TIMESTAMP '2015-04-08 20:34:57.331');

# --- !Downs

DELETE FROM Users;
DELETE FROM Tools;