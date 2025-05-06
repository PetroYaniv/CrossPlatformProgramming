module battleship.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens battleship.game to javafx.fxml;
    exports battleship.game;
}