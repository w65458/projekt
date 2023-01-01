package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.Map;

public class NewGameDialogController {
    private final Map<String, Level> OPPONENT_MAP = Map.of(
            "USER", Level.NONE,
            "AI - LEVEL EASY", Level.EASY,
            "AI - LEVEL MEDIUM", Level.MEDIUM,
            "AI - LEVEL HARD", Level.HARD
    );

    @FXML
    private ToggleGroup opponent;
    @FXML
    private ToggleGroup userSymbol;

    public Player createPlayer() {
        RadioButton userSymbolButton = (RadioButton) userSymbol.getSelectedToggle();
        String userSymbolString = userSymbolButton.getText();

        RadioButton opponentButton = (RadioButton) opponent.getSelectedToggle();
        String opponentType = opponentButton.getText().toUpperCase();

        if (opponentType.equals("USER")) {
            return new User("User");
        } else {
            Level level = OPPONENT_MAP.get(opponentType);
            String aiSymbol = userSymbolString.equals("X") ? "O" : "X";
            return new AI(level, aiSymbol);
        }
    }
}