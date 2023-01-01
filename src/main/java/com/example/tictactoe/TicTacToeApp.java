package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class TicTacToeApp extends Application {
    public static final int BOARD_SIZE = 3;
    public static final int TILE_SIZE = 200;
    public static final int SCENE_SIZE = BOARD_SIZE * TILE_SIZE;

    private static GameProcessor processor;
    private static GridPane tiles;

    @FXML
    public BorderPane root;
    @FXML
    public MenuItem newGameMenu;
    @FXML
    public MenuItem exitMenu;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApp.class.getResource("main-view.fxml"));
        root = fxmlLoader.load();
        generatePlayBoard();
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);
        stage.setTitle("TicTacToe Game!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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

}