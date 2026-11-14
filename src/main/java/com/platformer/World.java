package com.platformer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Dimension;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.util.List;

public class World {

    public static final int GRAVITY=47;
    public static final int TILE_SIZE=32;
    public static final int REACH=5;

    private static final byte[][] map=loadMap();

    private static final int TILE_WIDTH=map[0].length;
    private static final int TILE_HEIGHT=map.length;

    private final Player player=new Player(10,10);

    public Rectangle getPlayerBounds(){
        return player.getBounds();
    }
    public Dimension getWorldSize(){
        return new Dimension(TILE_WIDTH,TILE_HEIGHT);
    }

    public boolean canReach(int tx,int ty){
        Rectangle2D b=player.getTileBounds();
        Point2D loc=new Point2D.Double(b.getX(),b.getY());
        double dx=(tx+0.5)-loc.getX();
        double dy=(ty+0.5)-loc.getY();
        return dx*dx+dy*dy<=REACH*REACH;
    }
    private boolean inBounds(int tx,int ty){
        return tx>=0 && tx<TILE_WIDTH && ty>=0 && ty<TILE_HEIGHT;
    }
    public boolean breakBlock(int tx,int ty){
        if(!inBounds(tx,ty) || !canReach(tx,ty)) return false;
        if(map[ty][tx]==Block.AIR.ordinal())return false;
        map[ty][tx]=(byte)Block.AIR.ordinal();
        return true;
    }
    public boolean placeBlock(int tx,int ty,Block block){
        if(!inBounds(tx,ty) || !canReach(tx,ty)) return false;
        if(map[ty][tx]!=Block.AIR.ordinal())return false;
        if(player.overlapsTile(tx,ty))return false;
        map[ty][tx]=(byte)block.ordinal();
        return true;
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
                    if(c<'0' || c-'0'>=Block.values().length)
                        throw new IllegalStateException("Unknown block id "+c);
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
        return y >= map.length || y < 0 || x >= map[0].length || x < 0 || Block.fromID(map[y][x]).solid;
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
                Block block=Block.fromID(map[i][j]);
                if(block.color==null)continue;
                g.setColor(block.color);
                g.fillRect(j*TILE_SIZE,i*TILE_SIZE,TILE_SIZE,TILE_SIZE);
            }
        }
        player.paint(g);
    }
}
