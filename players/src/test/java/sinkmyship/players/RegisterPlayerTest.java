package sinkmyship.players;

import io.vavr.control.Try;
import org.junit.Test;
import sinkmyship.common.Serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class RegisterPlayerTest {

    @Test
    public void serialize() throws Exception {
        RegisterPlayer cmd = new RegisterPlayer("123", "Mike", "Foody", "wmfoody@gmail.com");
        String json = Serializer.serialize(cmd);
        String expected = "{\"id\":\"123\",\"firstName\":\"Mike\",\"lastName\":\"Foody\",\"emailAddress\":\"wmfoody@gmail.com\"}";
        assertEquals(expected, json);
    }

    @Test
    public void deserialize() throws Exception {
        String json = "{\"id\":\"123\",\"firstName\":\"Mike\",\"lastName\":\"Foody\",\"emailAddress\":\"wmfoody@gmail.com\"}";
        RegisterPlayer expected = new RegisterPlayer("123", "Mike", "Foody", "wmfoody@gmail.com");
        Try<RegisterPlayer> actual = Serializer.deserialize(json, RegisterPlayer.class);
        assertEquals(expected, actual.get());
    }

    @Test
    public void missingValue() throws Exception {
        String json = "{\"id\":\"123\",\"lastName\":\"Foody\",\"emailAddress\":\"wmfoody@gmail.com\"}";
        Try<RegisterPlayer> player = Serializer.deserialize(json, RegisterPlayer.class);
        assertTrue(player.isFailure());
        assertTrue(player.getCause().getMessage().contains("firstName is required"));
    }

    @Test
    public void invalidValue() throws Exception {
        String json = "{\"id\":\"123\",\"firstName\":\"\",\"lastName\":\"Foody\",\"emailAddress\":\"wmfoody@gmail.com\"}";
        Try<RegisterPlayer> player = Serializer.deserialize(json, RegisterPlayer.class);
        assertTrue(player.isFailure());
        assertTrue(player.getCause().getMessage().contains("firstName is required"));
    }

    @Test
    public void roundtrip() throws Exception {
        RegisterPlayer cmd = new RegisterPlayer("123", "Mike", "Foody", "wmfoody@gmail.com");
        String json = Serializer.serialize(cmd);
        assertEquals(cmd, Serializer.deserialize(json, RegisterPlayer.class).get());
    }

}