package sinkmyship.games;

import io.vavr.control.Try;
import okhttp3.Response;
import org.junit.Test;
import sinkmyship.IntegrationTest;
import sinkmyship.common.Serializer;
import sinkmyship.games.domain.GameStatus;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class FindOneGameIT extends IntegrationTest {

    @Test
    public void successWhenExists() throws Exception {
        String gameId = UUID.randomUUID().toString();
        CreateGame create = ImmutableCreateGame.of("p1", "p2", gameId, 10, 15);
        post(create, url("games/CreateGame"));

        FindOneGame query = ImmutableFindOneGame.of(gameId);
        Response response = post(query, url("games/FindOneGame"));
        assertEquals(200, response.code());
        Try<ImmutableGameDetailedView> view = Serializer.deserialize(response.body().string(), ImmutableGameDetailedView.class);
        assertEquals(ImmutableGameDetailedView.of(gameId, GameStatus.NEW), view.get());
    }

    @Test
    public void failWith404WhenNotExists() throws Exception {
        FindOneGame query = ImmutableFindOneGame.of(UUID.randomUUID().toString());
        Response response = post(query, url("games/FindOneGame"));
        assertEquals(404, response.code());
    }
}
