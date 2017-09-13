package sinkmyship.players;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;
import sinkmyship.IntegrationTest;
import sinkmyship.common.Serializer;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class RegisterPlayerHandlerIT extends IntegrationTest {

    @Test
    public void testTrue() throws Exception {
        String url = "https://y8va9fwva2.execute-api.us-east-1.amazonaws.com/dev/players/RegisterPlayer";
        RegisterPlayer command = new RegisterPlayer("abc123", "John", "Doe", "wmfoody+jdoe@gmail.com");
        String json = Serializer.serialize(command);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String expected = "{\"id\":\"abc123\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"emailAddress\":\"wmfoody+jdoe@gmail.com\"}";
        assertEquals(expected, response.body().string());
    }

}