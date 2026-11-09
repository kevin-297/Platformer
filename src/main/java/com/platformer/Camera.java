package com.platformer;

import java.awt.*;

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
    public long worldToScreenX(double worldX) { return Math.round(worldX) - drawX; }
    public long worldToScreenY(double worldY) { return Math.round(worldY) - drawY; }
    public double screenToWorldX(int screenX) { return screenX + drawX; }
    public double screenToWorldY(int screenY) { return screenY + drawY; }

    public Dimension getView(){
        return new Dimension(viewWidth,viewHeight);
    }
    public void setViewWidth(int width){
        this.viewWidth=width;
    }
    public void setViewHeight(int height){
        this.viewHeight=height;
    }

    public void applyCamera(Graphics2D g){
        g.translate(-drawX,-drawY);
    }
}
