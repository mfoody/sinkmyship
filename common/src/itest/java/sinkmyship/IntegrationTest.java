package sinkmyship;

import okhttp3.*;
import sinkmyship.common.Serializer;

import java.util.function.Function;

/**
 *
 */
public abstract class IntegrationTest {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected final OkHttpClient client = new OkHttpClient();

    public String url(String path) {
        return String.format("https://api.sinkmyshipgame.com/%s", path);
    }

    public <C> Response post(C command, String url) throws Exception {
        return execute(command, body -> new Request.Builder().url(url).post(body).build());
    }

    public <C> Response put(C command, String url) throws Exception {
        return execute(command, body -> new Request.Builder().url(url).put(body).build());
    }

    private <C> Response execute(C command, Function<RequestBody, Request> f) throws Exception {
        String json = Serializer.serialize(command);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = f.apply(body);
        return client.newCall(request).execute();
    }
}