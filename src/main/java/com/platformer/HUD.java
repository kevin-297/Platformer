package com.platformer;

import java.awt.*;
import java.awt.geom.Point2D;

public class HUD {

    Player player;
    Point2D coordinates;
    PlayerInput input;

    public void update(Player player,PlayerInput input,World world){
        this.player=player;
        this.input=input;
        this.coordinates=new Point2D.Double(player.getX()+player.getWidth()/2,world.getWorldSize().getHeight()-(player.getY()+player.getHeight()));
    }

    public void paint(Graphics g){
        int BLOCK_SIZE=16;
        Rectangle r =g.getClipBounds();
        Rectangle block=new Rectangle((r.width-BLOCK_SIZE)/2,5,BLOCK_SIZE,BLOCK_SIZE);

        g.setColor(Block.fromID(input.selected()).color);
        g.fillRect(block.x,block.y,block.width,block.height);

        g.setColor(Color.black);
        g.drawRect(block.x,block.y,block.width,block.height);

        if(player!=null)
            g.drawString(String.format("%.2f,%.2f",coordinates.getX(),coordinates.getY()),10,10);

    }
}
