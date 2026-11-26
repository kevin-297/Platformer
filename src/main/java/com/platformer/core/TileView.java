package com.platformer.core;

public interface TileView {
    boolean isSolid(int x,int y);

    int getWidth();
    int getHeight();
    Block getBlockAt(int x, int y);
}
