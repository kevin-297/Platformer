package com.platformer.core;

public record PlayerInput   (
                            boolean keyRight, boolean keyLeft,
                            boolean space,
                            int selected,
                            boolean mouseRight, boolean mouseLeft,
                            int sTileX,int sTileY
                            ) {
}
