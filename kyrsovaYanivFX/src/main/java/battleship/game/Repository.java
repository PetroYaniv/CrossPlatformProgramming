package battleship.game;

import java.util.List;


public interface Repository {

    List<Player> getAllPlayers();
    boolean addPlayer(Player player);
    boolean increasePlayerWins(Player player);
    boolean increasePlayerDefeat(Player player);
    boolean isPlayer(String plName);
    Player getPlayer(String plName);
}
