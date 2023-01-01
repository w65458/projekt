package com.example.tictactoe;

import org.apache.commons.lang3.StringUtils;

public class GameField {

    private final Field[][] gameField;

    public GameField(int size) {
        this.gameField = new Field[size][size];
        generateGameField();
    }

    private void generateGameField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = new Field(i, j, StringUtils.EMPTY);
            }
        }
    }

    public Field getField(int rowIndex, int columnIndex) {
        return gameField[rowIndex][columnIndex];
    }

    public String getFieldValue(int rowIndex, int columnIndex) {
        return gameField[rowIndex][columnIndex].getValue();
    }

    public void markField(int rowIndex, int columnIndex, String symbol) {
        Field field = this.gameField[rowIndex][columnIndex];
        field.setValue(symbol);
    }

    public boolean isFieldMarked(int rowIndex, int columnIndex) {
        return !this.gameField[rowIndex][columnIndex].getValue().isBlank();
    }

}