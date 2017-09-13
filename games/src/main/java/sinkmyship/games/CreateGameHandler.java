package sinkmyship.games;

import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.domain.Game;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class CreateGameHandler extends ApiGatewayHandler<CreateGame, GameView> {

    private final GameRepository repository;

    public CreateGameHandler() {
        this(GameRepository.getInstance());
    }

    public CreateGameHandler(GameRepository repository) {
        super(CreateGame.class);
        this.repository = repository;
    }

    @Override
    public Try<GameView> handle(CreateGame startGame) {
        Game game = Game.of(startGame.gameId, startGame.player1, startGame.player2, startGame.width, startGame.height);
        return repository.create(game).map(GameView::of);
    }

}