package com.example.tictactoe;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class TestTile {

    private GameProcessor gameProcessor;

    @BeforeEach
    public void setUp() {
        gameProcessor = new GameProcessor(3);
    }

    @Test
    public void testOnMouseClicked() {
        Tile tile = new Tile(1, 1, 100, "X", gameProcessor);

        tile.fireEvent(getMouseEvent());

        assertEquals("X", gameProcessor.getGameField().getFieldValue(1, 1));
        assertEquals("X", tile.getText());
    }

    private static MouseEvent getMouseEvent() {
        return new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null);
    }
}
