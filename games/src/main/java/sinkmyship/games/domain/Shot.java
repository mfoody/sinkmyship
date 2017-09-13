package sinkmyship.games.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 *
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableShot.class)
public abstract class Shot {

    @Value.Parameter
    @JsonProperty
    public abstract Position position();

}