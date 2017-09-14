package sinkmyship.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;
import sinkmyship.games.domain.GameStatus;

/**
 *
 */
@Value.Immutable
public abstract class GameSummaryView {

    @Value.Parameter
    @JsonProperty
    public abstract String gameId();

    @Value.Parameter
    @JsonProperty
    public abstract GameStatus status();

}