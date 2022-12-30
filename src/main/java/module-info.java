module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires static lombok;
    requires org.apache.commons.lang3;

    opens com.example.tictactoe to javafx.fxml;
    exports com.example.tictactoe;

}