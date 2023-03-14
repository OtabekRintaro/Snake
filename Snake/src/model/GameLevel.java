package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class GameLevel {
    public final GameID        gameID;
    public final int           rows, cols;
    public final LevelItem[][] level;
    public LinkedList<Position>player = new LinkedList<>();
    public Direction           currentDirection = null;
    public Direction           bufferedDirection = null;
    public Position            apple = new Position(0,0);          
    public Timer               timer = new Timer();
    public int                 seconds = 0;
    public boolean             gameFinished;
    public int                 eatenApples; 
    
    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID){
        this.gameID = gameID;
        int c = 0;
        for (String s : gameLevelRows) if (s.length() > c) c = s.length();
        rows = gameLevelRows.size();
        cols = c;
        level = new LevelItem[rows][cols];
        player.add(new Position(cols/2,rows/2));
        gameFinished = false;
        seconds = 0;
        Random random = new Random();
        
        Direction randomDirection = Direction.values()[random.nextInt(4)];
        Position tail = player.getFirst().translate(randomDirection);
        player.add(tail);
        
        currentDirection = Direction.opposite(randomDirection);
        bufferedDirection = currentDirection;
        
        TimerTask rollSeconds = new TimerTask(){
            @Override
            public void run()
            {
                if(gameFinished)
                    this.cancel();
                seconds++;
            }
        };
        timer.schedule(rollSeconds, 0, 1000);
        
        for (int i = 0; i < rows; i++){
            String s = gameLevelRows.get(i);
            for (int j = 0; j < s.length(); j++){
                switch (s.charAt(j)){
                    case 's': level[i][j] = LevelItem.STONE; break;
                    case '#': level[i][j] = LevelItem.WALL; break;
                    default:  level[i][j] = LevelItem.EMPTY; break;
                }
            }
            for (int j = s.length(); j < cols; j++){
                level[i][j] = LevelItem.EMPTY;
            }
        }
        
        dropApple();
    }

    public GameLevel(GameLevel gl) {
        gameID = gl.gameID;
        rows = gl.rows;
        cols = gl.cols;
        
        apple = gl.apple;
        level = new LevelItem[rows][cols];
        seconds = gl.seconds;
        timer = gl.timer;
        currentDirection = gl.currentDirection;
        player = (LinkedList<Position>)gl.player.clone();
        bufferedDirection = gl.bufferedDirection;
        
        TimerTask rollSeconds = new TimerTask(){
            @Override
            public void run()
            {
                seconds++;
            }
        };
        timer.schedule(rollSeconds, 0, 1000);
        
        for (int i = 0; i < rows; i++){
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
    }

    public boolean isGameEnded(){
        return player.size() == cols*rows || gameFinished;
    }
    
    public boolean isValidPosition(Position p){
        return (p.x >= 0 && p.y >= 0 && p.x < cols && p.y < rows);
    }
    
    public boolean isFree(Position p){
        if (!isValidPosition(p)) return false;
        LevelItem li = level[p.y][p.x];
        return (li == LevelItem.EMPTY || li == LevelItem.APPLE) && !player.contains(p);
    }
    
    public void dropApple(){
        Random random = new Random();
        if(level[apple.y][apple.x].equals(LevelItem.APPLE))
            level[apple.y][apple.x] = LevelItem.EMPTY;
        Position prevApple = new Position(apple);
        apple = new Position(random.nextInt(1,cols), random.nextInt(1,rows));
        while(player.contains(apple) || prevApple.equals(apple) || !isFree(apple))
            apple = new Position(random.nextInt(1,cols), random.nextInt(1,rows));
        level[apple.y][apple.x] = LevelItem.APPLE;
    }
    
    public boolean movePlayer(Direction d){
        if (isGameEnded()) return false;
        Position curr = player.getFirst();
        Position next = curr.translate(d);
        
        
        if(isFree(next))
        {
            player.addFirst(next);
            if(!next.equals(apple))
                player.removeLast();
            else{
                dropApple();
                eatenApples++;
            }
            return true;
        }
        gameFinished = true;
        return false;
    }


    public Timer getTimer()
    {
        return timer;
    }
    
    public int getSeconds()
    {
        return seconds;
    }
}
