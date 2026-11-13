package com.platformer;

import java.awt.*;

public class Player {

    private double tileX,tileY;
    private double tileVX,tileVY;

    private boolean onGround=false;

    private final Color PLAYER_COLOR=Color.black;

    private final double EPS=0.0001;

    private final double MAX_H_SPEED=7;
    private final double MAX_FALL_SPEED =25;
    private final double H_ACCELERATION=44;
    private final double TH_ACCELERATION=88;
    private final double JUMP_SPEED =16;
    private final double FRICTION = 56;

    private final double WIDTH =0.6;
    private final double HEIGHT=1.6;

    public Player(double tileX,double y){
        this.tileX=tileX;
        this.tileY=tileY;
    }

    public double getX(){return tileX;}
    public double getY(){return tileY;}
    public double getWidth(){return WIDTH;}
    public double getHeight(){return HEIGHT;}

    public Rectangle getBounds(){
        return new Rectangle(
                (int)Math.round(getX()*World.TILE_SIZE),
                (int)Math.round(getY()*World.TILE_SIZE),
                (int)Math.round(getWidth()*World.TILE_SIZE),
                (int)Math.round(getHeight()*World.TILE_SIZE)
        );
    }

    public void update(double dt){

        double tileAY=World.GRAVITY;
        tileVY+=tileAY*dt;

        if(Input.isJump() && onGround)
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
                if (World.isSolid(tx, ty)){
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

        byte dir=(byte)((Input.isRight()?1:0)+(Input.isLeft()?-1:0));

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
                if (World.isSolid(tx, ty)){
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

    public void paint(Graphics g){

        int T=World.TILE_SIZE;

        Rectangle p=getBounds();

        g.setColor(PLAYER_COLOR);
        g.fillRect(p.x,p.y,p.width,p.height);

    }

}
