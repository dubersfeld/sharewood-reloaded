DROP DATABASE IF EXISTS sharewood_users;

CREATE DATABASE sharewood_users DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE sharewood_users;

-- database for users only


CREATE TABLE user (
  userId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(30) NOT NULL,
  hashedPassword BINARY(68) NOT NULL,
  accountNonExpired BOOLEAN NOT NULL,
  accountNonLocked BOOLEAN NOT NULL,
  credentialsNonExpired BOOLEAN NOT NULL,
  enabled BOOLEAN NOT NULL,
  CONSTRAINT user_unique UNIQUE (username)
) ENGINE = InnoDB;

CREATE TABLE user_Authority (
  userId BIGINT UNSIGNED NOT NULL,
  authority VARCHAR(100) NOT NULL,
  UNIQUE KEY user_Authority_User_Authority (userId, authority),
  CONSTRAINT user_Authority_UserId FOREIGN KEY (userId)
    REFERENCES user (userId) ON DELETE CASCADE
) ENGINE = InnoDB;



INSERT INTO user (username, hashedPassword, accountNonExpired,
                           accountNonLocked, credentialsNonExpired, enabled)
VALUES ( -- s1a2t3o4r
  'Carol', '{bcrypt}$2a$10$qC5Nac5EvZ3TtOyZyNyA3uN3WlNIoBDMItrANlktLMtY65B3KON/C',
  TRUE, TRUE, TRUE, TRUE
);

INSERT INTO user (username, hashedPassword, accountNonExpired,
                           accountNonLocked, credentialsNonExpired, enabled)
VALUES ( -- o8p7e6r5a
  'Alice', '{bcrypt}$2a$10$V45DS2eQ0lXsoVOOyE8.duO/koj8jXl2LvywOtPMlBtaLv9s8GeO6',
  TRUE, TRUE, TRUE, TRUE
);


INSERT INTO user_Authority (UserId, Authority)
  VALUES (1, 'USER');

INSERT INTO user_Authority (UserId, Authority)
  VALUES (2, 'USER');




