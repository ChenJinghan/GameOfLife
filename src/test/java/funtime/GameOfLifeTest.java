package funtime;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameOfLifeTest {
    @Test
    public void shouldSayHello() {
        // given
        GameOfLife gameOfLife = new GameOfLife();

        // when
        String string = gameOfLife.sayHello();

        // then
        assertTrue("Hello World".equals(string));
    }
}