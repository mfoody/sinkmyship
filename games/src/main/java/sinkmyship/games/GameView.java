package sinkmyship.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;
import sinkmyship.games.domain.Game;
import sinkmyship.games.domain.GameStatus;

/**
 *
 */
@Value.Immutable
public abstract class GameView {

    public static GameView of(Game game) {
        return ImmutableGameView.of(game.gameId(), game.status());
    }

    @Value.Parameter
    @JsonProperty
    public abstract String gameId();

    @Value.Parameter
    @JsonProperty
    public abstract GameStatus status();

}