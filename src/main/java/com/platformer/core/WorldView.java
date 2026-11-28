package com.platformer.core;

public interface WorldView {
    boolean isSolid(int x,int y);

    int getWorldWidth();
    int getWorldHeight();
    Block getBlockAt(int x, int y);

    double getPlayerWidth();
    double getPlayerHeight();
    double getPlayerX();
    double getPlayerY();
}
