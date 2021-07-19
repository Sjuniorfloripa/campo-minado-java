package br.com.silvanogame.cm.model;

import br.com.silvanogame.cm.model.exception.ExplosionException;
import br.com.silvanogame.cm.model.exception.QuitException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole {

    private Board board;
    private Scanner scanner = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;

        runGame();
    }

    private void runGame() {
        try {
            boolean keepPlaying = true;

            while (keepPlaying) {
                gameCycle();

                System.out.println("Tentar novamente? (S/n) ");
                String choice = scanner.nextLine();
                if ("n".equalsIgnoreCase(choice)) {
                    keepPlaying = false;
                } else {
                    board.restart();
                }
            }
        } catch (QuitException e) {
            System.out.println("Tchau, até a próxima!");
        } finally {
            scanner.close();
        }
    }

    private void gameCycle() {
        try {
            while (!board.missionAccomplished()) {
                System.out.println(board);
                String typed = captureTypedValue("digite (x, y): ");
                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                typed = captureTypedValue("1 - Abrir ou 2 - (Des)Marcar");

                if ("1".equals(typed)){
                    board.open(xy.next(), xy.next());
                } else if ("2".equals(typed)){
                    board.mark(xy.next(), xy.next());
                }
            }
            System.out.println(board);
            System.out.println("Você venceu!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Você explodiu!");
        }
    }

    private String captureTypedValue(String text) {
        System.out.println(text);
        String typed = scanner.nextLine();
        if ("sair".equalsIgnoreCase(typed)) {
            throw new QuitException();
        }
        return typed;
    }
}
