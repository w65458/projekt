package com.example.tictactoe;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameFieldTest {

    private final GameField gameField = new GameField(3);

    @Test
    void testGetField() {
        Field field = gameField.getField(0, 0);
        assertEquals(0, field.getRowIndex());
        assertEquals(0, field.getColumnIndex());
    }

    @Test
    void testGetFieldValue() {
        String value = gameField.getFieldValue(0, 0);
        assertEquals(StringUtils.EMPTY, value);
    }

    @Test
    void testMarkField() {
        gameField.markField(0, 0, "X");
        String value = gameField.getFieldValue(0, 0);
        assertEquals("X", value);
    }

    @Test
    void testIsFieldMarked() {
        gameField.markField(0, 0, "X");
        assertTrue(gameField.isFieldMarked(0, 0));
    }
}