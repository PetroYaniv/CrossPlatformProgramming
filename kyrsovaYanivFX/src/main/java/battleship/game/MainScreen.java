package battleship.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

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

        Button startWithComputer = new Button("Start game with computer");
        Button startWithPlayer = new Button("Start game with player");
        Button tableOfPlayer = new Button("Show table of player");
        Button _close = new Button("Close the game");
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
            Repository _repos;
            _repos = new DataBaseRepository(
                    new DataBaseConnector("BattleShipDB"));

            List<Player> players = _repos.getAllPlayers();
            for (Player player : players) {

                System.out.println(player.toString());
            }
        });
       // Label labelPlayerInfo = new Label("Enter player login information:");
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
        //BattleShipGameHuman.launchGame();
    }
}
