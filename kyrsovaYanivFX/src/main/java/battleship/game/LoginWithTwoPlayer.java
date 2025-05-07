package battleship.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class LoginWithTwoPlayer {

    public static void launchLogin() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            new LoginWithTwoPlayer().start(stage);
        });
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battle Ships:");

        // Основна сітка
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Текстові поля та мітки
        Label labelPlayerInfo = new Label("Enter player login information:");
        grid.add(labelPlayerInfo, 0, 0, 2, 1);


        Label labelNameA = new Label("Enter player 1 name:");
        grid.add(labelNameA, 0, 2);

        TextField playerNameFieldA = new TextField();
        grid.add(playerNameFieldA, 1, 2);
        Label labelNameB = new Label("Enter player 2 name:");
        grid.add(labelNameB, 0, 3);

        TextField playerNameFieldB = new TextField();
        grid.add(playerNameFieldB, 1, 3);
        // Кнопка логіну
        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> {
            MainScreen mainScreen = new MainScreen();
            Stage stage2 = new Stage();
            mainScreen.start(stage2);
            primaryStage.close();


        });

        Button loginButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().addAll(loginButton,returnButton);
        grid.add(hbBtn, 0, 5, 2, 1);

        // Обробка натискання
        loginButton.setOnAction(e -> {


            String playerNameA = playerNameFieldA.getText();


            String playerNameB = playerNameFieldB.getText();

            Repository _repos;
            _repos = new DataBaseRepository(
                    new DataBaseConnector("BattleShipDB")
            );
            if(!_repos.isPlayer(playerNameA) && !_repos.isPlayer(playerNameB)) {
                Player playerA = _repos.getPlayer(playerNameA);
                Player playerB = _repos.getPlayer(playerNameB);
                primaryStage.close();

                // Запустити гру
                BattleShipGameHuman.launchGame(playerA,playerB);
            }
            else if(!_repos.isPlayer(playerNameA) && _repos.isPlayer(playerNameB)){
                Player playerA = _repos.getPlayer(playerNameA);
                Player playerB =  new Player(playerNameB);
                primaryStage.close();

                // Запустити гру
                BattleShipGameHuman.launchGame(playerA,playerB);
            }
            else if(_repos.isPlayer(playerNameA) && !_repos.isPlayer(playerNameB)){
                Player playerA = new Player(playerNameA);
                Player playerB =  _repos.getPlayer(playerNameB);
                primaryStage.close();

                // Запустити гру
                BattleShipGameHuman.launchGame(playerA,playerB);
            }
            else {

                Player playerA = new Player(playerNameA);
                Player playerB = new Player(playerNameB);
                primaryStage.close();

                // Запустити гру
                BattleShipGameHuman.launchGame(playerA,playerB);
            }



        });

        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
