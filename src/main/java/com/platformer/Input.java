package com.platformer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private static volatile boolean right,left=false;
    private static volatile boolean jump=false;
    private static volatile Block selected=Block.DIRT;

    public static boolean isRight(){
        return right;
    }
    public static boolean isLeft(){
        return left;
    }
    public static boolean isJump(){
        return jump;
    }
    public static Block getSelected(){
        return selected;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_D,KeyEvent.VK_RIGHT ->right=true;
            case KeyEvent.VK_A,KeyEvent.VK_LEFT  ->left=true;
            case KeyEvent.VK_SPACE -> jump=true;
            case KeyEvent.VK_1 -> selected=Block.GRASS;
            case KeyEvent.VK_2 -> selected=Block.DIRT;
            case KeyEvent.VK_3 -> selected=Block.STONE;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_D,KeyEvent.VK_RIGHT -> right=false;
            case KeyEvent.VK_A,KeyEvent.VK_LEFT  -> left=false;
            case KeyEvent.VK_SPACE -> jump=false;
        }
    }

    //Unused
    public void keyTyped(KeyEvent e) {}

}
