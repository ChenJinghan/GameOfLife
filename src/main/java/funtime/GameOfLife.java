package funtime;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Random;

public class GameOfLife {
    private Random random = new Random();
    private int size;
    private String[][] matrixA;

    public GameOfLife(int size) {
        this.size = size;
        matrixA = new String[size][size];
    }

    public String[][] getMatrix() {
        return matrixA;
    }

    public GameOfLife(int cellCount, int size) {
        this.size = size;
        matrixA = new String[size][size];
        if (cellCount > matrixA.length)
            cellCount = matrixA.length;
        HashSet<Pair<Integer, Integer>> posSet = generateRandomCells(cellCount);
        cleanMatrix();

        for (Pair position : posSet) {
            int row = (int) position.getKey();
            int column = (int) position.getValue();
            matrixA[row][column] = "*";
        }
    }

    private HashSet<Pair<Integer, Integer>> generateRandomCells(int cellCount) {
        HashSet<Pair<Integer, Integer>> posSet = new HashSet<>();
        while (true) {
            int row = random.nextInt(size);
            int column = random.nextInt(size);
            Pair<Integer, Integer> position = new Pair<>(row, column);
            posSet.add(position);
            if (posSet.size() == cellCount) {
                break;
            }
        }
        return posSet;
    }

    private void cleanMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                matrixA[i][j] = " ";
            }
        }
    }
}
