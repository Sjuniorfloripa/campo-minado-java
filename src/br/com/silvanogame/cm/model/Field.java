package br.com.silvanogame.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private boolean mined = false;
    private boolean opened = false;
    private boolean marked = false;
    private final int line;
    private final int column;
    private List<Field> neighbors = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public boolean addNeighbor(Field neigbor) {
        boolean differentLine = line != neigbor.line;
        boolean differentColumn = column != neigbor.column;
        boolean diagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neigbor.line);
        int deltaColumn = Math.abs(column - neigbor.column);
        int detaGeneral = deltaColumn + deltaLine;

        if (detaGeneral == 1 && !diagonal) {
            neighbors.add(neigbor);
            return true;
        } else if (detaGeneral == 2 && diagonal) {
            neighbors.add(neigbor);
            return true;
        } else {
            return false;
        }
    }
}
