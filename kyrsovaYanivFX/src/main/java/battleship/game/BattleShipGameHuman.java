package battleship.game;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.Random;

public class BattleShipGameHuman {
    public String playerAName;
    public String playerBName;
    private int PlayerWin = 0;
    private int EnemyWin = 0;
    private static final int WIN = 15;
    private static final int SIZE = 10;
    private Button[][] buttonsEnemy = new Button[SIZE][SIZE];
    private int[][] boardEnemy = new int[SIZE][SIZE]; // 0 = порожньо, 1 = корабель, 2 = влучання, 3 = промах
    private Button[][] buttons = new Button[SIZE][SIZE];
    private int[][] board = new int[SIZE][SIZE]; // 0 = порожньо, 1 = корабель, 2 = влучання, 3 = промах
    private Stage stageClose;
    private boolean gameOver = false;
    private boolean player1MakeChoise = false;
    private boolean whoMakeChoise = false;
    public  BorderPane root = new BorderPane();

    public static void launchGame(Player playerA, Player playerB) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            new BattleShipGameHuman().start(stage,playerA,playerB);
        });
    }

    public void start(Stage stage,Player playerA,Player playerB) {
        stage.setTitle("Battle Ship Game");
        playerAName = playerA.getName();
        playerBName = playerB.getName();



        VBox playerBoard = createLabeledBoard(playerAName+" Field", buttons, true);
        VBox enemyBoard = createLabeledBoard(playerBName+" Field", buttonsEnemy, false);

        HBox boardsBox = new HBox(50, playerBoard, enemyBoard);
        boardsBox.setAlignment(Pos.CENTER);

// Панель керування
        GridPane controlPanel = new GridPane();
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setHgap(10);
        controlPanel.setVgap(10);
        controlPanel.setPadding(new Insets(10));

// Елементи керування
        Label enterHitX = new Label("Enter OX:");
        TextField _hitX = new TextField();
        Label enterHitY = new Label("Enter OY:");
        TextField _hitY = new TextField();
        Button enterHitButton = new Button("Enter Hit");
        Button randomHitButton = new Button("Random Hit");

// Розміщення по сітці
       controlPanel.add(enterHitX, 0, 0);

        controlPanel.add(_hitX, 1, 0);
        controlPanel.add(enterHitY, 0, 1);
        controlPanel.add(_hitY, 1, 1);
        controlPanel.add(enterHitButton, 0, 2); // кнопка на всю ширину
        controlPanel.add(randomHitButton, 1, 2);

// Обробка натискання кнопки
        enterHitButton.setOnAction(e -> {
            try {
                int X = Integer.parseInt(_hitX.getText());
                int Y = Integer.parseInt(_hitY.getText());

                if (X < 0 || X >= SIZE || Y < 0 || Y >= SIZE) {
                    showAlert("Invalid", "Coordinates must be between 0 and 9");
                    return;
                }
                if(!whoMakeChoise) {
                    whoMakeChoise = !whoMakeChoise;
                if ((boardEnemy[Y][X] == 2 || boardEnemy[Y][X] == 3)) {
                    showAlert("Invalid", "These coordinates were already hit.");
                }
                else {
                    handleCellClick(Y, X, 2);
                }
                }
                else{
                    whoMakeChoise = !whoMakeChoise;
                    if ((board[Y][X] == 2 || board[Y][X] == 3)) {
                        showAlert("Invalid", "These coordinates were already hit.");
                    }
                    else {
                        handleCellClick(Y, X, 0);
                    }
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter valid numbers for OX and OY.");
            }
        });
        randomHitButton.setOnAction(e -> {
            Random rand = new Random();
            if(!whoMakeChoise) {
                whoMakeChoise = !whoMakeChoise;
                int r = rand.nextInt(SIZE);
                int c = rand.nextInt(SIZE);
                while (boardEnemy[r][c] == 2 || boardEnemy[r][c] == 3) {
                    r = rand.nextInt(SIZE);
                    c = rand.nextInt(SIZE);
                }
                handleCellClick(r, c, 2);
            }
            else{
                whoMakeChoise = !whoMakeChoise;
                int r = rand.nextInt(SIZE);
                int c = rand.nextInt(SIZE);
                while (board[r][c] == 2 || board[r][c] == 3) {
                    r = rand.nextInt(SIZE);
                    c = rand.nextInt(SIZE);
                }
                handleCellClick(r, c, 0);
            }
        });


        Button shufleButton = new Button("New ships arrangement");
        shufleButton.setOnAction(e -> {
            resetBoard(1);
            placeShips(1);
        });

        Button OkButton = new Button("Confirm");
        OkButton.setOnAction(e -> {

            if(!player1MakeChoise) {
                resetBoard(2);
                placeShips(2);
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        buttons[i][j].setStyle("-fx-background-color: CYAN;");
                    }
                }
                player1MakeChoise = true;
                shufleButton.setOnAction(ee -> {
                resetBoard(2);
                placeShips(2);
                });
            }
            else {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        buttonsEnemy[i][j].setStyle("-fx-background-color: CYAN;");
                    }
                }
                    root.setBottom(null);
                    root.setBottom(controlPanel);
            }


        });



        VBox leftButtons = new VBox(10); // 10 — відступ між кнопками
        leftButtons.setAlignment(Pos.CENTER);
        leftButtons.setPadding(new Insets(10));
        leftButtons.getChildren().addAll(shufleButton, OkButton);


        root.setBottom(leftButtons);


        root.setCenter(boardsBox);


        Scene scene = new Scene(root, 1300, 750);
        stage.setScene(scene);
        stageClose = stage;
        stageClose.show();

        startGame();
    }

    private void handleCellClick(int row, int col,int choise) {


        if (choise == 0) {
            if (board[row][col] == 1) {
                EnemyWin = EnemyWin + 1;
                buttons[row][col].setStyle("-fx-background-color: RED;");
                buttons[row][col].setText("X");
                buttons[row][col].setTextFill(Color.WHITE);
                buttons[row][col].setFont(Font.font(18));
                board[row][col] = 2;
            } else if (board[row][col] == 0) {
                buttons[row][col].setStyle("-fx-background-color: WHITE;");
                buttons[row][col].setText("•");
                buttons[row][col].setTextFill(Color.BLACK);
                buttons[row][col].setFont(Font.font(14));
                board[row][col] = 3;
            }}
        else {
            if (boardEnemy[row][col] == 1) {
                PlayerWin = PlayerWin + 1;
                buttonsEnemy[row][col].setStyle("-fx-background-color: RED;");
                buttonsEnemy[row][col].setText("X");
                buttonsEnemy[row][col].setTextFill(Color.WHITE);
                buttonsEnemy[row][col].setFont(Font.font(18));
                boardEnemy[row][col] = 2;
            } else if (boardEnemy[row][col] == 0) {
                buttonsEnemy[row][col].setStyle("-fx-background-color: WHITE;");
                buttonsEnemy[row][col].setText("•");
                buttonsEnemy[row][col].setTextFill(Color.BLACK);
                buttonsEnemy[row][col].setFont(Font.font(14));
                boardEnemy[row][col] = 3;
            }
        }
        if(PlayerWin==WIN){
            gameOver = true;
            showAlert("WIN", playerAName+" win.");
            Stage stage = new Stage();
            MainScreen mainScreen = new MainScreen();
            mainScreen.start(stage);
            stageClose.close();


        }
        if(EnemyWin==WIN){
            gameOver = true;
            showAlert("WIN", playerBName+" win.");
            Stage stage = new Stage();
            MainScreen mainScreen = new MainScreen();
            mainScreen.start(stage);
            stageClose.close();

        }


    }

    private void startGame() {
        resetBoard(1);
        //resetBoard(2);
        placeShips(1);
        //placeShips(2);
    }

    private void resetBoard(int choise) {
        if(choise == 1) {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    board[row][col] = 0;
                    buttons[row][col].setStyle("-fx-background-color: CYAN;");
                    buttons[row][col].setText("");
                }
            }
        }
        else {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    boardEnemy[row][col] = 0;
                    buttonsEnemy[row][col].setStyle("-fx-background-color: CYAN;");
                    buttonsEnemy[row][col].setText("");
                }
            }
        }
    }

    private void placeShips(int choise) {

        int[] shipSizes = {4, 3, 3, 2, 2, 1};
        Random rand = new Random();
        if(choise == 1) {
            for (int size : shipSizes) {
                boolean placed = false;
                while (!placed) {
                    int row = rand.nextInt(SIZE);
                    int col = rand.nextInt(SIZE);
                    boolean horizontal = rand.nextBoolean();

                    if (canPlaceShip(row, col, size, horizontal, 1)) {
                        for (int i = 0; i < size; i++) {
                            int r = row + (horizontal ? 0 : i);
                            int c = col + (horizontal ? i : 0);
                            board[r][c] = 1;
                            buttons[r][c].setStyle("-fx-background-color: GREY;"); // показати кораблі
                        }
                        placed = true;
                    }
                }
            }
        }
        else {
            for (int size : shipSizes) {
                boolean placed = false;
                while (!placed) {
                    int row = rand.nextInt(SIZE);
                    int col = rand.nextInt(SIZE);
                    boolean horizontal = rand.nextBoolean();

                    if (canPlaceShip(row, col, size, horizontal, 0)) {
                        for (int i = 0; i < size; i++) {
                            int r = row + (horizontal ? 0 : i);
                            int c = col + (horizontal ? i : 0);
                            boardEnemy[r][c] = 1;
                            buttonsEnemy[r][c].setStyle("-fx-background-color: GREY;"); // показати кораблі
                        }
                        placed = true;
                    }
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal,int choise) {
        if (choise == 1) {
            for (int i = 0; i < size; i++) {
                int r = row + (horizontal ? 0 : i);
                int c = col + (horizontal ? i : 0);
                if (r >= SIZE || c >= SIZE || board[r][c] != 0) {
                    return false;
                }
            }
            return true;
        }
        else {
            for (int i = 0; i < size; i++) {
                int r = row + (horizontal ? 0 : i);
                int c = col + (horizontal ? i : 0);
                if (r >= SIZE || c >= SIZE || boardEnemy[r][c] != 0) {
                    return false;
                }
            }
            return true;
        }
    }
    public void None(){}
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private VBox createLabeledBoard(String title, Button[][] buttonsArray, boolean isPlayer) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(0);
        grid.setVgap(0);


        for (int col = 0; col < SIZE; col++) {
            Label label = new Label(String.valueOf((col)));
            label.setMinSize(50, 30);
            label.setAlignment(Pos.CENTER);
            grid.add(label, col + 1, 0); // зсув на 1 вправо
        }


        for (int row = 0; row < SIZE; row++) {
            Label label = new Label(String.valueOf(row));
            label.setMinSize(30, 50);
            label.setAlignment(Pos.CENTER_RIGHT);
            grid.add(label, 0, row + 1); // зсув на 1 вниз

            for (int col = 0; col < SIZE; col++) {
                Button cell = new Button();
                cell.setPrefSize(50, 50);
                cell.setStyle("-fx-background-color: CYAN;");
                int finalRow = row;
                int finalCol = col;

                if (isPlayer) {
                    cell.setOnAction(e ->
                            {
                                if(whoMakeChoise){
                                    handleCellClick(finalRow, finalCol, 0);
                                    whoMakeChoise = !whoMakeChoise;
                                }
                                else{
                                    showAlert("Error",playerAName+" makes a move");
                                }
                            });
                    buttons[finalRow][finalCol] = cell;
                } else {
                    cell.setOnAction(e ->
                    {
                        if(!whoMakeChoise){
                            handleCellClick(finalRow, finalCol, 1);
                            whoMakeChoise = !whoMakeChoise;
                        }
                        else{
                            showAlert("Error",playerBName+" makes a move");
                        }
                    });
                    buttonsEnemy[finalRow][finalCol] = cell;
                }

                grid.add(cell, col + 1, row + 1); // Зсув на 1 по X та Y
            }
        }

        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(16));
        boardTitle.setAlignment(Pos.CENTER);
        VBox boardBox = new VBox(5, boardTitle, grid);
        boardBox.setAlignment(Pos.CENTER);
        return boardBox;
    }

}
