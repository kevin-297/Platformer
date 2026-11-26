package com.platformer;

import java.awt.*;

import static com.platformer.World.TILE_SIZE;

public class Renderer {

    private final Color PLAYER_COLOR=Color.black;
    private final Camera camera;
    private final Texture texture;
    private final World world;

    public Renderer(Camera camera,Texture texture,World world){
        this.camera=camera;
        this.texture=texture;
        this.world=world;
    }

    public void paint(Graphics2D g){

        var TILE_HEIGHT=world.getHeight();
        var TILE_WIDTH=world.getWidth();

        var view=camera.getCamera();
        var player=world.getPlayer();

        int firstCol = Math.max(0, Math.floorDiv(view.x, TILE_SIZE));
        int lastCol  = Math.min(TILE_WIDTH  - 1, Math.floorDiv(view.x + view.width  - 1, TILE_SIZE));
        int firstRow = Math.max(0, Math.floorDiv(view.y, TILE_SIZE));
        int lastRow  = Math.min(TILE_HEIGHT - 1, Math.floorDiv(view.y + view.height - 1, TILE_SIZE));

        for(int i=firstRow;i<=lastRow;i++){
            for(int j=firstCol;j<=lastCol;j++){
                Block block=world.getBlockAt(j,i);
                if(texture.getBlockColor(block)==null)continue;
                g.setColor(texture.getBlockColor(block));
                g.fillRect(j*TILE_SIZE,i*TILE_SIZE,TILE_SIZE,TILE_SIZE);
            }
        }
        Rectangle p=player.getBounds();

        g.setColor(PLAYER_COLOR);
        g.fillRect(p.x,p.y,p.width,p.height);

    }

}
