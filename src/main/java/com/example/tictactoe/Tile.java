package com.example.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tile extends Button {

    public Tile(int rowIndex, int columnIndex, int tileSize, String symbol, GameProcessor processor) {
        setTileProperties(tileSize, symbol);
        setOnMouseClicked(event -> {
            processor.markSymbol(rowIndex, columnIndex);
            setText(processor.getGameField().getFieldValue(rowIndex, columnIndex));
        });
    }

    void setTileProperties(int tileSize, String symbol) {
        setPrefSize(tileSize, tileSize);
        setAlignment(Pos.CENTER);
        setFont(Font.font(80));
        setTextAlignment(TextAlignment.CENTER);
        setText(symbol);
    }

}