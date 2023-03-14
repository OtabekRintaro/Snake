package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import model.GameID;

public class Database {
    private final String tableName = "highscore";
    private final Connection conn;
    private final HashMap<GameID, Integer> highScores;
    private HashMap<GameID, String> names;
    
    public Database(){
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost/snake?"
                    + "serverTimezone=UTC&user=root&password=OTannoyingshinji21");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("No connection");
        }
        this.conn = c;
        highScores = new HashMap<>();
        names = new HashMap<>();
        loadHighScores();
    }
    
    public boolean storeHighScore(GameID id, int newScore, String name){
        return mergeHighScores(id, newScore, newScore > 0, name);
    }
    
    public ArrayList<HighScore> getHighScores(){
        ArrayList<HighScore> scores = new ArrayList<>();
        for (GameID id : highScores.keySet()){
            HighScore h = new HighScore(id, highScores.get(id), names.get(id));
            scores.add(h);
            System.out.println(h);
        }
        return scores;
    }
    
    private void loadHighScores(){
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()){
                String diff = rs.getString("Difficulty");
                int level = rs.getInt("GameLevel");
                int eatenApples = rs.getInt("EatenApples");
                String name = rs.getString("Nickname");
                GameID id = new GameID(diff, level);
                mergeHighScores(id, eatenApples, false, name);
            }
        } catch (Exception e){ System.out.println("loadHighScores error: " + e.getMessage());}
    }
    
    private boolean mergeHighScores(GameID id, int score, boolean store, String name){
        System.out.println("Merge: " + id.difficulty + "-" + id.level + ":" + score + "(" + store + ") " + name);
        boolean doUpdate = true;
        if (highScores.containsKey(id)){
            int oldScore = highScores.get(id);
            doUpdate = ((score > oldScore && score != 0) || oldScore == 0);
            doUpdate = doUpdate || (score == oldScore && names.get(id) == null);
        }
        if (doUpdate){
            highScores.remove(id);
            highScores.put(id, score);
            names.put(id, name);
            if (store) return storeToDatabase(id, score, name) > 0;
            return true;
        }
        return false;
    }
    
    private int storeToDatabase(GameID id, int score, String name){
        try (Statement stmt = conn.createStatement()){
            String s = "INSERT INTO " + tableName + 
                    " (Difficulty, GameLevel, EatenApples, Nickname) " + 
                    "VALUES('" + id.difficulty + "'," + id.level + 
                    "," + score + ",'" + name + "'" + 
                    ") ON DUPLICATE KEY UPDATE EatenApples=" + score + ", Nickname='" + name + "'";
            return stmt.executeUpdate(s);
        } catch (Exception e){
            System.out.println("storeToDatabase error");
        }
        return 0;
    }
    
}
