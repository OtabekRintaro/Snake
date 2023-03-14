CREATE DATABASE IF NOT EXISTS snake;
USE snake;
CREATE TABLE IF NOT EXISTS HighScore (
  Difficulty VARCHAR(50) NOT NULL,
  GameLevel  INT NOT NULL,
  EatenApples INT NOT NULL,
  Nickname	 VARCHAR(50) NOT NULL,
  PRIMARY KEY(Difficulty, GameLevel)
);