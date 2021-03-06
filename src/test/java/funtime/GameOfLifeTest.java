package funtime;

import javafx.util.Pair;
import org.junit.Test;

import java.util.HashSet;

import static funtime.Constants.ALIVE_PATTERN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameOfLifeTest {
    static final int SIZE = 100;

    @Test
    public void shouldGetMatrix() {
        // Given
        GameOfLife gameOfLife = new GameOfLife(SIZE);

        // When
        String[][] matrix = gameOfLife.getMatrix();

        // Then
        assertTrue(matrix != null);
    }

    @Test
    public void shouldGetMatrixByInput() {
        // Given
        HashSet<Pair<Integer, Integer>> positionSet = new HashSet<>();
        positionSet.add(new Pair<>(1, 1));
        positionSet.add(new Pair<>(0, 1));
        GameOfLife gameOfLife = new GameOfLife(positionSet, SIZE);

        // When
        String[][] matrix = gameOfLife.getMatrix();

        // Then
        assertTrue(checkCellCount(matrix) == 2);
    }

    @Test
    public void shouldGetInitMatrix() {
        // Given
        int cellCount = 5;
        GameOfLife gameOfLife = new GameOfLife(cellCount, SIZE);

        // When
        String[][] matrix = gameOfLife.getMatrix();
        int count_matrix = checkCellCount(matrix);

        // Then
        assertTrue(count_matrix == cellCount);
    }

    @Test
    public void shouldGetRandomMatrix() {
        // Given
        int cellCount = 5;
        GameOfLife gameOfLife = new GameOfLife(cellCount, SIZE);
        GameOfLife gameOfLife2 = new GameOfLife(cellCount, SIZE);

        // When
        boolean flag = true;
        String[][] matrix = gameOfLife.getMatrix();
        String[][] matrix2 = gameOfLife2.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (!matrix[i][j].equals(matrix2[i][j])) flag = false;
            }
        }

        // Then
        assertFalse(flag);
    }

    @Test
    public void shouldLessTwoDead() {
        // Given
        int cellCount = 2;
        GameOfLife gameOfLife = new GameOfLife(cellCount, SIZE);

        // When
        String[][] matrixNew = gameOfLife.updateMatrix();
        int cellCountNew = checkCellCount(matrixNew);

        // Then
        assertTrue(cellCountNew == 0);
    }

    @Test
    public void shouldEqualsTwoKeep() {
        // Given
        int cellCount = 2;
        GameOfLife gameOfLife = new GameOfLife(cellCount, 2);

        // When
        String[][] matrixNew = gameOfLife.updateMatrix();
        int cellCountNew = checkCellCount(matrixNew);

        // Then
        assertTrue(cellCountNew == 0);
    }

    @Test
    public void shouldEqualsThreeAlive() {
        // Given
        int cellCount = 3;
        GameOfLife gameOfLife = new GameOfLife(cellCount, 2);

        // When
        String[][] matrixNew = gameOfLife.updateMatrix();
        int cellCountNew = checkCellCount(matrixNew);

        // Then
        assertTrue(cellCountNew == 4);
    }

    @Test
    public void shouldGreatThanThreeDead() {
        // Given
        int cellCount = 9;
        GameOfLife gameOfLife = new GameOfLife(cellCount, 3);

        // When
        String[][] matrixNew = gameOfLife.updateMatrix();
        int cellCountNew = checkCellCount(matrixNew);

        // Then
        assertTrue(cellCountNew == 4);
    }

    private int checkCellCount(String[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].equals(ALIVE_PATTERN)) {
                    count++;
                }
            }
        }
        return count;
    }
}