package com.platformer;

import java.awt.*;

public class HUD {

    public void paint(Graphics g){
        int BLOCK_SIZE=16;
        Rectangle r =g.getClipBounds();
        Rectangle block=new Rectangle((r.width-BLOCK_SIZE)/2,5,BLOCK_SIZE,BLOCK_SIZE);

        g.setColor(Input.getSelected().color);
        g.fillRect(block.x,block.y,block.width,block.height);

        g.setColor(Color.black);
        g.drawRect(block.x,block.y,block.width,block.height);

    }
}
