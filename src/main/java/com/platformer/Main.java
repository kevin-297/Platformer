package com.platformer;

import javax.swing.*;

public class Main extends JFrame {

    public static Input input=new Input();

    private Pane pane=new Pane();

    public Main(){

        add(pane);
        setResizable(false);
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Main m = new Main();
            m.setVisible(true);
            m.pane.requestFocusInWindow();
        });
    }

}
