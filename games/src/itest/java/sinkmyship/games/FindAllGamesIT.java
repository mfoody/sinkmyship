package sinkmyship.games;

import okhttp3.Response;
import org.junit.Test;
import sinkmyship.IntegrationTest;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class FindAllGamesIT extends IntegrationTest {

    @Test
    public void test() throws Exception {
        FindAllGames query = ImmutableFindAllGames.builder().build();
        Response response = post(query, url("games/FindAllGames"));
        assertEquals(response.message(), 200, response.code());
    }
}
