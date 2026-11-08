package com.platformer;

import java.awt.*;

public class Platform {

    private int x,y;

    private final Color PF_COLOR=Color.gray;
    private final int WIDTH=700;
    private final int HEIGHT =200;

    public Platform(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void paint(Graphics g){

        g.setColor(PF_COLOR);
        g.fillRect(x,y,WIDTH, HEIGHT);

    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return WIDTH;
    }
    public int getHeight(){
        return HEIGHT;
    }

}
