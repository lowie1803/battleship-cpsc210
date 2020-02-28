package model;

import jdk.internal.util.xml.impl.Input;

import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {
    // TODO: implement methods for HumanPlayer and put into app.
    Scanner input = new Scanner(System.in);

    @Override
    public boolean inGameMenu(Player opponent, int index) {
        return true;
    }

    @Override
    public void makeAnAttack(Player opponent) {
        System.out.println("Choose one grid to attack!\n(X first, Y follows)");
        int x = input.nextInt();
        int y = input.nextInt();
        int result = attack(opponent, x, y);

        while (result == -1) {
            System.out.println("Illegal move! Please redo!");
            System.out.println("(Either your choice are out of bounds or you have made this move before)");
            x = input.nextInt();
            y = input.nextInt();
            result = attack(opponent, x, y);
        }

        if (result == 0) {
            System.out.println("MISSED!\n");
        } else {
            System.out.println("HIT! Earned " + result + " points!\n");
        }
    }

    @Override
    public void generateAllShips(List<Integer> sizes) {

    }
}
