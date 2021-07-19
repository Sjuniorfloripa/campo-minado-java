package br.com.silvanogame.cm.model.br.com.silvanogame.cm.application;

import br.com.silvanogame.cm.model.Board;
import br.com.silvanogame.cm.model.BoardConsole;

public class Application {

    public static void main(String[] args) {
        Board board = new Board(6, 6, 6);
        new BoardConsole(board);
    }
}
