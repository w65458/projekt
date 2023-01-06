package com.example.tictactoe;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameProcessorTest {

    private GameProcessor gameProcessor;

    @BeforeEach
    public void setUp() {
        gameProcessor = new GameProcessor(3);
    }

    @Test
    public void testMarkSymbol() {
        gameProcessor.markSymbol(1, 1);
        assertEquals("X", gameProcessor.getGameField().getFieldValue(1, 1));
        assertEquals("O", gameProcessor.getTurn());

        gameProcessor.markSymbol(0, 0);
        assertEquals("O", gameProcessor.getGameField().getFieldValue(0, 0));
        assertEquals("X", gameProcessor.getTurn());

        gameProcessor.markSymbol(0, 0);
        assertEquals("O", gameProcessor.getGameField().getFieldValue(0, 0));
        assertEquals("X", gameProcessor.getTurn());
    }

    @Test
    public void testIsGameFinished() {
        assertFalse(gameProcessor.isGameFinished());

        gameProcessor.markSymbol(0, 0);
        gameProcessor.swapTurn();
        assertFalse(gameProcessor.isGameFinished());

        gameProcessor.markSymbol(0, 1);
        gameProcessor.swapTurn();
        assertFalse(gameProcessor.isGameFinished());

        gameProcessor.markSymbol(0, 2);
        gameProcessor.swapTurn();
        assertTrue(gameProcessor.isGameFinished());
        assertEquals("X", gameProcessor.getWinner());
    }

    @Test
    public void testIsBoardFilled() {
        assertFalse(gameProcessor.isBoardFilled());

        gameProcessor.markSymbol(0, 0);
        gameProcessor.markSymbol(0, 1);
        gameProcessor.markSymbol(0, 2);
        gameProcessor.markSymbol(1, 0);
        gameProcessor.markSymbol(1, 1);
        gameProcessor.markSymbol(1, 2);
        gameProcessor.markSymbol(2, 0);
        gameProcessor.markSymbol(2, 1);
        gameProcessor.markSymbol(2, 2);

        assertTrue(gameProcessor.isBoardFilled());
    }
}
