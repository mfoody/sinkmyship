package sinkmyship.games;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 *
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableFindOneGame.class)
public abstract class FindOneGame {

    @Value.Parameter
    public abstract String gameId();
}
