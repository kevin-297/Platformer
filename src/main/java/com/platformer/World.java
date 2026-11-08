package com.platformer;

import java.awt.*;

public class World {

    public static final int WIDTH=700;
    public static final int HEIGHT=500;
    public static final int GRAVITY=1500;

    private final Player player=new Player(10,10);
    private final Platform platform=new Platform(0,300);

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
