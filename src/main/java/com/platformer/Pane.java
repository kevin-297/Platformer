package com.platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Pane extends JPanel {

    private final int FPS=60;
    private final long delta=1_000_000_000/FPS;
    private final double step=1D/FPS;

    private final Color BG_COLOR=Color.white;

    private final World world=new World();
    private final Camera camera=new Camera(700,500);

    public Pane(){
        init();
        loop();
    }

    private void init(){
        Dimension screen=camera.getView();
        setPreferredSize(new Dimension(screen.width,screen.height));
        setBackground(BG_COLOR);
        setOpaque(true);
        setFocusable(true);
        addKeyListener(Main.input);

    }
    private void loop(){
        Thread gameLoop=new Thread(()->{
            long now=System.nanoTime();
            long next=now+delta;
            long elapsed;
            while(!Thread.interrupted()){
                now=System.nanoTime();
                elapsed=next-now;
                if(elapsed<=0){
                    update();
                    repaint();

                    next+=delta;
                }
                else{
                    try {
                        Thread.sleep((long)(elapsed/1_000_000),(int)(elapsed%1_000_000));
                    } catch (InterruptedException e) {
                        return;
                    }
                }

            }
        });
        gameLoop.setDaemon(true);
        gameLoop.start();
    }

    private void update(){
        world.update(step);

        Point2D player=world.getPlayerLoc();
        Dimension size=world.getWorldSize();

        camera.update(step,player.getX(),player.getY(),size.width,size.height);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g.create();
        camera.applyCamera(g2);
        world.paint(g2);
        g2.dispose();

    }

}
