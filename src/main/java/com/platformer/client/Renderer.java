package com.platformer.client;

import com.platformer.core.Block;
import com.platformer.core.World;
import com.platformer.core.WorldView;

import java.awt.*;

public class Renderer {

    public static final int TILE_SIZE=32;

    private final Color PLAYER_COLOR=Color.black;
    private final Camera camera;
    private final Texture texture;
    private final WorldView view;

    public Renderer(Camera camera,Texture texture,WorldView view){
        this.camera=camera;
        this.texture=texture;
        this.view=view;
    }

    public void paint(Graphics2D g){

        var TILE_HEIGHT=view.getHeight();
        var TILE_WIDTH=view.getWidth();

        var cam=camera.getCamera();

        int firstCol = Math.max(0, Math.floorDiv(cam.x, TILE_SIZE));
        int lastCol  = Math.min((int)Math.floor(TILE_WIDTH  - 1), Math.floorDiv(cam.x + cam.width  - 1, TILE_SIZE));
        int firstRow = Math.max(0, Math.floorDiv(cam.y, TILE_SIZE));
        int lastRow  = Math.min((int)Math.ceil(TILE_HEIGHT - 1), Math.floorDiv(cam.y + cam.height - 1, TILE_SIZE));

        for(int i=firstRow;i<=lastRow;i++){
            for(int j=firstCol;j<=lastCol;j++){
                Block block=view.getBlockAt(j,i);
                if(texture.getBlockColor(block)==null)continue;
                g.setColor(texture.getBlockColor(block));
                g.fillRect(j*TILE_SIZE,i*TILE_SIZE,TILE_SIZE,TILE_SIZE);
            }
        }

        var x=(int)Math.round(view.getPlayerX()*TILE_SIZE);
        var y=(int)Math.round(view.getPlayerY()*TILE_SIZE);
        var width=(int)Math.round(view.getWidth()*TILE_SIZE);
        var height=(int)Math.round(view.getHeight()*TILE_SIZE);

        g.setColor(PLAYER_COLOR);
        g.fillRect(x,y,width,height);

    }

}
