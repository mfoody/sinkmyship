package sinkmyship.games;

import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class ShootHandler extends ApiGatewayHandler<Shoot, GameView> {

    private final GameRepository repository;

    public ShootHandler() {
        this(GameRepository.getInstance());
    }

    public ShootHandler(GameRepository repository) {
        super(Shoot.class);
        this.repository = repository;
    }

    @Override
    public Try<GameView> handle(Shoot cmd) {
        return repository.load(cmd.gameId)
                .flatMap(game -> {
                    return game.shoot(cmd.playerId, cmd.x, cmd.y).apply((updatedGame, hitMiss) -> {
                        return repository.update(updatedGame);
                    });
                })
                .map(GameView::of);
    }

}