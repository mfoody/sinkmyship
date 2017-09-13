package sinkmyship.games;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableStartGame.class)
public abstract class StartGame {

    @Value.Parameter
    public abstract String gameId();
}