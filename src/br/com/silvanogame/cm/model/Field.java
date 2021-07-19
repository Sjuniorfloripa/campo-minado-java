package br.com.silvanogame.cm.model;

import br.com.silvanogame.cm.model.exception.ExplosionException;

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

    public void alternateMarking() {
        if (!opened) {
            marked = !marked;
        }
    }

    public boolean open() {
        if (!opened && !marked) {
            opened = true;

            if (mined) {
                throw new ExplosionException();
            }
            if (safeNeighborhood()) {
                neighbors.forEach(v -> v.open());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(v -> v.mined);
    }

    public void mine(){
        mined = true;
    }

    public boolean isMarked() {
        return marked;
    }

    void setOpened(boolean opened){
        this.opened = opened;
    }

    public boolean isMined() {
        return mined;
    }

    public boolean isOpened() {
        return opened;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

     boolean missionAccomplished() {
        boolean unveiling = !mined && opened;
        boolean protectedField = mined && marked;

        return unveiling || protectedField;
    }

    public long minesInTheNeighborhood() {
        return neighbors.stream().filter(v -> v.mined).count();
    }

    public void restart() {
        opened = false;
        mined = false;
        marked = false;
    }

    public String toString() {
        if (marked) {
            return "x";
        } else if (opened && mined) {
            return "*";
        } else if (opened && minesInTheNeighborhood() > 0) {
            return Long.toString(minesInTheNeighborhood());
        } else if (opened) {
            return " ";
        } else {
            return "?";
        }
    }
}
