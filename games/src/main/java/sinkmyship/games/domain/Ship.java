package sinkmyship.games.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 *
 */
@Value.Immutable(builder = false)
@JsonDeserialize(as = ImmutableShip.class)
public abstract class Ship {

    public static Ship of(ShipDefinition definition) {
        return ImmutableShip.of(definition.shipId(), definition.length());
    }

    public static Ship of(String shipId, int length) {
        return ImmutableShip.of(shipId, length, 0);
    }

    @Value.Parameter
    public abstract String shipId();

    @Value.Parameter
    public abstract int length();

    @Value.Parameter
    public abstract int hits();

    @JsonIgnore
    public boolean isSunk() {
        return hits() == length();
    }

    public Ship hit() {
        return ImmutableShip.copyOf(this).withHits(hits() + 1);
    }

}