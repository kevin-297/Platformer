package com.platformer.core;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.util.List;

public class World implements TileView {

    public static final int GRAVITY=47;
    public static final int REACH=5;

    private final byte[][] map;
    private final int TILE_WIDTH;
    private final int TILE_HEIGHT;

    private final Player player;

    public World(byte[][] map){
        this.map=map;
        TILE_HEIGHT=map.length;
        TILE_WIDTH=map[0].length;

        player=new Player(spawnX(),spawnY());
    }

    private double spawnY() {
        int x=(int)Math.floor(spawnX());
        for(int ty=0;ty<TILE_HEIGHT;ty++)
            if(Block.fromID(map[ty][x]).solid)return ty-Player.HEIGHT;
        return 0;
    }

    private double spawnX() {
        return TILE_WIDTH/2D;
    }

    public static World fromSeed(long seed,int width,int height){
        return new World(World.genMap(seed,width,height));
    }
    public static World fromFile(String filePath){
        return new World(World.loadMap(filePath));
    }

    public int getWidth(){
        return TILE_WIDTH;
    }
    public int getHeight(){
        return TILE_HEIGHT;
    }
    public Block getBlockAt(int x,int y){
        return Block.fromID(map[y][x]);
    }
    public Player getPlayer(){
        return player;
    }

    public boolean canReach(int tx,int ty){
        double dx=(tx+0.5)-(player.getX()+player.getWidth() /2);
        double dy=(ty+0.5)-(player.getY()+player.getHeight()/2);
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

    public static byte[][] loadMap(String filePath) {

        byte[][] grid;

        try(InputStream inputStream= ClassLoader.getSystemResourceAsStream(filePath)){
            if(inputStream==null)
                throw new IllegalStateException(filePath+" not found");

            List<String> rows=new BufferedReader(new InputStreamReader(inputStream)).lines().map(String::trim).filter(l -> !l.isEmpty()).toList();

            if(rows.isEmpty())
                throw new IllegalStateException(filePath+" is Empty");

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
            throw new UncheckedIOException("Failed to read "+filePath,e);
        }
    }
    public static byte[][] genMap(long seed,int width,int height){
        System.out.println(seed);
        return TerrainGen.generate(seed,width,height);
    }

    public boolean isSolid(int x,int y){
        return y >= map.length || y < 0 || x >= map[0].length || x < 0 || Block.fromID(map[y][x]).solid;
    }

    public void update(double dt, PlayerInput input) {
        player.update(dt,input,this);
        handleActions(player,input);
    }

    private void handleActions(Player player,PlayerInput input) {
        if(player.canAct()){
            int tx=input.sTileX();
            int ty=input.sTileY();

            if(input.mouseRight()&&placeBlock(tx,ty, Block.fromID(input.selected())))
                player.resetCoolDown();
            else if(input.mouseLeft()&&breakBlock(tx,ty))
                player.resetCoolDown();

        }
    }
}
