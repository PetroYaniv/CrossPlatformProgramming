package battleship.game;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

        TableColumn<Player, Integer> winsCol = new TableColumn<>("Wins");
        winsCol.setCellValueFactory(new PropertyValueFactory<>("Victory"));

        TableColumn<Player, Integer> defeatsCol = new TableColumn<>("Defeats");
        defeatsCol.setCellValueFactory(new PropertyValueFactory<>("Defeat"));

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

        // Вирівнюємо кнопку по центру
        HBox buttonBox = new HBox(returnButton);
        buttonBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        VBox vbox = new VBox(tableView, buttonBox);
        VBox.setVgrow(tableView, Priority.ALWAYS); // таблиця росте разом із вікном

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.setTitle("Players Table");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }
}
