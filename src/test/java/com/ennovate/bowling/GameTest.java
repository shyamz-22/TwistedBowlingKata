package com.ennovate.bowling;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void testGutterGame() throws Exception {
        rollMany(10, 0);
        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    public void testAllOnes() throws Exception {
        rollMany(10, 1);
        assertThat(game.score()).isEqualTo(20);

    }

    @Test
    public void testOneSpare() throws Exception {

        game.add(spare());
        game.add(new Frame().roll(3));

        rollMany(8, 0);

        assertThat(game.score()).isEqualTo(16);

    }

    @Test
    public void testOneStrike() throws Exception {
        game.add(strike());
        game.add(new Frame().roll(3).roll(4));

        rollMany(8, 0);

        assertThat(game.score()).isEqualTo(24);
    }

    @Test
    public void testGameWithConsecutiveStrikes() throws Exception {

        game.add(strike());
        game.add(strike());
        game.add(new Frame().roll(3).roll(4));

        rollMany(7, 0);

        assertThat(game.score()).isEqualTo(47);

    }

    @Test
    public void testPerfectGame() throws Exception {
        rollMany(9, 10);
        game.add(new Frame().roll(10).roll(10).roll(10));
        assertThat(game.score()).isEqualTo(300);

    }

    @Test
    public void testFullGame() throws Exception {
        game.add(new Frame().roll(1).roll(2));
        game.add(new Frame().roll(3).roll(4));
        game.add(new Frame().roll(5).roll(5));
        game.add(new Frame().roll(6).roll(4));
        game.add(new Frame().roll(10));
        game.add(new Frame().roll(9).roll(0));
        game.add(new Frame().roll(3).roll(7));
        game.add(new Frame().roll(6).roll(3));
        game.add(new Frame().roll(2).roll(5));
        game.add(new Frame().roll(10).roll(9).roll(1));

        assertThat(game.score()).isEqualTo(126);

    }

    @Test
    public void testFullGameEndingWithSpare() throws Exception {

        rollMany(9, 1);
        game.add(new Frame().roll(5).roll(5).roll(5));

        assertThat(game.score()).isEqualTo(33);

    }

    private Frame strike() {
        return new Frame().roll(10);
    }

    private Frame spare() {
        return new Frame().roll(5).roll(5);
    }

    private void rollMany(int numberOfFrames, int pins) {
        for (int i = 0; i < numberOfFrames; i++) {
            Frame frame = new Frame().roll(pins);
            if(pins != 10)
                frame.roll(pins);
            game.add(frame);
        }
    }
}
