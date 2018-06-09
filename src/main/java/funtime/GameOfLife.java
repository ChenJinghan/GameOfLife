package funtime;

import javafx.util.Pair;

import java.util.*;

public class GameOfLife {
    private Random random = new Random();
    private int size;
    private String[][] matrixA;

    public GameOfLife(int size) {
        this.size = size;
        matrixA = new String[size][size];
    }

    public GameOfLife(int cellCount, int size) {
        this.size = size;
        matrixA = new String[size][size];
        int maxCells = matrixA.length * matrixA[0].length;
        if (cellCount > maxCells)
            cellCount = maxCells;
        HashSet<Pair<Integer, Integer>> posSet = generateRandomCells(cellCount);
        cleanMatrix(matrixA);

        setAlivePosition(posSet);
    }

    public GameOfLife(HashSet<Pair<Integer, Integer>> aliveCellsPositions, int size) {
        this.size = size;
        matrixA = new String[size][size];
        cleanMatrix(matrixA);
        setAlivePosition(aliveCellsPositions);
    }

    public String[][] getMatrix() {
        return matrixA;
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
                } else {
                    resultMatrix[i][j] = " ";
                }
            }
        }
        this.matrixA = resultMatrix;
        return resultMatrix;
    }

    public void output(double time) throws InterruptedException {
        String line = "-----------------------------------";
        while (true) {
            System.out.println(line);
            for (int i = 0; i < size; i++) {
                System.out.print("|");
                for (int j = 0; j < size; j++) {
                    System.out.print(matrixA[i][j] + "|");
                }
                System.out.println();
            }
            updateMatrix();
            Thread.sleep((long) time * 1000);
        }
    }

    private void setAlivePosition(HashSet<Pair<Integer, Integer>> posSet) {
        for (Pair position : posSet) {
            int row = (int) position.getKey();
            int column = (int) position.getValue();
            matrixA[row][column] = "*";
        }
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

    private static boolean isInputValid(List<Integer> list, int size) {
        if (list.size() / 2 > size * size) {
            System.out.println("输入坐标个数超出矩阵范围");
            return false;
        }
        if (list.size() % 2 != 0) {
            System.out.println("输入坐标个数错误");
            return false;
        }
        for (int m : list) {
            if (m >= size) {
                System.out.println("输入值大小超出矩阵范围");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输迭代速度（秒）：");
        double speed = scanner.nextDouble();

        System.out.println("请输入矩阵长度：");
        int size = scanner.nextInt();

        System.out.println("请输入细胞坐标，输入任意字母结束：");
        while (scanner.hasNextInt()) {
            int m = scanner.nextInt();
            list.add(m);
        }

        if (!isInputValid(list, size))
            return;

        int i = 0;
        HashSet<Pair<Integer, Integer>> hashSet = new HashSet<>();
        while (i < list.size()) {
            Pair<Integer, Integer> pair = new Pair<>(list.get(i), list.get(i + 1));
            hashSet.add(pair);
            i += 2;
        }

        GameOfLife gameOfLife = new GameOfLife(hashSet, size);
        gameOfLife.output(speed);

    }
}
