package battleship.game;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TableOfPlayers {
    public static void launchGame() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            new TableOfPlayers().start(stage);
        });
    }

    public void start(Stage stage) {

        Repository _repos = new DataBaseRepository(
                new DataBaseConnector("BattleShipDB"));

        List<Player> players = _repos.getAllPlayers();
        ObservableList<Player> playersList =
                FXCollections.observableArrayList(players);

        TableView<Player> tableView = new TableView<>();

        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        nameCol.setStyle( "-fx-text-fill: red;"+
                "-fx-font-weight: bold;");
        TableColumn<Player, Integer> winsCol = new TableColumn<>("Wins");
        winsCol.setCellValueFactory(new PropertyValueFactory<>("Victory"));
        winsCol.setStyle( "-fx-text-fill: red;"+
                "-fx-font-weight: bold;");
        TableColumn<Player, Integer> defeatsCol = new TableColumn<>("Defeats");
        defeatsCol.setCellValueFactory(new PropertyValueFactory<>("Defeat"));
        defeatsCol.setStyle( "-fx-text-fill: red;"+
                "-fx-font-weight: bold;");
        tableView.setItems(playersList);
        tableView.getColumns().addAll(nameCol, winsCol, defeatsCol);

        // Створюємо кнопку
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            MainScreen mainScreen = new MainScreen();
            Stage stage2 = new Stage();
            mainScreen.start(stage2);
            stage.close();


        });
        Image backgroundImage = new Image(getClass()
                .getResource("/battleship/game/background01.jpg").toExternalForm());



// Створення фону


        // Вирівнюємо кнопку по центру
        HBox buttonBox = new HBox(returnButton);
        buttonBox.setStyle("-fx-alignment: center; -fx-padding: 10;");
        tableView.setStyle(
                "-fx-background-color: transparent;" +     // фон таблиці
                        "-fx-control-inner-background: transparent;" + // фон клітинок
                        "-fx-table-cell-border-color: transparent;" +  // межі клітинок
                        "-fx-table-header-border-color: transparent;"

        );
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

// Створення фону
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        VBox vbox = new VBox(tableView, buttonBox);
        vbox.setBackground(new Background(bgImage));
        VBox.setVgrow(tableView, Priority.ALWAYS); // таблиця росте разом із вікном

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Players Table");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }
}
