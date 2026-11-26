package com.platformer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private static volatile boolean right,left=false;
    private static volatile boolean jump=false;
    private static volatile int selected=1;

    public boolean isRight(){
        return right;
    }
    public boolean isLeft(){
        return left;
    }
    public boolean isJump(){
        return jump;
    }
    public int getSelected(){
        return selected;
    }
    public void clearKeys(){
        right=false;left=false;jump=false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_D,KeyEvent.VK_RIGHT ->right=true;
            case KeyEvent.VK_A,KeyEvent.VK_LEFT  ->left=true;
            case KeyEvent.VK_SPACE -> jump=true;
            case KeyEvent.VK_1 -> selected=1;
            case KeyEvent.VK_2 -> selected=2;
            case KeyEvent.VK_3 -> selected=3;
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
