package funtime;

import java.util.Random;

public class GameOfLife {
    Random random = new Random();
    int size = random.nextInt(100);
    String[][] matrixA = new String[size][size];

    public GameOfLife(int cellCount) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j < cellCount) matrixA[i][j] = "*";
                else matrixA[i][j] = " ";
            }
        }
    }

    public GameOfLife() {

    }

    public String[][] getMatrix() {
        return matrixA;
    }
}
