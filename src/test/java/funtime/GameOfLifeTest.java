package funtime;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameOfLifeTest {
    @Test
    public void shouldGetMatrix() {
        // Given
        GameOfLife gameOfLife = new GameOfLife();

        // When
        String[][] matrix = gameOfLife.getMatrix();

        // Then
        assertTrue(matrix != null);
    }

    @Test
    public void shouldGetInitMatrix() {
        // Given
        int size = 5;
        GameOfLife gameOfLife = new GameOfLife(size);

        // When
        String[][] matrix = gameOfLife.getMatrix();
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j].equals("*")) {
                    count++;
                }

            }
        }

        // Then
        assertTrue(count == 5);
    }
}