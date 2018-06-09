package funtime;

import javafx.util.Pair;

import java.util.*;

import static funtime.Constants.*;

public class GameOfLife {
    private static Random random = new Random();
    private int size;
    private String[][] matrix;

    public GameOfLife(int size) {
        this.size = size;
        matrix = new String[size][size];
    }

    public GameOfLife(int cellCount, int size) {
        this.size = size;
        matrix = new String[size][size];
        int maxCells = matrix.length * matrix[0].length;
        if (cellCount > maxCells)
            cellCount = maxCells;
        HashSet<Pair<Integer, Integer>> posSet = generateRandomCells(cellCount);
        cleanMatrix(matrix);

        setAlivePosition(posSet);
    }

    public GameOfLife(HashSet<Pair<Integer, Integer>> aliveCellsPositions, int size) {
        this.size = size;
        matrix = new String[size][size];
        cleanMatrix(matrix);
        setAlivePosition(aliveCellsPositions);
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public String[][] updateMatrix() {
        String[][] resultMatrix = new String[size][size];
        cleanMatrix(resultMatrix);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int aliveCellCount = getAroundAliveCount(i, j);
                setMatrixBasedOnRules(resultMatrix, i, j, aliveCellCount);
            }
        }
        this.matrix = resultMatrix;
        return resultMatrix;
    }

    public void output(double time) throws InterruptedException {
        String line = "-----------------------------------";
        while (true) {
            System.out.println(line);
            for (int i = 0; i < size; i++) {
                System.out.print("|");
                for (int j = 0; j < size; j++) {
                    System.out.print(matrix[i][j] + "|");
                }
                System.out.println();
            }
            updateMatrix();
            Thread.sleep((long) time * MILLIS_PER_SECOND);
        }
    }

    private void setMatrixBasedOnRules(String[][] resultMatrix, int i, int j, int aliveCellCount) {
        if (aliveCellCount < 2) {
            resultMatrix[i][j] = DEAD_PATTERN;
        } else if (aliveCellCount == 2) {
            resultMatrix[i][j] = matrix[i][j];
        } else if (aliveCellCount == 3) {
            resultMatrix[i][j] = ALIVE_PATTERN;
        } else {
            resultMatrix[i][j] = DEAD_PATTERN;
        }
    }

    private void setAlivePosition(HashSet<Pair<Integer, Integer>> posSet) {
        for (Pair position : posSet) {
            int row = (int) position.getKey();
            int column = (int) position.getValue();
            matrix[row][column] = ALIVE_PATTERN;
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
                    if (matrix[iNew][jNew].equals(ALIVE_PATTERN)) {
                        aliveCellCount++;
                    }
                }
            }
        }
        return aliveCellCount;
    }

    private boolean isIndexValid(int iNew, int jNew) {
        return iNew >= 0 && iNew < matrix.length && jNew >= 0 && jNew < matrix[0].length;
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

    private void cleanMatrix(String[][] matrix) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = DEAD_PATTERN;
            }
        }
    }

    private static boolean isInputValid(List<Integer> list, int size) {
        if (list.size() % 2 != 0) {
            System.out.println("输入坐标个数错误");
            return false;
        }
        for (int coordinate : list) {
            if (coordinate >= size) {
                System.out.println("输入值大小超出矩阵范围");
                return false;
            }
        }
        return true;
    }

    private static GameOfLife generateGameOfLife(ArrayList<Integer> list, Scanner scanner, int size, int choice) {
        GameOfLife gameOfLife;
        if (choice == RANDOM) {
            int cellCount = random.nextInt(size);
            gameOfLife = new GameOfLife(cellCount, size);
        } else {
            System.out.println("请输入细胞坐标，输入任意字母结束：");
            while (scanner.hasNextInt()) {
                int coordinate = scanner.nextInt();
                list.add(coordinate);
            }

            if (!isInputValid(list, size))
                return null;

            HashSet<Pair<Integer, Integer>> hashSet = buildAliveCellPositionSet(list);
            gameOfLife = new GameOfLife(hashSet, size);
        }
        return gameOfLife;
    }

    private static HashSet<Pair<Integer, Integer>> buildAliveCellPositionSet(ArrayList<Integer> list) {
        int i = 0;
        HashSet<Pair<Integer, Integer>> hashSet = new HashSet<>();
        while (i < list.size()) {
            Pair<Integer, Integer> pair = new Pair<>(list.get(i), list.get(i + 1));
            hashSet.add(pair);
            i += 2;
        }
        return hashSet;
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输迭代速度（秒）：");
        double speed = scanner.nextDouble();

        System.out.println("请输入矩阵长度：");
        int size = scanner.nextInt();

        System.out.println("请选择细胞坐标初始化方式：（0）随机，（1）自定义");
        int choice = scanner.nextInt();

        GameOfLife gameOfLife;
        gameOfLife = generateGameOfLife(list, scanner, size, choice);

        if (gameOfLife == null) return;

        gameOfLife.output(speed);
    }
}
