DROP DATABASE TicTacToe;
CREATE DATABASE TicTacToe;
USE TicTacToe;

CREATE TABLE users (
   username VARCHAR(30) NOT NULL,
   password BLOB NOT NULL,
   win int DEFAULT 0,
   draw int DEFAULT 0,
   loss int DEFAULT 0,
   registered DATETIME NOT NULL DEFAULT NOW(),
   waiting boolean DEFAULT false,
   ipAddress VARCHAR(20),
   portNumber int,
   connected boolean DEFAULT false,
   PRIMARY KEY(username)
);


CREATE TABLE generalChat (
   id_message int NOT NULL AUTO_INCREMENT,
   id_user VARCHAR(30) NOT NULL,
   content VARCHAR(100) NOT NULL,
   sent_at DATETIME NOT NULL DEFAULT NOW(), 
   PRIMARY KEY(id_message),
   FOREIGN KEY(id_user) REFERENCES users(username)
);


CREATE TABLE games (
	id_game int NOT NULL AUTO_INCREMENT,
	playerX varchar(30) NOT NULL,
	playerO varchar(30) NOT NULL,
	state varchar(10) NOT NULL DEFAULT '..........',
	userTurn varchar(30) NOT NULL,
	isFinished bool NOT NULL DEFAULT false,
	isDraw bool NOT NULL DEFAULT false,
	winner varchar(30) NOT NULL DEFAULT 'none',
	created_at DATETIME NOT NULL DEFAULT NOW(),
	PRIMARY KEY (id_game),
	FOREIGN KEY(playerX) REFERENCES users(username),
	FOREIGN KEY(playerO) REFERENCES users(username)
);