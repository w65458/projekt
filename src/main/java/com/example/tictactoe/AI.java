package com.example.tictactoe;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.tictactoe.TicTacToeApp.BOARD_SIZE;

@Getter
@Setter
public class AI implements Player {

    public static final int MAX_DEPTH = 9;
    private final Level level;
    private final String aiSymbol;
    private final String opponentSymbol;
    private final Random random;

    public AI(Level level, String aiSymbol) {
        this.level = level;
        this.aiSymbol = aiSymbol;
        this.opponentSymbol = aiSymbol.equals("X") ? "O" : "X";
        this.random = new Random();
    }

    public Field move(GameField gameField) {
        List<Field> availableFields = getAvailableFields(gameField);

        switch (level) {
            case EASY:
                return chooseNextMoveLevelEasy(availableFields);
            case MEDIUM:
                Field aiMediumLevelMove = chooseNextMoveLevelMedium(gameField, aiSymbol);
                Field opponentMediumLevelMove = chooseNextMoveLevelMedium(gameField, opponentSymbol);
                if (aiMediumLevelMove == null && opponentMediumLevelMove == null) {
                    return chooseNextMoveLevelEasy(availableFields);
                }
                if (aiMediumLevelMove != null && opponentMediumLevelMove == null) {
                    return aiMediumLevelMove;
                }
                return opponentMediumLevelMove;
            default:
                if (isBoardEmpty(availableFields)) {
                    return chooseNextMoveLevelEasy(availableFields);
                }
                return chooseNextMoveLevelHard(gameField);
        }
    }

    private Field chooseNextMoveLevelEasy(List<Field> availableFields) {
        return availableFields.get(random.nextInt(availableFields.size()));
    }

    public Field chooseNextMoveLevelMedium(GameField gameField, String symbol) {
        Field possibleMove = checkForPossibleMoveInRows(gameField, symbol);
        if (possibleMove != null)
            return possibleMove;

        possibleMove = checkForPossibleMoveInColumns(gameField, symbol);
        if (possibleMove != null)
            return possibleMove;

        possibleMove = checkForPossibleMoveInFirstDiagonal(gameField, symbol);
        if (possibleMove != null)
            return possibleMove;

        possibleMove = checkForPossibleMoveInSecondDiagonal(gameField, symbol);

        return possibleMove;
    }

    private Field checkForPossibleMoveInRows(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameField.getFieldValue(i, j).equals(symbol)) {
                    consecutiveSymbolCounter++;
                }
                if (consecutiveSymbolCounter == 2 && j == 1 && gameField.getFieldValue(i, 2).equals(StringUtils.EMPTY)) {
                    return new Field(i, 2, symbol);
                }
                if (consecutiveSymbolCounter == 2 && j == 2 && gameField.getFieldValue(i, 1).equals(symbol) && gameField.getFieldValue(i, 0).equals(StringUtils.EMPTY)) {
                    return new Field(i, 0, symbol);
                }
                if (consecutiveSymbolCounter == 2 && j == 2 && gameField.getFieldValue(i, 1).equals(StringUtils.EMPTY)) {
                    return new Field(i, 1, symbol);
                }
            }
            consecutiveSymbolCounter = 0;
        }
        return null;
    }

    private Field checkForPossibleMoveInColumns(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameField.getFieldValue(j, i).equals(symbol)) {
                    consecutiveSymbolCounter++;
                }
                if (consecutiveSymbolCounter == 2 && j == 1 && gameField.getFieldValue(2, i).equals(StringUtils.EMPTY)) {
                    return new Field(2, i, symbol);
                }
                if (consecutiveSymbolCounter == 2 && j == 2 && gameField.getFieldValue(1, i).equals(symbol) && gameField.getFieldValue(0, i).equals(StringUtils.EMPTY)) {
                    return new Field(0, i, symbol);
                }
                if (consecutiveSymbolCounter == 2 && j == 2 && gameField.getFieldValue(1, i).equals(StringUtils.EMPTY)) {
                    return new Field(1, i, symbol);
                }
            }
            consecutiveSymbolCounter = 0;
        }
        return null;
    }

    private Field checkForPossibleMoveInFirstDiagonal(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameField.getFieldValue(i, i).equals(symbol)) {
                consecutiveSymbolCounter++;
            }
            if (consecutiveSymbolCounter == 2 && i == 1 && gameField.getFieldValue(2, 2).equals(StringUtils.EMPTY)) {
                return new Field(2, 2, symbol);
            }
            if (consecutiveSymbolCounter == 2 && i == 2 && gameField.getFieldValue(1, 1).equals(symbol) && gameField.getFieldValue(0, 0).equals(StringUtils.EMPTY)) {
                return new Field(0, 0, symbol);
            }
            if (consecutiveSymbolCounter == 2 && i == 2 && gameField.getFieldValue(1, 1).equals(StringUtils.EMPTY)) {
                return new Field(1, 1, symbol);
            }
        }
        return null;
    }

    private Field checkForPossibleMoveInSecondDiagonal(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameField.getFieldValue(i, BOARD_SIZE - 1 - i).equals(symbol)) {
                consecutiveSymbolCounter++;
            }
            if (consecutiveSymbolCounter == 2 && i == 1 && gameField.getFieldValue(2, 0).equals(StringUtils.EMPTY)) {
                return new Field(2, 0, symbol);
            }
            if (consecutiveSymbolCounter == 2 && i == 2 && gameField.getFieldValue(1, 1).equals(symbol) && gameField.getFieldValue(0, 2).equals(StringUtils.EMPTY)) {
                return new Field(0, 2, symbol);
            }
            if (consecutiveSymbolCounter == 2 && i == 2 && gameField.getFieldValue(1, 1).equals(StringUtils.EMPTY)) {
                return new Field(1, 1, symbol);
            }
        }
        return null;
    }

    public Field chooseNextMoveLevelHard(GameField gameField) {
        Field bestMove = null;

        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameField.getFieldValue(i, j).isBlank()) {
                    gameField.markField(i, j, aiSymbol);
                    int score = miniMax(gameField, false, 0);
                    gameField.markField(i, j, StringUtils.EMPTY);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Field(i, j, aiSymbol);
                    }
                }
            }
        }

        return bestMove;
    }

    public int miniMax(GameField gameField, boolean isMaximizing, int depth) {
        if (checkIfWinning(gameField, aiSymbol)) {
            return 10;
        } else if (checkIfWinning(gameField, opponentSymbol)) {
            return -10;
        } else if (getAvailableFields(gameField).size() == 0) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Field field : getAvailableFields(gameField)) {
                gameField.markField(field.getRowIndex(), field.getColumnIndex(), aiSymbol);
                int score = miniMax(gameField, false, depth + 1);
                gameField.markField(field.getRowIndex(), field.getColumnIndex(), StringUtils.EMPTY);
                bestScore = Math.max(score, bestScore);
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Field field : getAvailableFields(gameField)) {
                gameField.markField(field.getRowIndex(), field.getColumnIndex(), opponentSymbol);
                int score = miniMax(gameField, true, depth - 1);
                gameField.markField(field.getRowIndex(), field.getColumnIndex(), StringUtils.EMPTY);
                bestScore = Math.min(score, bestScore);
            }
            return bestScore;
        }
    }

    public boolean checkIfWinning(GameField gameField, String symbol) {
        return isWinningInRows(gameField, symbol) || isWinningInColumns(gameField, symbol)
                || isWinningInFirstDiagonal(gameField, symbol) || isWinningInSecondDiagonal(gameField, symbol);
    }

    private boolean isWinningInRows(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameField.getFieldValue(i, j).equals(symbol)) {
                    consecutiveSymbolCounter++;
                }
            }
            if (consecutiveSymbolCounter == BOARD_SIZE) {
                return true;
            }
            consecutiveSymbolCounter = 0;
        }
        return false;
    }

    private boolean isWinningInColumns(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameField.getFieldValue(j, i).equals(symbol)) {
                    consecutiveSymbolCounter++;
                }
            }
            if (consecutiveSymbolCounter == BOARD_SIZE) {
                return true;
            }
            consecutiveSymbolCounter = 0;
        }
        return false;
    }

    private boolean isWinningInFirstDiagonal(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameField.getFieldValue(i, i).equals(symbol)) {
                consecutiveSymbolCounter++;
            }
        }
        return consecutiveSymbolCounter == BOARD_SIZE;
    }

    private boolean isWinningInSecondDiagonal(GameField gameField, String symbol) {
        int consecutiveSymbolCounter = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameField.getFieldValue(i, BOARD_SIZE - 1 - i).equals(symbol)) {
                consecutiveSymbolCounter++;
            }
        }
        return consecutiveSymbolCounter == BOARD_SIZE;
    }

    public List<Field> getAvailableFields(GameField gameField) {
        List<Field> availableFields = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Field field = gameField.getField(i, j);
                if (field.getValue().isBlank()) {
                    availableFields.add(field);
                }
            }
        }
        return availableFields;
    }

    private boolean isBoardEmpty(List<Field> availableFields) {
        return availableFields.size() == BOARD_SIZE * BOARD_SIZE;
    }

}
