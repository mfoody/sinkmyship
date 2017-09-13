package sinkmyship.games;

import io.vavr.control.Try;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinkmyship.games.domain.DomainTime;
import sinkmyship.games.domain.Game;
import sinkmyship.games.repositories.GameRepository;

import java.time.Instant;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class CreateGameHandlerTest {

    private Instant now;

    @Before
    public void setup() {
        now = Instant.now();
        DomainTime.setNow(now);
    }

    @After
    public void teardown() {
        DomainTime.setNow(null);
    }

    @Test
    public void handle() throws Exception {
        CreateGame command = new CreateGame("123", "456", "abc", 5, 4);
        Game game = Game.of(command.gameId, command.player1, command.player2, command.width, command.height);

        GameRepository repository = mock(GameRepository.class);
        when(repository.create(game)).thenReturn(Try.success(game));
        CreateGameHandler handler = new CreateGameHandler(repository);

        Try<GameView> view = handler.handle(command);

        GameView expectedView = ImmutableGameView.of(game);
        assertEquals(expectedView, view.get());
    }

}