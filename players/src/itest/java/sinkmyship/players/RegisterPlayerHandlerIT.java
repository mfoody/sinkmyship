package sinkmyship.players;

import okhttp3.Response;
import org.junit.Test;
import sinkmyship.IntegrationTest;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class RegisterPlayerHandlerIT extends IntegrationTest {

    @Test
    public void testTrue() throws Exception {
        RegisterPlayer command = new RegisterPlayer("abc123", "John", "Doe", "wmfoody+jdoe@gmail.com");
        Response response = post(command, url("players/RegisterPlayer"));
        assertEquals(200, response.code());

        String expected = "{\"id\":\"abc123\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"emailAddress\":\"wmfoody+jdoe@gmail.com\"}";
        assertEquals(expected, response.body().string());
    }

}