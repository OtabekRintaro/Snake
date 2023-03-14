package model;

public enum LevelItem {
    STONE('s'), WALL('#'), EMPTY(' '), APPLE('a');
    LevelItem(char rep){ representation = rep; }
    public final char representation;
}
