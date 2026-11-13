package com.platformer;

import java.awt.*;
import java.awt.geom.Point2D;

public class Camera {

    private double x,y;
    private volatile long drawX,drawY;
    private int viewWidth,viewHeight;

    public Camera(int width,int height){
        viewWidth=width;
        viewHeight=height;
    }

    public void update(double dt, double playerX, double playerY, int width, int height) {

        x=playerX-viewWidth/2D;
        y=playerY-viewHeight/2D;

        x=x<0?0:x>width -viewWidth ?width -viewWidth :x;
        y=y<0?0:y>height-viewHeight?height-viewHeight:y;

        publish();

    }

    private void publish(){
        drawX=Math.round(x);
        drawY=Math.round(y);
    }

    public Dimension getView(){
        return new Dimension(viewWidth,viewHeight);
    }

    public Rectangle getCamera(){
        return new Rectangle((int)drawX,(int)drawY,viewWidth,viewHeight);
    }

    public void applyCamera(Graphics2D g){
        g.translate(-drawX,-drawY);
    }
}
