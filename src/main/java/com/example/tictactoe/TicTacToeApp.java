package com.example.tictactoe;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TicTacToeApp extends Application {
    public static final int BOARD_SIZE = 3;
    public static final int TILE_SIZE = 200;
    public static final int SCENE_SIZE = BOARD_SIZE * TILE_SIZE;
    @FXML
    public BorderPane root;
    @FXML
    public MenuItem newGameMenu;
    @FXML
    public MenuItem exitMenu;
    private GridPane tiles;
    private GameProcessor processor;
    private AnimationTimer gameTimer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApp.class.getResource("main-view.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);
        stage.setTitle("TicTacToe Game!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        showNewGameDialog();
    }

    private void generatePlayBoard() {
        processor = new GameProcessor(BOARD_SIZE);
        tiles = new GridPane();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Tile tile = new Tile(i, j, TILE_SIZE, StringUtils.EMPTY, processor);
                GridPane.setConstraints(tile, j, i);
                tiles.getChildren().add(tile);
            }
        }
        root.setCenter(tiles);
    }

    @FXML
    public void showNewGameDialog() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApp.class.getResource("new-game-dialog.fxml"));
        Optional<ButtonType> dialog = createNewGameDialog(fxmlLoader);
        if (dialog.isPresent() && dialog.get() == ButtonType.OK) {
            NewGameDialogController controller = fxmlLoader.getController();
            Player opponent = controller.createPlayer();
            startNewGame(opponent);
        } else {
            disableBoard();
        }
    }

    private Optional<ButtonType> createNewGameDialog(FXMLLoader fxmlLoader) {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("New Game");
            dialog.initOwner(root.getScene().getWindow());
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            return dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void startNewGame(Player opponent) {
        generatePlayBoard();
        root.requestFocus();
        gameLoop(opponent);
    }

    public void gameLoop(Player opponent) {
        if (opponent instanceof AI) {
            AI aiPlayer = (AI) opponent;
            gameTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (processor.isGameFinished()) {
                        endGame();
                    } else if (processor.getTurn().equals(aiPlayer.getAiSymbol())) {
                        Field aiMoveField = aiPlayer.move(processor.getGameField());
                        markTile(aiMoveField);
                    }
                }
            };
            gameTimer.start();
        } else {
            gameTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (processor.isGameFinished()) {
                        endGame();
                    }
                }
            };
            gameTimer.start();
        }
    }

    private void markTile(Field aiMoveField) {
        processor.markSymbol(aiMoveField.getRowIndex(), aiMoveField.getColumnIndex());
        for (Node child : tiles.getChildren()) {
            if (GridPane.getRowIndex(child) == aiMoveField.getRowIndex() && GridPane.getColumnIndex(child) == aiMoveField.getColumnIndex()) {
                Tile t = (Tile) child;
                t.setText(processor.getGameField().getFieldValue(aiMoveField.getRowIndex(), aiMoveField.getColumnIndex()));
                break;
            }
        }
    }

    private void endGame() {
        gameTimer.stop();

        Label scores = (Label) root.getBottom();
        scores.setText("X wins: " + processor.getXWinningsCounter() +
                "\tO wins: " + processor.getOWinningsCounter() +
                "\tDraws: " + processor.getDrawsCounter());
        paintWinningCombo(processor.getWinningCombo());

        showGameOverAlert();
    }

    public void paintWinningCombo(List<Field> winningCombo) {
        for (Node child : tiles.getChildren()) {
            for (Field field : winningCombo) {
                if (GridPane.getRowIndex(child) == field.getRowIndex()
                        && GridPane.getColumnIndex(child) == field.getColumnIndex()) {
                    Tile t = (Tile) child;
                    t.setStyle("-fx-text-fill: green; -fx-base: #b6e7c9;");
                }
            }
        }
    }

    private void showGameOverAlert() {
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION, StringUtils.EMPTY, new ButtonType("New Game"));
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText(null);
        gameOverAlert.initOwner(root.getScene().getWindow());

        String winner = processor.getWinner();
        if (winner.isEmpty()) {
            gameOverAlert.setContentText("Draw!");
        } else {
            gameOverAlert.setContentText(winner + " wins!");
        }

        gameOverAlert.setOnHidden(e -> {
            gameOverAlert.close();
            showNewGameDialog();
        });

        gameOverAlert.show();
    }

    private void disableBoard() {
        for (Node child : tiles.getChildren()) {
            Tile t = (Tile) child;
            t.setDisable(true);
        }
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

}