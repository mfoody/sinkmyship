package sinkmyship.games.domain;

import org.junit.Test;
import sinkmyship.common.Serializer;

import static org.junit.Assert.*;

/**
 *
 */
public class ShipDefinitionTest {

    @Test
    public void serialize() {
        ShipDefinition definition = ImmutableShipDefinition.of("Battleship", 4);
        assertEquals(definition, Serializer.deserialize(Serializer.serialize(definition), ShipDefinition.class).get());
    }
}