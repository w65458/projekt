module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.tictactoe to javafx.fxml;
    exports com.example.tictactoe;
}