package funtime;

import java.util.Random;

public class GameOfLife {
    Random random =  new Random();
    int size = random.nextInt(100);
    String[][] matrixA = new String[size][size];

    public String[][] getMatrix() {
        return matrixA;
    }
}
