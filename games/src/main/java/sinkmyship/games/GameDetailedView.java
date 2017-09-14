package sinkmyship.games;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import sinkmyship.games.domain.GameStatus;

/**
 *
 */
@Value.Immutable
@JsonSerialize
public abstract class GameDetailedView {

    @Value.Parameter
    public abstract String gameId();

    @Value.Parameter
    public abstract GameStatus status();

}