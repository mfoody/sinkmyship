package sinkmyship.games;

import io.vavr.control.Try;
import org.junit.Test;
import sinkmyship.games.domain.Direction;
import sinkmyship.games.domain.Game;
import sinkmyship.games.domain.Position;
import sinkmyship.games.repositories.GameRepository;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 */
public class PlaceShipHandlerTest {

    @Test
    public void handle() {
        String gameId = "1";
        String player1 = "pw1";
        String player2 = "pw2";
        PlaceShip placeShip = new PlaceShip(gameId, player1, "Battleship", 3, 2, Direction.VERTICAL);

        GameRepository repository = mock(GameRepository.class);
        Game game = Game.of(gameId, player1, player2, 10, 10);
        when(repository.load(gameId)).thenReturn(Try.success(game));

        Game updatedGame = game.placeShip(player1, "Battleship", new Position(3, 2), Direction.VERTICAL);
        when(repository.update(updatedGame)).thenReturn(Try.success(updatedGame));

        PlaceShipHandler handler = new PlaceShipHandler(repository);

        Try<GameView> view = handler.handle(placeShip);
        GameView expected = GameView.of(updatedGame);
        assertEquals(expected, view.get());
    }
}