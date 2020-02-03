package com.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Board extends JPanel {

    private final int OFFSET = 30;
    private final int SPACE = 20;

    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggs;

    private Player soko;
    private int w = 0;
    private int h = 0;

    private boolean isCompleted = false;

    private List<String> levels ;

    private JFrame parent ;

    public Board(List<String> levels, JFrame parent) {

        this.levels = levels ;
        this.parent = parent ;
        initBoard();
    }

    private void initBoard() {

        setFocusable(true);
        Sim s = new Sim(levels.size(), parent);
        initWorld(0);
        s.start();
    }

    class Sim extends Thread{

        int lvls = 0;
        JFrame parent ;
        public Sim(int lvls, JFrame parent) {
            this.lvls = lvls ;
            this.parent = parent;
        }

        @Override
        public void run() {

            for (int i = 0 ; i < lvls ; i ++){

                initWorld(i);
                repaint();

                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            parent.setVisible(false);

        }
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    private void initWorld(int index) {

        walls = new ArrayList<>();
        baggs = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage b;

        for (int i = 0; i < levels.get(index).length(); i++) {

            char item = levels.get(index).charAt(i);

            switch (item) {

                case '\n':
                    y += SPACE;

                    if (this.w < x) {
                        this.w = x;
                    }

                    x = OFFSET;
                    break;

                case '%':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '.':
                    b = new Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case 'P':
                    soko = new Player(x, y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }

            h = y;
        }
    }

    private void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Actor> world = new ArrayList<>();

        world.clear();
        world.addAll(walls);
        world.addAll(baggs);
        world.add(soko);

        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);

            if (item instanceof Player || item instanceof Baggage) {

                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {

                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (isCompleted) {

                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        buildWorld(g);
    }
}
