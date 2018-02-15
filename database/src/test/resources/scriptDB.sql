CREATE TABLE Game
(
  id         INT(10) AUTO_INCREMENT
    PRIMARY KEY,
  startTime  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP     NOT NULL,
  endTime    TIMESTAMP(6) DEFAULT '0000-00-00 00:00:00' NOT NULL,
  idGameMode INT(10)                                     NOT NULL
);

CREATE TABLE GameMode
(
  id        INT(10) AUTO_INCREMENT
    PRIMARY KEY,
  name      VARCHAR(64)        NOT NULL,
  minPlayer INT(10) DEFAULT 0  NOT NULL,
  maxPlayer INT(10) DEFAULT 10 NOT NULL
);

CREATE UNIQUE INDEX GameMode_name_uindex
  ON GameMode (name);

ALTER TABLE Game
  ADD CONSTRAINT Game_GameMode_id_fk
FOREIGN KEY (idGameMode) REFERENCES GameMode (id);

CREATE TABLE GameParticipation
(
  idGame  INT(10) NOT NULL,
  idSnake INT(10) NOT NULL,
  PRIMARY KEY (idGame, idSnake),
  CONSTRAINT GameParticipation_Game_id_fk
  FOREIGN KEY (idGame) REFERENCES Game (id)
);

CREATE TABLE Snake
(
  id           INT(10) AUTO_INCREMENT
    PRIMARY KEY,
  userID       INT(10)           NOT NULL,
  name         VARCHAR(64)       NOT NULL,
  exp          INT(10) DEFAULT 0 NOT NULL,
  info         BLOB              NULL,
  idSnakeClass INT(10)           NOT NULL
);

ALTER TABLE GameParticipation
  ADD CONSTRAINT GameParticipation_Snake_id_fk
FOREIGN KEY (idSnake) REFERENCES Snake (id);

CREATE TABLE SnakeClass
(
  id   INT(10) AUTO_INCREMENT
    PRIMARY KEY,
  name VARCHAR(64) NOT NULL
);

ALTER TABLE Snake
  ADD CONSTRAINT Snake_SnakeClass_id_fk
FOREIGN KEY (idSnakeClass) REFERENCES SnakeClass (id);

CREATE TABLE User
(
  id          INT(10) AUTO_INCREMENT
    PRIMARY KEY,
  alias       VARCHAR(64)  NOT NULL,
  email       VARCHAR(128) NOT NULL,
  accountName VARCHAR(64)  NOT NULL,
  password    VARCHAR(256) NOT NULL
);

CREATE UNIQUE INDEX User_accountName_uindex
  ON User (accountName);

ALTER TABLE Snake
  ADD CONSTRAINT snake_User_id_fk
FOREIGN KEY (userID) REFERENCES User (id);


