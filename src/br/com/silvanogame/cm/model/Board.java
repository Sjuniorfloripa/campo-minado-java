package br.com.silvanogame.cm.model;

import br.com.silvanogame.cm.model.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateNeighbors();
        drawMines();
    }

    public void open(int line, int column) {
        try {
            fields.parallelStream()
                    .filter(c -> c.getLine() == line)
                    .filter(c -> c.getColumn() == column)
                    .findFirst()
                    .ifPresent(c -> c.open());
        } catch (ExplosionException e) {
            fields.forEach(c -> c.setOpened(true));
            throw e;
        }
    }

    public void mark(int line, int column) {
        fields.parallelStream()
                .filter(c -> c.getLine() == line)
                .filter(c -> c.getColumn() == column)
                .findFirst()
                .ifPresent(c -> c.alternateMarking());
    }

    private void generateFields() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                fields.add(new Field(i, j));
            }
        }
    }

    private void associateNeighbors() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void drawMines() {
        long armedMines = 0;
        Predicate<Field> mined = c -> c.isMined();

        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).mine();
            armedMines = fields.stream().filter(mined).count();
        } while (armedMines < mines);
    }

    public boolean missionAccomplished() {
        return fields.stream().allMatch(c -> c.missionAccomplished());
    }

    public void restart() {
        fields.stream().forEach(c -> c.restart());
        drawMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int c = 0; c < columns; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");

        int i = 0;
        for (int l = 0; l < lines; l++) {
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < columns; c++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
