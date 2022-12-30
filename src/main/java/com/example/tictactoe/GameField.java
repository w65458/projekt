package com.example.tictactoe;

public class GameField {

    private final Field[][] gameField;

    public GameField(int size) {
        this.gameField = new Field[size][size];
    }

    public String getFieldValue(int rowIndex, int columnIndex) {
        Field field = gameField[rowIndex][columnIndex];
        if (field == null) {
            return " ";
        }
        return field.getValue();
    }

    public void markField(int rowIndex, int columnIndex, String symbol) {
        this.gameField[rowIndex][columnIndex] = new Field(rowIndex, columnIndex, symbol);
    }

    public boolean isFieldMarked(int rowIndex, int columnIndex) {
        return !this.gameField[rowIndex][columnIndex].getValue().equals(" ");
    }

}