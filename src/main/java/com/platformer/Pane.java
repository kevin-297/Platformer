package com.platformer;

import javax.swing.*;
import java.awt.*;

public class Pane extends JPanel {

    private final int FPS=60;
    private final long delta=1_000_000_000/FPS;
    private final double step=1D/FPS;

    private final Color BG_COLOR=Color.white;

    private final double ACTION_DELAY=0.1;
    private double cooldown;

    private volatile PlayerInput input=new PlayerInput(
            false,false,
            false,
            1,
            false,false,
            0,0
    );

    private final World world=World.fromSeed(123,700,500);
    private final Camera camera=new Camera(700,500);
    private final HUD hud=new HUD();

    public static final KeyInput keyboard =new KeyInput();
    public static final MouseInput mouse=new MouseInput();
    public static final FocusInput focus=new FocusInput(keyboard);

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

        addFocusListener(focus);
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

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

        input=new PlayerInput(
                keyboard.isRight(),
                keyboard.isLeft(),
                keyboard.isJump(),
                keyboard.getSelected(),
                mouse.isRight(),
                mouse.isLeft(),
                mouse.getX(),
                mouse.getY()
        );

        cooldown-=step;
        if(cooldown<=0){
            Point selectedTile=getSelectedTile();
            int tx=selectedTile.x;
            int ty=selectedTile.y;

            if(input.mouseRight()&&world.placeBlock(tx,ty, Block.fromID(input.selected()))) {
                cooldown = ACTION_DELAY;
            }
            if(input.mouseLeft()&&world.breakBlock(tx,ty)) {
                cooldown = ACTION_DELAY;
            }

        }

        world.update(step,input);

        double T=World.TILE_SIZE;
        Rectangle player=world.getPlayerBounds();
        Dimension worldSize=world.getWorldSize();

        int px=player.x;
        int py=player.y;
        int pw=player.width;
        int ph=player.height;

        camera.update(px+pw/2,py+ph/2,worldSize.width*T,worldSize.height*T);
        hud.update(world.getPlayer(),input,world);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g.create();
        camera.applyCamera(g2);
        world.paint(g2,camera.getCamera());

        outline(g2);
        
        g2.dispose();

        hud.paint(g);

    }

    private void outline(Graphics2D g) {
        g.setColor(Color.black);

        int T=World.TILE_SIZE;
        Point tile=getSelectedTile();

        if(world.canReach(tile.x,tile.y))
            g.drawRect(tile.x*T,tile.y*T,T,T);

    }
    private Point getSelectedTile(){
        Rectangle cam=camera.getCamera();
        int tx=Math.floorDiv(input.mouseX()+cam.x,World.TILE_SIZE);
        int ty=Math.floorDiv(input.mouseY()+cam.y,World.TILE_SIZE);

        return new Point(tx,ty);
    }

}
