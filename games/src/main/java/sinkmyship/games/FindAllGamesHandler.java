package sinkmyship.games;

import io.vavr.collection.List;
import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class FindAllGamesHandler extends ApiGatewayHandler<FindAllGames, List<GameSummaryView>> {

    private final GameRepository repository;

    public FindAllGamesHandler() {
        this(GameRepository.getInstance());
    }

    public FindAllGamesHandler(GameRepository repository) {
        super(FindAllGames.class);
        this.repository = repository;
    }

    @Override
    public Try<List<GameSummaryView>> handle(FindAllGames findAllGames) {
        return repository.findAllGames();
    }

}
