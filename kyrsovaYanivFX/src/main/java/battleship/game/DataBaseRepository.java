package battleship.game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseRepository implements Repository {
    private DataBaseConnector dataBaseConnector;

    public DataBaseRepository(
            DataBaseConnector dataBaseConnector) {
        this.dataBaseConnector = dataBaseConnector;
        try (Connection conn = dataBaseConnector.getConnection()) {
            String tableCreateStr =
                    "CREATE TABLE IF NOT EXISTS Players\n" +
                            "(PlayerName VARCHAR(50),"+
                            "PlayerWin INT, PlayerDefeat INT,PRIMARY KEY (PlayerName));";
            Statement createTable = conn.createStatement();
            createTable.execute(tableCreateStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Player> getAllPlayers(){
        List<Player> players = new ArrayList<>();
        try(Connection connection = dataBaseConnector.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Players");
            while(rs.next()){
                players.add(new Player(rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return players;
    }

    @Override
    public boolean addPlayer(Player player) {
        int updCount = 0;

        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO Players (PlayerName,PlayerWin," +
                            "PlayerDefeat) VALUES (?,?,?)");
            preparedStatement.setString(1, player.getName());
            preparedStatement.setInt(2, player.getVictory());
            preparedStatement.setInt(3, player.getDefeat());

            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }

    @Override
    public boolean increasePlayerWins(Player player) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement( "UPDATE Players " +
                            "SET PlayerWin = ?" +
                            "WHERE PlayerName = ?");
            preparedStatement.setInt(1, player.getVictory());
            preparedStatement.setString(2, player.getName());
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }
    @Override
    public boolean increasePlayerDefeat(Player player) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement( "UPDATE Players " +
                            "SET PlayerDefeat = ?" +
                            "WHERE PlayerName = ?");
            preparedStatement.setInt(1, player.getDefeat());
            preparedStatement.setString(2, player.getName());
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }
    @Override
    public boolean isPlayer(String plName) {
        int isCount = 0;
        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement =
                    connection.prepareStatement(
                            "select * from Players where PlayerName = ?"
                    );
            statement.setString(1, plName);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                isCount++;
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return isCount==0;
    }
    @Override
    public Player getPlayer(String plName) {
        Player player = null;
        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement =
                    connection.prepareStatement(
                            "select * from Players where PlayerName = ?"
                    );
            statement.setString(1, plName);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                player = new Player(rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3)
                        );
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return player;
    }




}
