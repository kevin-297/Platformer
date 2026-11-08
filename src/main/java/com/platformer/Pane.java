package com.platformer;

import javax.swing.*;
import java.awt.*;

public class Pane extends JPanel {

    private final int FPS=60;
    private final long delta=1_000_000_000/FPS;

    private final Color BG_COLOR=Color.white;

    private final World world=new World();

    public Pane(){
        init();
        loop();
    }

    private void init(){
        setPreferredSize(new Dimension(World.WIDTH,World.HEIGHT));
        setBackground(BG_COLOR);
        setOpaque(true);
        setFocusable(true);
        addKeyListener(Main.input);

    }
    private void loop(){
        new Thread(()->{
            long now=System.nanoTime();
            long next=now+delta;
            long elapsed;
            while(true){
                now=System.nanoTime();
                elapsed=next-now;
                if(elapsed<=0){
                    update(delta);
                    repaint();

                    next=now+delta;
                }
                else{
                    try {
                        Thread.sleep(elapsed/1_000_000,(int)elapsed%1_000_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();
    }

    private void update(long dt){
        world.update(dt);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        world.paint(g);

    }

}
