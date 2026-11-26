package com.platformer;

import java.awt.*;

public enum Block{
    AIR(false),
    GRASS(true),
    DIRT(true),
    STONE(true);

    public final boolean solid;

    Block(boolean solid){
        this.solid=solid;
    }
    private static final Block[] ids=values();

    public static Block fromID(int id){
        return ids[id];
    }
}