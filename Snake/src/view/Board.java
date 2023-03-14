package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JPanel;
import model.Game;
import model.LevelItem;
import model.Position;
import res.ResourceLoader;

public class Board extends JPanel {
    private Game game;
    private final Image player, snakeHead, apple, wall, empty, stone;
    private double scale;
    private int scaled_size;
    private final int tile_size = 32;
    
    public Board(Game g) throws IOException{
        game = g;
        scale = 1.0;
        scaled_size = (int)(scale * tile_size);
        player = ResourceLoader.loadImage("res/snake_body.png");
        snakeHead = ResourceLoader.loadImage("res/snake_head.png");
        wall = ResourceLoader.loadImage("res/wall.png");
        apple = ResourceLoader.loadImage("res/apple.png");
        empty = ResourceLoader.loadImage("res/empty.png");
        stone = ResourceLoader.loadImage("res/stone.png");
    }
    
    public boolean setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        return refresh();
    }
    
    public boolean refresh(){
        if (!game.isLevelLoaded()) return false;
        Dimension dim = new Dimension(game.getLevelCols() * scaled_size, game.getLevelRows() * scaled_size);
        game.restart();
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D)g;
        int w = game.getLevelCols();
        int h = game.getLevelRows();
        
        for (int y = 0; y < h; y++)
        {
            for(int x = 0; x < w; x++)
            {
                Image img = null;
                img = empty;
                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
                Image img = null;
                LevelItem li = game.getItem(y, x);
                switch (li){
                    case STONE: img = stone; break;
                    case APPLE: img = apple; break;
                    case WALL: img = wall; break;
                }
                if (img == null) continue;
                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
        drawSnake(g);
    }
    
    private void drawSnake(Graphics g)
    {
        Graphics2D gr = (Graphics2D)g;
        LinkedList<Position> p = game.getPlayerPos();
        int index = 1;
        for(; index < p.size(); index++)
        {
            gr.drawImage(player, p.get(index).x * scaled_size, p.get(index).y * scaled_size, scaled_size, scaled_size,null);
        }
        gr.drawImage(snakeHead, p.get(0).x * scaled_size, p.get(0).y * scaled_size, scaled_size, scaled_size, null);
    }
}
