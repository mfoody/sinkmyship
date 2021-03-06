package sinkmyship.games;

import okhttp3.Response;
import org.junit.Test;
import sinkmyship.IntegrationTest;
import sinkmyship.games.domain.Direction;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PlaceShipHandlerIT extends IntegrationTest {

    @Test
    public void handle() throws Exception {
        String awsApiId = "mpctlo9yhh";

        CreateGame create = ImmutableCreateGame.of("p1", "p2", UUID.randomUUID().toString(), 10, 10);
        Response creationResponse = post(create, url("games/CreateGame"));
        assertEquals(creationResponse.message(), 200, creationResponse.code());

        PlaceShip cmd = new PlaceShip(create.gameId(), create.player1(), "Battleship", 0, 0, Direction.VERTICAL);
        Response response = put(cmd, url("games/PlaceShip"));
        assertEquals(response.message(), 200, response.code());

        String expected = String.format("{\"gameId\":\"%s\",\"status\":\"NEW\"}", cmd.gameId);
        assertEquals(expected, response.body().string());
    }

}