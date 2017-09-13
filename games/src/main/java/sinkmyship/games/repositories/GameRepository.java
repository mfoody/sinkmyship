package sinkmyship.games.repositories;

import io.vavr.control.Try;
import sinkmyship.games.domain.Game;

/**
 *
 */
public abstract class GameRepository {

    private static GameRepository instance;

    public static GameRepository getInstance() {
        if (instance == null) {
            instance = new DynamoGameRepository();
        }
        return instance;
    }

    public abstract Try<Game> create(Game game);

    public abstract Try<Game> update(Game game);

    public abstract Try<Game> load(String gameId);

}