package sinkmyship.games;

import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.games.domain.Game;
import sinkmyship.games.domain.Position;
import sinkmyship.games.repositories.GameRepository;

/**
 *
 */
public class PlaceShipHandler extends ApiGatewayHandler<PlaceShip, GameView> {

    private final GameRepository repository;

    public PlaceShipHandler() {
        this(GameRepository.getInstance());
    }

    public PlaceShipHandler(GameRepository repository) {
        super(PlaceShip.class);
        this.repository = repository;
    }

    @Override
    public Try<GameView> handle(PlaceShip cmd) {
        return repository.load(cmd.gameId)
                .flatMap((game) -> {
                    Game updatedGame = game.placeShip(cmd.playerId, cmd.shipId, new Position(cmd.x, cmd.y), cmd.direction);
                    return repository.update(updatedGame);
                })
                .map(GameView::of);
    }

}