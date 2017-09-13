package sinkmyship.games;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableCreateGame.class)
public abstract class CreateGame {

    @Value.Parameter
    public abstract String player1();

    @Value.Parameter
    public abstract String player2();

    @Value.Parameter
    public abstract String gameId();

    @Value.Parameter
    public abstract int width();

    @Value.Parameter
    public abstract int height();

    @Value.Check
    void check() {
        require(nonEmpty(player1()), "player1 is required");
        require(nonEmpty(player2()), "player2 is required");
        require(nonEmpty(gameId()), "gameId is required");
        require(width() > 0, "width must be > 0");
        require(height() > 0, "height must be > 0");
    }
}