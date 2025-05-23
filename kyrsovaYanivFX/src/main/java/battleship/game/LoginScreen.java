package battleship.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
        labelPlayerInfo.setStyle( "-fx-text-fill: red;"+
                "-fx-font-weight: bold;");
        grid.add(labelPlayerInfo, 0, 0, 2, 1);


        Label labelName = new Label("Enter player name:");
        labelName.setStyle( "-fx-text-fill: red;"+
                "-fx-font-weight: bold;");
        grid.add(labelName, 0, 2);

        TextField playerNameField = new TextField();
        grid.add(playerNameField, 1, 2);

        // Кнопка логіну
        Button loginButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);


        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> {
            MainScreen mainScreen = new MainScreen();
            Stage stage2 = new Stage();
            mainScreen.start(stage2);
            primaryStage.close();


        });

        hbBtn.getChildren().addAll(loginButton, returnButton);
        Image backgroundImage = new Image(getClass()
                .getResource("/battleship/game/back01.png").toExternalForm());



// Створення фону
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );
        grid.setBackground(new Background(bgImage));
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

                primaryStage.close();

                // Запустити гру
                BattleShipGame.launchGame(player);
            }
            else {
                Player player = new Player(playerName);

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
