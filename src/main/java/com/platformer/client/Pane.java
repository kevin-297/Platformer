package com.platformer.client;

import com.platformer.core.Player;
import com.platformer.core.PlayerInput;
import com.platformer.core.World;

import javax.swing.*;
import java.awt.*;

public class Pane extends JPanel {

    private final int FPS=60;
    private final long delta=1_000_000_000/FPS;
    private final double step=1D/FPS;

    private final Color BG_COLOR=Color.white;

    private volatile PlayerInput input=new PlayerInput(
            false,false,
            false,
            1,
            false,false,
            0,0
    );

    private final Texture texture=new Texture();
    private final World world=World.fromSeed(123,700,500);
    private final Camera camera=new Camera(700,500);
    private final Renderer renderer=new Renderer(camera,texture,world);
    private final HUD hud=new HUD(world.getPlayer(),texture);

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

        Point selected=getSelectedTile();

        input=new PlayerInput(
                keyboard.isRight(),
                keyboard.isLeft(),
                keyboard.isJump(),
                keyboard.getSelected(),
                mouse.isRight(),
                mouse.isLeft(),
                selected.x,
                selected.y
        );

        world.update(step,input);

        double T=Renderer.TILE_SIZE;
        Player player=world.getPlayer();

        int px=(int)Math.round(player.getX()*Renderer.TILE_SIZE);
        int py=(int)Math.round(player.getY()*Renderer.TILE_SIZE);
        int pw=(int)Math.round(player.getWidth()*Renderer.TILE_SIZE);
        int ph=(int)Math.round(player.getHeight()*Renderer.TILE_SIZE);

        camera.update(px+pw/2,py+ph/2,world.getWidth()*T,world.getHeight()*T);
        hud.update(input,world);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g.create();

        camera.applyCamera(g2);

        renderer.paint(g2);

        outline(g2);
        
        g2.dispose();

        hud.paint(g);

    }

    private void outline(Graphics2D g) {
        g.setColor(Color.black);

        int T=Renderer.TILE_SIZE;
        Point tile=getSelectedTile();

        if(world.canReach(tile.x,tile.y))
            g.drawRect(tile.x*T,tile.y*T,T,T);

    }
    private Point getSelectedTile(){
        Rectangle cam=camera.getCamera();
        int tx=Math.floorDiv(mouse.getX()+cam.x,Renderer.TILE_SIZE);
        int ty=Math.floorDiv(mouse.getY()+cam.y,Renderer.TILE_SIZE);

        return new Point(tx,ty);
    }

}
