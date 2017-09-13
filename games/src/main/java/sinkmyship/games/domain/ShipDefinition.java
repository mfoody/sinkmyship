package sinkmyship.games.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 *
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableShipDefinition.class)
public abstract class ShipDefinition {

    @Value.Parameter
    public abstract String shipId();

    @Value.Parameter
    public abstract int length();

}