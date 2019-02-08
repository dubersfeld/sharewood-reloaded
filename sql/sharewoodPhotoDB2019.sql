DROP DATABASE IF EXISTS sharewood_photos;

CREATE DATABASE sharewood_photos DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE sharewood_photos;

-- database for photos only

CREATE TABLE photo(
    id        	BIGINT NOT NULL AUTO_INCREMENT,
    title  	VARCHAR(20) NULL,
    username    VARCHAR(20) NOT NULL,
    shared	BOOLEAN NOT NULL, 	
    PRIMARY KEY (id)
) ENGINE = InnoDB;


INSERT INTO photo VALUES (
  '1', 'photo1', 'Alice', '0'
);

INSERT INTO photo VALUES (
  '2', 'photo2', 'Carol', '0'
);

INSERT INTO photo VALUES (
  '3', 'photo3', 'Alice', '0'
);

INSERT INTO photo VALUES (
  '4', 'photo4', 'Carol', '0'
);

INSERT INTO photo VALUES (
  '5', 'photo5', 'Alice', '0'
);

INSERT INTO photo VALUES (
  '6', 'photo6', 'Carol', '0'
);



