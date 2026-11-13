package com.platformer;

import java.awt.*;
import java.awt.geom.Point2D;

public class Player {

    private double x,y;
    private double vx,vy;

    private boolean onGround=false;

    private final Color PLAYER_COLOR=Color.black;

    private final double EPS=0.0001;

    private final int MAX_H_SPEED=200;
    private final int MAX_FALL_SPEED =500;
    private final int H_ACCELERATION=800;
    private final int TH_ACCELERATION=1600;
    private final int JUMP_SPEED =600;
    private final int FRICTION = 1600;

    private final int WIDTH =20;
    private final int HEIGHT=50;

    public Player(int x,int y){
        this.x=x;
        this.y=y;
    }

    public Point2D getLoc(){
        return new Point2D.Double(x+WIDTH/2D,y+HEIGHT/2D);
    }

    public void update(double dt){

        double ay=World.GRAVITY;
        vy+=ay*dt;

        if(Input.isJump() && onGround)
            vy = -JUMP_SPEED;

        vy=(vy> MAX_FALL_SPEED)? MAX_FALL_SPEED :vy;

        onGround=false;
        y+=vy*dt;

        int topTile=(int)Math.floor(y/World.TILE_SIZE);
        int downTile=(int)Math.floor((y+HEIGHT-EPS)/World.TILE_SIZE);
        int leftTile=(int)Math.floor(x/World.TILE_SIZE);
        int rightTile=(int)Math.floor((x+WIDTH-EPS)/World.TILE_SIZE);

        if(vy!=0 ){
            for(int tx=leftTile;tx<=rightTile;tx++) {
                int ty=(vy>0)?downTile:topTile;
                if (World.isSolid(tx, ty)){
                    if(vy>0) {
                        y = ty * World.TILE_SIZE - HEIGHT;
                        onGround=true;
                    }
                    else
                        y = (ty+1) * World.TILE_SIZE;
                    vy = 0;
                    break;
                }
            }
        }

        byte dir=(byte)((Input.isRight()?1:0)+(Input.isLeft()?-1:0));

        double ax;

        if(dir!=0) {
            if (vx != 0 && dir!= Math.signum(vx))
                ax = dir * TH_ACCELERATION;
            else
                ax = dir * H_ACCELERATION;
            vx+= ax * dt;
        }
        else{
            double drop= FRICTION *dt;
            if(Math.abs(vx)<= drop)
                vx=0;
            else
                vx-= Math.signum(vx) * drop;
        }

        if(Math.abs(vx)>MAX_H_SPEED)
            vx=Math.signum(vx)*MAX_H_SPEED;

        x+=vx*dt;

        topTile=(int)Math.floor(y/World.TILE_SIZE);
        downTile=(int)Math.floor((y+HEIGHT-EPS)/World.TILE_SIZE);
        leftTile=(int)Math.floor(x/World.TILE_SIZE);
        rightTile=(int)Math.floor((x+WIDTH-EPS)/World.TILE_SIZE);

        if(vx!=0 ){
            for(int ty=topTile;ty<=downTile;ty++) {
                int tx=(vx>0)?rightTile:leftTile;
                if (World.isSolid(tx, ty)){
                    if(vx>0)
                        x =tx * World.TILE_SIZE-WIDTH;
                    else
                        x = (tx+1) * World.TILE_SIZE;
                    vx = 0;
                    break;
                }
            }
        }

    }

    public void paint(Graphics g){

        g.setColor(PLAYER_COLOR);
        g.fillRect((int)Math.round(x),(int)Math.round(y),WIDTH,HEIGHT);

    }

}
