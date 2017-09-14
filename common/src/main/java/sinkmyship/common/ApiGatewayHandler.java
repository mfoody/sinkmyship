package sinkmyship.common;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static sinkmyship.common.Requirements.require;

/**
 *
 */
public abstract class ApiGatewayHandler<Message, Response> implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    protected final Logger LOG = Logger.getLogger(getClass());

    private final Map<String, String> headers;

    private final Class<Message> messageClass;

    public ApiGatewayHandler(Class<Message> messageClass) {
        this(messageClass, true);
    }

    public ApiGatewayHandler(Class<Message> messageClass, boolean enableCors) {
        require(messageClass != null, "messageClass is required");
        this.messageClass = messageClass;
        this.headers = new HashMap<>();

        if (enableCors) {
            headers.put("Access-Control-Allow-Origin", "*");
            headers.put("Access-Control-Allow-Credentials", "true");
        }
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest request, Context context) {
        Either<AwsProxyResponse, AwsProxyResponse> response = deserialize(request).map(this::processMessage);
        return response.getOrElseGet(Function.identity());
    }

    public Either<AwsProxyResponse, Message> deserialize(AwsProxyRequest request) {
        return Serializer.deserialize(request.getBody(), messageClass)
                .toEither()
                .mapLeft(throwable -> invalidRequestResponse(request, throwable));
    }

    public AwsProxyResponse processMessage(Message message) {
        return Try.of(() -> handle(message))
                .flatMap(Function.identity())
                .map(response -> {
                    if (response instanceof Option && ((Option) response).isEmpty()) {
                        return notFoundResponse(message);
                    } else if (response instanceof Validation) {
                        Validation<?, Response> validation = (Validation<?, Response>) response;
                        return validation
                                .map(value -> successResponse(message, value))
                                .getOrElseGet(error -> unprocessableResponse(message, error));
                    } else {
                        return successResponse(message, response);
                    }
                })
                .getOrElseGet(throwable -> errorResponse(message, throwable));
    }

    public AwsProxyResponse successResponse(Message message, Response response) {
        String body = Serializer.serialize(response);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Successful response: " + body);
        }
        return new AwsProxyResponse(200, headers, body);
    }

    public AwsProxyResponse invalidRequestResponse(AwsProxyRequest request, Throwable throwable) {
        LOG.warn("Invalid request: " + throwable.getMessage());
        return new AwsProxyResponse(400, headers, throwable.getMessage());
    }

    public <E> AwsProxyResponse unprocessableResponse(Message message, E validations) {
        LOG.warn("Cannot process: " + message);
        LOG.warn("Returned validations: " + validations);

        String body = Serializer.serialize(validations);
        return new AwsProxyResponse(402, headers, body);
    }

    public AwsProxyResponse notFoundResponse(Message message) {
        LOG.warn("Not found: " + message);
        return new AwsProxyResponse(404, headers, "");
    }

    public AwsProxyResponse errorResponse(Message message, Throwable throwable) {
        LOG.error("Error Handling " + message, throwable);
        return new AwsProxyResponse(500, headers, throwable.getMessage());
    }

    public abstract Try<Response> handle(Message message);

}