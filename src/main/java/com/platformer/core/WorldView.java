package com.platformer.core;

public interface WorldView {
    boolean isSolid(int x,int y);

    double getWidth();
    double getHeight();
    Block getBlockAt(int x, int y);

    double getPlayerWidth();
    double getPlayerHeight();
    double getPlayerX();
    double getPlayerY();
}
