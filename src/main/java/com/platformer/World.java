package com.platformer;

import java.awt.*;

public class World {

    public static final int WIDTH=700;
    public static final int HEIGHT=500;
    public static final int GRAVITY=500;

    private final Color WORLD_COLOR=Color.white;

    private final Player player=new Player(10,10);
    private final Platform platform=new Platform(0,300);

    public void update(long dt) {
        player.update(dt,platform);
    }
    public void paint(Graphics g){

        g.setColor(Color.black);
        g.drawRect(0,0,WIDTH,HEIGHT);

        platform.paint(g);
        player.paint(g);
    }
}
