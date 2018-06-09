package funtime;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameOfLifeTest {
    @Test
    public void shouldGetMatrix() {
        // Given
        GameOfLife gameOfLife = new GameOfLife();

        // When
        String [][] matrix = gameOfLife.getMatrix();

        // Then
        assertTrue(matrix != null);
    }
}