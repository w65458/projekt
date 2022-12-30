package com.example.tictactoe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {
    private int rowIndex;
    private int columnIndex;
    private String value;
}
