package com.platformer;

import java.awt.*;

public class Player {

    private double x,y;
    private double vx,vy;

    private boolean onGround=true;

    private final Color PLAYER_COLOR=Color.black;

    private final int MAX_H_SPEED=200;
    private final int MAX_FALL_SPEED =500;
    private final int H_ACCELERATION=800;
    private final int TH_ACCELERATION=1600;
    private final int JUMP_SPEED =600;
    private final int FRICTION = 1600;

    private final int WIDTH =50;
    private final int HEIGHT=50;

    public Player(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void update(double dt,Platform platform){
        double ay=World.GRAVITY;
        vy+=ay*dt;

        if(Input.isJump() && onGround) {
            vy = -JUMP_SPEED;
            onGround=false;
        }

        vy=(vy> MAX_FALL_SPEED)? MAX_FALL_SPEED :vy;

        y+=vy*dt;

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

        byte dir=(byte)((Input.isRight()?1:0)+(Input.isLeft()?-1:0));

        double ax;

        if(dir!=0) {
            if (vx != 0 && Integer.signum(dir) != Integer.signum((int) vx))
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
        //continue friction handling

        if(Math.abs(vx)>MAX_H_SPEED)
            vx=Math.signum(vx)*MAX_H_SPEED;

        x+=vx*dt;
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
