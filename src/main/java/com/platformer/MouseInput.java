package com.platformer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private volatile int x,y;
    private volatile boolean left,right;

    public boolean isRight() {return right;}
    public boolean isLeft() {return left;}
    public int getX() {return x;}
    public int getY() {return y;}

    @Override public void mouseMoved(MouseEvent e) {x=e.getX();y=e.getY();}
    @Override public void mouseDragged(MouseEvent e) {mouseMoved(e);}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
        if(SwingUtilities.isRightMouseButton(e))right=true;
        if(SwingUtilities.isLeftMouseButton(e))left=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseMoved(e);
        if(SwingUtilities.isRightMouseButton(e))right=false;
        if(SwingUtilities.isLeftMouseButton(e))left=false;
    }
}
