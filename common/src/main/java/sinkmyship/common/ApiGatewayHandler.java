package sinkmyship.common;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Collections;

import static sinkmyship.common.Requirements.require;

/**
 *
 */
public abstract class ApiGatewayHandler<Message, Response> implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    protected final Logger LOG = Logger.getLogger(getClass());

    private final Class<Message> messageClass;

    public ApiGatewayHandler(Class<Message> messageClass) {
        require(messageClass != null, "messageClass is required");
        this.messageClass = messageClass;
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest request, Context context) {
        return deserialize(request).map(this::processMessage).get();
    }

    public Either<AwsProxyResponse, Message> deserialize(AwsProxyRequest request) {
        return Serializer.deserialize(request.getBody(), messageClass)
                .toEither()
                .mapLeft(throwable -> {
                    return new AwsProxyResponse(400, Collections.emptyMap(), throwable.getMessage());
                });
    }

    public AwsProxyResponse processMessage(Message message) {
        return Try.of(() -> handle(message))
                .flatMap(response -> response)
                .map(Serializer::serialize)
                .map(response -> {
                    return new AwsProxyResponse(200, Collections.emptyMap(), response);
                })
                .getOrElseGet(throwable -> {
                    LOG.error("Error processing " + message, throwable);
                    return new AwsProxyResponse(500, Collections.emptyMap(), throwable.getMessage());
                });
    }

    public abstract Try<Response> handle(Message message);

}