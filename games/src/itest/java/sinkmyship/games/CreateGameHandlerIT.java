package sinkmyship.games;

import okhttp3.Response;
import org.junit.Test;
import sinkmyship.IntegrationTest;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CreateGameHandlerIT extends IntegrationTest {

    @Test
    public void handle() throws Exception {
        CreateGame cmd = ImmutableCreateGame.of("p1", "p2", UUID.randomUUID().toString(), 10, 10);
        Response response = post(cmd, url("games/CreateGame"));
        assertEquals(200, response.code());

        String expected = String.format("{\"gameId\":\"%s\",\"status\":\"NEW\"}", cmd.gameId());
        assertEquals(expected, response.body().string());
    }

}