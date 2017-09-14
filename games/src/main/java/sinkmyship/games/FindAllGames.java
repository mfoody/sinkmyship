package sinkmyship.games;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 *
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableFindAllGames.class)
public abstract class FindAllGames {
}
