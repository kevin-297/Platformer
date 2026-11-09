package com.platformer;

import java.awt.*;
import java.awt.geom.Point2D;

public class World {

    public static final int WIDTH=1000;
    public static final int HEIGHT=1000;
    public static final int GRAVITY=1500;

    private final Player player=new Player(10,10);
    private final Platform platform=new Platform(0,300);

    public Point2D getPlayerLoc(){
        return player.getLoc();
    }
    public Dimension getWorldSize(){
        return new Dimension(WIDTH,HEIGHT);
    }

    public void update(double dt) {
        player.update(dt,platform);
    }
    public void paint(Graphics g){

        g.setColor(Color.black);
        g.drawRect(0,0,WIDTH,HEIGHT);

        platform.paint(g);
        player.paint(g);
    }
}
