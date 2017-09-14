package sinkmyship.games;

import io.vavr.control.Option;
import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class FindOneGameHandler extends ApiGatewayHandler<FindOneGame, Option<GameDetailedView>> {

    private final GameRepository repository;

    public FindOneGameHandler() {
        this(GameRepository.getInstance());
    }

    public FindOneGameHandler(GameRepository repository) {
        super(FindOneGame.class);
        this.repository = repository;
    }

    @Override
    public Try<Option<GameDetailedView>> handle(FindOneGame findOneGame) {
        return repository.findOneGame(findOneGame.gameId());
    }
}
