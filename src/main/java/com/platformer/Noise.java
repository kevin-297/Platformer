package com.platformer;

public final class Noise {
    private Noise() {}

    private static double hash(long seed, int x) {
        long h = seed + x * 0x9E3779B97F4A7C15L;
        h = (h ^ (h >>> 30)) * 0xBF58476D1CE4E5B9L;
        h = (h ^ (h >>> 27)) * 0x94D049BB133111EBL;
        h ^= h >>> 31;
        return (h >>> 11) * 0x1.0p-53;
    }

    private static double smooth(double t) { return t * t * (3 - 2 * t); }

    public static double value1D(long seed, double x) {
        int x0 = (int) Math.floor(x);
        double t = smooth(x - x0);
        return hash(seed, x0) * (1 - t) + hash(seed, x0 + 1) * t;
    }

    public static double fbm1D(long seed, double x, int octaves,double persistence) {
        double sum = 0, amp = 1, norm = 0, freq = 1;
        for (int i = 0; i < octaves; i++) {
            sum  += value1D(seed + 1013L * i, x * freq) * amp;
            norm += amp;
            amp  *= persistence;
            freq *= 2;
        }
        return sum / norm;
    }
}