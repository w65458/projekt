package com.example.tictactoe;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class AI implements Player {

    private final Level level;
    private final String aiSymbol;
    private final Random random;

    public AI(Level level, String aiSymbol) {
        this.level = level;
        this.aiSymbol = aiSymbol;
        this.random = new Random();
    }

}
