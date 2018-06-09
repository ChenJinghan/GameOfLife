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
        int maxCells = matrixA.length * matrixA[0].length;
        if (cellCount > maxCells)
            cellCount = maxCells;
        HashSet<Pair<Integer, Integer>> posSet = generateRandomCells(cellCount);
        cleanMatrix(matrixA);

        for (Pair position : posSet) {
            int row = (int) position.getKey();
            int column = (int) position.getValue();
            matrixA[row][column] = "*";
        }
    }

    public String[][] updateMatrix() {
        String[][] resultMatrix = new String[size][size];
        cleanMatrix(resultMatrix);

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                int aliveCellCount = getAroundAliveCount(i, j);

                if (aliveCellCount < 2) {
                    resultMatrix[i][j] = " ";
                } else if (aliveCellCount == 2) {
                    resultMatrix[i][j] = matrixA[i][j];
                } else if (aliveCellCount == 3) {
                    resultMatrix[i][j] = "*";
                }
            }
        }
        return resultMatrix;
    }

    private int getAroundAliveCount(int i, int j) {
        int aliveCellCount = 0;
        for (int iOffSet = -1; iOffSet <= 1; iOffSet++) {
            for (int jOffSet = -1; jOffSet <= 1; jOffSet++) {
                if (iOffSet == 0 && jOffSet == 0) continue;
                int iNew = i + iOffSet;
                int jNew = j + jOffSet;

                if (isIndexValid(iNew, jNew)) {
                    if (matrixA[iNew][jNew].equals("*")) {
                        aliveCellCount++;
                    }
                }
            }
        }
        return aliveCellCount;
    }

    private boolean isIndexValid(int iNew, int jNew) {
        return iNew >= 0 && iNew < matrixA.length && jNew >= 0 && jNew < matrixA[0].length;
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

    private void cleanMatrix(String[][] matrixA) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = " ";
            }
        }
    }
}
