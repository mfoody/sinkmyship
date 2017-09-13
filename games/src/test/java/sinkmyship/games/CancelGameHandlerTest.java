package sinkmyship.games;

import io.vavr.control.Try;
import org.junit.Test;
import sinkmyship.games.domain.Game;
import sinkmyship.games.domain.GameStatus;
import sinkmyship.games.repositories.GameRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class CancelGameHandlerTest {

    private final GameRepository repository = mock(GameRepository.class);

    private final CancelGameHandler handler = new CancelGameHandler(repository);

    @Test
    public void handle() {
        CancelGame command = new CancelGame("1");

        Game game = Game.of(command.gameId, "p1", "p2", 4, 6);
        when(repository.load(command.gameId)).thenReturn(Try.success(game));
        Game cancelledGame = game.cancelGame();
        when(repository.update(cancelledGame)).thenReturn(Try.success(cancelledGame));

        Try<GameView> view = handler.handle(command);
        GameView expectedView = ImmutableGameView.of(command.gameId, GameStatus.CANCELLED);
        assertEquals(expectedView, view.get());
    }

}