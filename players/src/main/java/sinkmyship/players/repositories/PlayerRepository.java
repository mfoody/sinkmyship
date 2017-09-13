package sinkmyship.players.repositories;

import io.vavr.control.Try;
import sinkmyship.players.Player;

/**
 *
 */
public abstract class PlayerRepository {

    private static PlayerRepository instance;

    public static PlayerRepository getInstance() {
        if (instance == null) {
            instance = new DynamoPlayerRepository();
        }
        return instance;
    }

    public static void setInstance(PlayerRepository repository) {
        instance = repository;
    }

    /**
     * @param player
     * @return
     */
    public abstract Try<Player> create(Player player);

}