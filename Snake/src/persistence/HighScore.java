package persistence;

import java.util.Objects;
import model.GameID;

public class HighScore {
    public final String difficulty;
    public final int level;
    public final int eatenApples;
    public String nickname = new String();
    
    public HighScore(GameID gameID, int eatenApples){
        this.difficulty = gameID.difficulty;
        this.level = gameID.level;
        this.eatenApples = eatenApples;
    }
    
    public HighScore(GameID gameID, int eatenApples, String nickname){
        this.difficulty = gameID.difficulty;
        this.level = gameID.level;
        this.eatenApples = eatenApples;
        this.nickname = nickname;
    }
    
    public HighScore(String difficulty, int level, int eatenApples, String nickname){
        this.difficulty = difficulty;
        this.level = level;
        this.eatenApples = eatenApples;
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.difficulty);
        hash = 89 * hash + this.level;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HighScore other = (HighScore) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.difficulty, other.difficulty)) {
            return false;
        }
        return true;
    }   

    @Override
    public String toString() {
        return difficulty + "-" + level + ": " + eatenApples + ", nickname: " + nickname;
    }
    
    
}
