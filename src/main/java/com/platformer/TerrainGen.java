package com.platformer;

public final class TerrainGen {
    private TerrainGen() {}

    public static byte[][] generate(long seed, int width, int height) {
        return generate(seed,width,height,TerrainSettings.defaults());
    }
    public static byte[][] generate(long seed, int width, int height,TerrainSettings S) {
        byte[][] grid = new byte[height][width];

        if (height < S.topMargin() + S.bottomMargin() + 1)
            throw new IllegalArgumentException("World too short for these margins");

        for (int x = 0; x < width; x++) {
            double n = Noise.fbm1D(seed, x / S.hillWidth(), S.octaves(),S.persistence());
            int surface = (int) Math.round(S.base() + (n - 0.5) * 2 * S.amplitude());
            surface = Math.clamp(surface, S.topMargin(), height - S.bottomMargin());

            for (int y = surface; y < height; y++) {
                int depth = y - surface;
                grid[y][x] = (byte) (depth == 0         ? S.top().ordinal()
                        : depth <= S.middleLayerDepth() ? S.middle().ordinal()
                        :                                 S.bottom().ordinal());
            }
        }
        return grid;
    }
}