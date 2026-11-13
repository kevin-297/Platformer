package com.platformer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.List;

public class World {

    public static final int GRAVITY=1500;
    public static final int TILE_SIZE=32;

    private static final byte[][] map=loadMap();

    private static final int TILE_WIDTH=map[0].length;
    private static final int TILE_HEIGHT=map.length;

    public static final int WIDTH=TILE_WIDTH*TILE_SIZE;
    public static final int HEIGHT=TILE_HEIGHT*TILE_SIZE;

    private final Player player=new Player(10,10);

    public Point2D getPlayerLoc(){
        return player.getLoc();
    }
    public Dimension getWorldSize(){
        return new Dimension(WIDTH,HEIGHT);
    }

    public World(){
        loadMap();
    }

    private static byte[][] loadMap() {

        byte[][] grid;

        try(InputStream inputStream= ClassLoader.getSystemResourceAsStream("map.txt")){
            if(inputStream==null)
                throw new IllegalStateException("map.txt not found");

            List<String> rows=new BufferedReader(new InputStreamReader(inputStream)).lines().map(String::trim).filter(l -> !l.isEmpty()).toList();

            if(rows.isEmpty())
                throw new IllegalStateException("map.txt is Empty");

            grid=new byte[rows.size()][rows.getFirst().length()];

            int x=0,y=0;
            for(String row:rows){
                if(row.length()!=grid[0].length)
                    throw new IllegalStateException("Expected row size of : "+grid[0].length+" but found : "+row.length());
                for(char c:row.toCharArray()){
                    if("01".indexOf(c)==-1)
                        throw new IllegalStateException("Allowed chars are : 1 0 , found :"+c);
                    byte value=(byte) (c-'0');
                    grid[y][x]=value;
                    x++;
                }
                x=0;
                y++;
            }
            return grid;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read map.txt",e);
        }
    }

    public static boolean isSolid(int x,int y){
        return y >= map.length || y < 0 || x >= map[0].length || x < 0 || map[y][x] == 1;
    }

    public void update(double dt) {
        player.update(dt);
    }
    public void paint(Graphics g,Rectangle view){

        int firstCol = Math.max(0, Math.floorDiv(view.x, TILE_SIZE));
        int lastCol  = Math.min(TILE_WIDTH  - 1, Math.floorDiv(view.x + view.width  - 1, TILE_SIZE));
        int firstRow = Math.max(0, Math.floorDiv(view.y, TILE_SIZE));
        int lastRow  = Math.min(TILE_HEIGHT - 1, Math.floorDiv(view.y + view.height - 1, TILE_SIZE));


        for(int i=firstRow;i<=lastRow;i++){
            for(int j=firstCol;j<=lastCol;j++){
                g.setColor(isSolid(j,i)?Color.green:Color.white);
                g.fillRect(j*TILE_SIZE,i*TILE_SIZE,TILE_SIZE,TILE_SIZE);
            }
        }
        player.paint(g);
    }
}
