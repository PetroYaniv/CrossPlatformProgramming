module battleship.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens battleship.game to javafx.fxml;
    exports battleship.game;
}