package sinkmyship.games;

import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.junit.Test;
import sinkmyship.games.domain.Game;
import sinkmyship.games.domain.ShotHitMiss;
import sinkmyship.games.repositories.GameRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class ShootHandlerTest {

    @Test
    public void handle() {
        Shoot cmd = new Shoot("1", "p1", 4, 3);

        GameRepository repository = mock(GameRepository.class);

        Game game = Game.of(cmd.gameId, cmd.playerId, "p2", 10, 10);
        when(repository.load(game.gameId())).thenReturn(Try.success(game));

        Tuple2<Game, ShotHitMiss> gameHitMiss = game.shoot(cmd.playerId, cmd.x, cmd.y);
        when(repository.update(gameHitMiss._1())).thenReturn(Try.success(gameHitMiss._1()));

        ShootHandler handler = new ShootHandler(repository);
        Try<GameView> view = handler.handle(cmd);

        assertEquals(GameView.of(gameHitMiss._1()), view.get());
    }
}