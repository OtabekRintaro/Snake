package view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import persistence.HighScore;

public class HighScoreTableModel extends AbstractTableModel{
    private final ArrayList<HighScore> highScores;
    private final String[] colName = new String[]{ "Difficulty", "Level", "Eaten apples", "Nickname" };
    
    public HighScoreTableModel(ArrayList<HighScore> highScores){
        this.highScores = highScores;
    }

    @Override
    public int getRowCount() { return highScores.size(); }

    @Override
    public int getColumnCount() { return 4; }

    @Override
    public Object getValueAt(int r, int c) {
        HighScore h = highScores.get(r);
        if      (c == 0) return h.difficulty;
        else if (c == 1) return h.level;
        else if (c == 3) return h.nickname;
        return (h.eatenApples == 0) ? "N/A" : h.eatenApples;
    }

    @Override
    public String getColumnName(int i) { return colName[i]; }    
    
}
