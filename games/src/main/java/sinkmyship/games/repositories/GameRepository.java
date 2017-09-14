package sinkmyship.games.repositories;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import sinkmyship.games.GameDetailedView;
import sinkmyship.games.GameSummaryView;
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

    public abstract Try<List<GameSummaryView>> findAllGames();

    public abstract Try<Option<GameDetailedView>> findOneGame(String gameId);

}