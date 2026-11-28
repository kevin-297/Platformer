package com.platformer.client;

import com.platformer.core.*;

import java.awt.*;
import java.awt.geom.Point2D;

public class HUD {

    private final Texture texture;
    private final WorldView view;
    private PlayerInput input;

    public HUD(WorldView view, Texture texture){
        this.view=view;
        this.texture=texture;
        this.input=new PlayerInput(
                false,false,
                false,
                1,
                false,false,
                0,0
        );
    }

    public void update(PlayerInput input){
        this.input=input;
    }

    public void paint(Graphics g){
        int BLOCK_SIZE=16;
        Rectangle r =g.getClipBounds();
        Rectangle block=new Rectangle((r.width-BLOCK_SIZE)/2,5,BLOCK_SIZE,BLOCK_SIZE);

        g.setColor(texture.getBlockColor(Block.fromID(input.selected())));
        g.fillRect(block.x,block.y,block.width,block.height);

        g.setColor(Color.black);
        g.drawRect(block.x,block.y,block.width,block.height);

        if(view!=null)
            g.drawString(String.format("%.2f,%.2f",view.getPlayerX(),view.getPlayerY()),10,10);

    }
}
