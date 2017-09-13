package sinkmyship.games;

import io.vavr.control.Try;
import org.junit.Test;
import sinkmyship.games.domain.Game;
import sinkmyship.games.repositories.GameRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 */
public class StartGameHandlerTest {

    @Test
    public void handle() {
        StartGame cmd = new StartGame("1");
        Game game = Game.of(cmd.gameId, "p1", "p2", 10, 10);

        GameRepository repository = mock(GameRepository.class);
        when(repository.load(cmd.gameId)).thenReturn(Try.success(game));

        Game startedGame = game.startGame();
        when(repository.update(startedGame)).thenReturn(Try.success(startedGame));

        StartGameHandler handler = new StartGameHandler(repository);
        Try<GameView> view = handler.handle(cmd);
        assertEquals(GameView.of(startedGame), view.get());
    }
}