package com.platformer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Player {

    private double tileX,tileY;
    private double tileVX,tileVY;

    private boolean onGround=false;

    private final double EPS=0.0001;

    private final double MAX_H_SPEED=7;
    private final double MAX_FALL_SPEED =25;
    private final double H_ACCELERATION=44;
    private final double TH_ACCELERATION=88;
    private final double JUMP_SPEED =16;
    private final double FRICTION = 56;

    private final double ACTION_DELAY=0.1;
    private double cooldown;

    public static final double WIDTH =0.6;
    public static final double HEIGHT=1.6;

    public Player(double tileX,double tileY){
        this.tileX=tileX;
        this.tileY=tileY;
    }

    public double getX(){return tileX;}
    public double getY(){return tileY;}
    public double getWidth(){return WIDTH;}
    public double getHeight(){return HEIGHT;}

    public Rectangle getBounds(){
        return new Rectangle(
                (int)Math.round(getX()),
                (int)Math.round(getY()),
                (int)Math.round(getWidth()),
                (int)Math.round(getHeight())
        );
    }

    public boolean overlapsTile(int tx,int ty){
        return getBounds().intersects(tx,ty,1,1);
    }

    public boolean canAct(){
        return cooldown<=0;
    }
    public void resetCoolDown(){
        cooldown=ACTION_DELAY;
    }

    public void update(double dt,PlayerInput input,TileView mapView){

        cooldown=Math.max(0,cooldown-dt);

        double tileAY=World.GRAVITY;
        tileVY+=tileAY*dt;

        if(input.space() && onGround)
            tileVY = -JUMP_SPEED;

        tileVY= Math.min(tileVY, MAX_FALL_SPEED);

        onGround=false;
        tileY+=tileVY*dt;

        int topTile=(int)Math.floor(tileY);
        int downTile=(int)Math.floor(tileY+HEIGHT-EPS);
        int leftTile=(int)Math.floor(tileX);
        int rightTile=(int)Math.floor(tileX+WIDTH-EPS);

        if(tileVY!=0 ){
            for(int tx=leftTile;tx<=rightTile;tx++) {
                int ty=(tileVY>0)?downTile:topTile;
                if (mapView.isSolid(tx, ty)){
                    if(tileVY>0) {
                        tileY = ty-HEIGHT;
                        onGround=true;
                    }
                    else
                        tileY = ty+1;
                    tileVY = 0;
                    break;
                }
            }
        }

        byte dir=(byte)((input.keyRight()?1:0)+(input.keyLeft()?-1:0));

        double tileAX;

        if(dir!=0) {
            if (tileVX != 0 && dir!= Math.signum(tileVX))
                tileAX = dir * TH_ACCELERATION;
            else
                tileAX = dir * H_ACCELERATION;
            tileVX+= tileAX * dt;
        }
        else{
            double drop= FRICTION *dt;
            if(Math.abs(tileVX)<= drop)
                tileVX=0;
            else
                tileVX-= Math.signum(tileVX) * drop;
        }

        if(Math.abs(tileVX)>MAX_H_SPEED)
            tileVX=Math.signum(tileVX)*MAX_H_SPEED;

        tileX+=tileVX*dt;

        topTile=(int)Math.floor(tileY);
        downTile=(int)Math.floor(tileY+HEIGHT-EPS);
        leftTile=(int)Math.floor(tileX);
        rightTile=(int)Math.floor(tileX+WIDTH-EPS);

        if(tileVX!=0 ){
            for(int ty=topTile;ty<=downTile;ty++) {
                int tx=(tileVX>0)?rightTile:leftTile;
                if (mapView.isSolid(tx, ty)){
                    if(tileVX>0)
                        tileX = tx-WIDTH;
                    else
                        tileX = tx+1;
                    tileVX = 0;
                    break;
                }
            }
        }

    }

}
