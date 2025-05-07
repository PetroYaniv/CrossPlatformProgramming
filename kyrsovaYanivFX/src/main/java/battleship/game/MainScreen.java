package battleship.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

import java.net.URL;

public class MainScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu:");

        // Основна сітка
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Image backgroundImage = new Image(getClass()
                .getResource("/battleship/game/back02.png").toExternalForm());



// Створення фону
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );



        Button startWithComputer = new Button("Start game with computer");
        Button startWithPlayer = new Button("Start game with player");
        Button tableOfPlayer = new Button("Show table of player");
        Button _close = new Button("Close the game");
        grid.setBackground(new Background(bgImage));
        // Текстові поля та мітки
        startWithComputer.setOnAction(e -> {
            primaryStage.close();
            LoginScreen.launchLogin();
        });
        _close.setOnAction(e -> {
           primaryStage.close();
        });
        startWithPlayer.setOnAction(e -> {
            primaryStage.close();
            LoginWithTwoPlayer.launchLogin();
        });
        tableOfPlayer.setOnAction(e -> {
            primaryStage.close();
            TableOfPlayers.launchGame();
        });

        grid.add(startWithComputer, 0, 0);
        grid.add(startWithPlayer, 0, 2);
        grid.add(tableOfPlayer, 0, 4);
        grid.add(_close, 0, 6);




        Scene scene = new Scene(grid, 500, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);// ПОТІМ ВКЛЮЧИТИ!!!!!
        //Player player = new Player("Alex");
       //BattleShipGameHuman.launchGame();
    }
}
