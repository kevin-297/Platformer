package com.platformer;

import java.awt.*;

public class Player {

    private double x,y;
    private double vx,vy;
    private double ax,ay;

    private boolean onGround=true;

    private final Color PLAYER_COLOR=Color.black;

    private final int MAX_H_SPEED=200;
    private final int MAX_FALL_SPEED =500;
    private final int H_ACCELERATION=800;
    private final int JUMP_SPEED =200;

    private final int WIDTH =50;
    private final int HEIGHT=50;

    public Player(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void update(long dt,Platform platform){
        ay=World.GRAVITY;
        vy+=ay*dt/1_000_000_000d;

        if(Input.isJump() && onGround) {
            vy = -JUMP_SPEED;
            onGround=false;
        }

        vy=(vy> MAX_FALL_SPEED)? MAX_FALL_SPEED :vy;

        y+=vy*dt/1_000_000_000d;

        //To fix later (fragile collision detection)
        if(y<=0){
            y=0;
            vy=0;
        }
        else if(y+HEIGHT>=platform.getY()){
            y=platform.getY()-HEIGHT;
            vy=0;
            onGround=true;
        }

        ax=((Input.isRight()?1:0)+(Input.isLeft()?-1:0))*H_ACCELERATION;

        vx+=ax*dt/1_000_000_000d;
        if(Math.abs(vx)>MAX_H_SPEED)
            vx=(vx/Math.abs(vx))*MAX_H_SPEED;

        x+=vx*dt/1_000_000_000d;
        if(x<=0){
            x=0;
            vx=0;
        }
        else if(x+WIDTH>=World.WIDTH){
            x=World.WIDTH-WIDTH;
            vx=0;
        }

    }

    public void paint(Graphics g){

        g.setColor(PLAYER_COLOR);
        g.fillRect((int)x,(int)y,WIDTH,HEIGHT);

    }

}
