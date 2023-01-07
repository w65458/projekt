package com.example.tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AITest {
    private static final int SIZE = 3;

    private final GameField gameField = new GameField(SIZE);
    private final AI easyAI = new AI(Level.EASY, "X");
    private final AI mediumAI = new AI(Level.MEDIUM, "X");
    private final AI hardAI = new AI(Level.HARD, "X");

    @Test
    void testMoveEasy() {
        Field move = easyAI.move(gameField);
        assertTrue(move.getRowIndex() >= 0 && move.getRowIndex() < SIZE);
        assertTrue(move.getColumnIndex() >= 0 && move.getColumnIndex() < SIZE);
    }


    @Test
    void testMoveMedium() {
        gameField.markField(0, 0, "O");
        gameField.markField(0, 1, "O");

        Field move = mediumAI.move(gameField);

        assertEquals(0, move.getRowIndex());
        assertEquals(2, move.getColumnIndex());
    }

    @Test
    void testMoveHardWinningMove() {
        gameField.markField(0, 0, "X");
        gameField.markField(0, 1, "X");

        Field move = hardAI.move(gameField);

        assertEquals(0, move.getRowIndex());
        assertEquals(2, move.getColumnIndex());
    }

    @Test
    void testMoveHardBlockOpponent() {
        gameField.markField(0, 0, "O");
        gameField.markField(0, 1, "O");

        Field move = hardAI.move(gameField);

        assertEquals(0, move.getRowIndex());
        assertEquals(2, move.getColumnIndex());
    }
}