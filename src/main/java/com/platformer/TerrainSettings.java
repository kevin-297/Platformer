package com.platformer;

public record TerrainSettings(int base,
                              double amplitude,
                              double hillWidth,
                              int octaves,
                              double persistence,
                              int middleLayerDepth,
                              int topMargin,
                              int bottomMargin,
                              Block top,
                              Block middle,
                              Block bottom
                              ) {

    public static TerrainSettings defaults(){
        return new TerrainSettings(60, 14,
                40, 4, 0.5,
                4, 4, 8,
                Block.GRASS, Block.DIRT, Block.STONE);
    }

}
