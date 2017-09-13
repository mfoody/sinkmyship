package sinkmyship.games.domain;

import org.junit.Test;
import sinkmyship.common.Serializer;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ShotTest {

    @Test
    public void testSerialization() {
        Shot shot = ImmutableShot.of(new Position(4, 3));
        assertEquals(shot, Serializer.deserialize(Serializer.serialize(shot), Shot.class).get());
    }
}