package battleship.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class LoginScreen {

    public static void launchLogin() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            new LoginScreen().start(stage);
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


        Label labelName = new Label("Enter player name:");
        grid.add(labelName, 0, 2);

        TextField playerNameField = new TextField();
        grid.add(playerNameField, 1, 2);

        // Кнопка логіну
        Button loginButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 0, 4, 2, 1);

        // Обробка натискання
        loginButton.setOnAction(e -> {


            String playerName = playerNameField.getText();
            Repository _repos;
            _repos = new DataBaseRepository(
                    new DataBaseConnector("BattleShipDB")
            );

            if(!_repos.isPlayer(playerName)) {
                Player player = _repos.getPlayer(playerName);
                System.out.println(player.toString());
                // Закрити поточне вікно
                primaryStage.close();

                // Запустити гру
                BattleShipGame.launchGame(player);
            }
            else {
                Player player = new Player(playerName);
                System.out.println(player.toString());
                // Закрити поточне вікно
                primaryStage.close();

                // Запустити гру
                BattleShipGame.launchGame(player);
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
