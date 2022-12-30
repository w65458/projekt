package com.example.tictactoe;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameProcessor {

    public static final String X = "X";
    public static final String O = "O";

    private final int boardSize;
    private final GameField gameField;
    private final List<Field> winningCombo;
    private int xWinningsCounter = 0;
    private int oWinningsCounter = 0;
    private int drawsCounter = 0;
    private String turn;
    private String winner;

    public GameProcessor(final int boardSize) {
        this.boardSize = boardSize;
        this.gameField = new GameField(boardSize);
        this.winningCombo = new ArrayList<>();
        this.turn = X;
    }

    public void markSymbol(int firstCoordinate, int secondCoordinate) {
        if (isBeyondBoard(firstCoordinate, secondCoordinate) || gameField.isFieldMarked(firstCoordinate, secondCoordinate)) {
            return;
        }
        gameField.markField(firstCoordinate, secondCoordinate, turn);
        swapTurn();
    }

    private boolean isBeyondBoard(int firstCoordinate, int secondCoordinate) {
        return firstCoordinate > boardSize || firstCoordinate < 0 || secondCoordinate > boardSize || secondCoordinate < 0;
    }

    public void swapTurn() {
        turn = turn.equals(X) ? O : X;
    }

    public boolean isGameFinished() {
        if (isWinnerInRows() || isWinnerInColumns() || isWinnerInFirstDiagonal() || isWinnerInSecondDiagonal()) {
            return true;
        }
        return isBoardFilled();
    }

    private boolean isWinnerInRows() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameField.getFieldValue(i, j).equals(X)) {
                    winningCombo.add(new Field(i, j, X));
                    xInRowCounter++;
                } else if (gameField.getFieldValue(i, j).equals(O)) {
                    winningCombo.add(new Field(i, j, O));
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == boardSize) {
                winner = X;
                xWinningsCounter++;
                return true;
            }
            if (oInRowCounter == boardSize) {
                winner = O;
                oWinningsCounter++;
                return true;
            }
            winningCombo.clear();
            xInRowCounter = 0;
            oInRowCounter = 0;
        }

        return false;
    }

    public boolean isWinnerInColumns() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int j = 0; j < boardSize; j++) {
            for (int i = 0; i < boardSize; i++) {
                if (gameField.getFieldValue(i, j).equals(X)) {
                    winningCombo.add(new Field(i, j, X));
                    xInRowCounter++;
                } else if (gameField.getFieldValue(i, j).equals(O)) {
                    winningCombo.add(new Field(i, j, O));
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == boardSize) {
                winner = X;
                xWinningsCounter++;
                return true;
            }
            if (oInRowCounter == boardSize) {
                winner = X;
                oWinningsCounter++;
                return true;
            }
            winningCombo.clear();
            xInRowCounter = 0;
            oInRowCounter = 0;
        }

        return false;
    }

    public boolean isWinnerInFirstDiagonal() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (j == i && gameField.getFieldValue(i, j).equals(X)) {
                    winningCombo.add(new Field(i, j, X));
                    xInRowCounter++;
                } else if (j == i && gameField.getFieldValue(i, j).equals(O)) {
                    winningCombo.add(new Field(i, j, O));
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == boardSize) {
            winner = X;
            xWinningsCounter++;
            return true;
        }
        if (oInRowCounter == boardSize) {
            winner = O;
            oWinningsCounter++;
            return true;
        }
        winningCombo.clear();
        return false;
    }

    public boolean isWinnerInSecondDiagonal() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (j == -i + boardSize - 1 && gameField.getFieldValue(i, j).equals(X)) {
                    winningCombo.add(new Field(i, j, X));
                    xInRowCounter++;
                } else if (j == -i + boardSize - 1 && gameField.getFieldValue(i, j).equals(X)) {
                    winningCombo.add(new Field(i, j, O));
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == boardSize) {
            winner = X;
            xWinningsCounter++;
            return true;
        }
        if (oInRowCounter == boardSize) {
            winner = O;
            oWinningsCounter++;
            return true;
        }
        winningCombo.clear();
        return false;
    }

    public boolean isBoardFilled() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameField.getFieldValue(i, j).isBlank()) {
                    return false;
                }
            }
        }
        winner = "Draw";
        drawsCounter++;
        return true;
    }

}
