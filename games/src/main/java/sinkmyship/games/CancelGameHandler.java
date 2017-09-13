package sinkmyship.games;

import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.domain.Game;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class CancelGameHandler extends ApiGatewayHandler<CancelGame, GameView> {

    private final GameRepository repository;

    public CancelGameHandler() {
        this(GameRepository.getInstance());
    }

    public CancelGameHandler(GameRepository repository) {
        super(CancelGame.class);
        this.repository = repository;
    }

    @Override
    public Try<GameView> handle(CancelGame cancelGame) {
        return repository.load(cancelGame.gameId)
                .map(Game::cancelGame)
                .flatMap(repository::update)
                .map(GameView::of);
    }

}