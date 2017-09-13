package sinkmyship.games;

import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.domain.Game;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class StartGameHandler extends ApiGatewayHandler<StartGame, GameView> {

    private final GameRepository repository;

    public StartGameHandler() {
        this(GameRepository.getInstance());
    }

    public StartGameHandler(GameRepository repository) {
        super(StartGame.class);
        this.repository = repository;
    }

    @Override
    public Try<GameView> handle(StartGame startGame) {
        return repository.load(startGame.gameId)
                .map(Game::startGame)
                .flatMap(repository::update)
                .map(GameView::of);
    }
}
