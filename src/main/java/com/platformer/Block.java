package com.platformer;

import java.awt.*;

public enum Block{
    AIR(false,null),
    GRASS(true,new Color(90,170,60)),
    DIRT(true,new Color(130,90,50)),
    STONE(true,new Color(120,120,120));

    public final boolean solid;
    public final Color color;

    Block(boolean solid,Color color){
        this.solid=solid;
        this.color=color;
    }
    private static final Block[] ids=values();

    public static Block fromID(int id){
        return ids[id];
    }
}