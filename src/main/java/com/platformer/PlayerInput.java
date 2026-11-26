package com.platformer;

public record PlayerInput   (
                            boolean keyRight, boolean keyLeft,
                            boolean space,
                            int selected,
                            boolean mouseRight, boolean mouseLeft,
                            int mouseX,int mouseY
                            ) {
}
