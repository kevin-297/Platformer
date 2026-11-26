package com.platformer.client;

import com.platformer.core.Block;

import java.awt.*;
import java.util.EnumMap;

public class Texture {

    private final EnumMap<Block, Color> blocks=new EnumMap<>(Block.class);

    public Texture(){
        loadTextures();
    }

    private void loadTextures(){
        blocks.put(Block.GRASS,new Color(90,170,60));
        blocks.put(Block.DIRT,new Color(130,90,50));
        blocks.put(Block.STONE,new Color(120,120,120));
    }

    public Color getBlockColor(Block block){
        return blocks.get(block);
    }

}
