package sinkmyship.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.vavr.control.Try;
import io.vavr.jackson.datatype.VavrModule;

/**
 *
 */
public class Serializer {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        objectMapper.registerModule(new VavrModule());
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        objectMapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NON_PRIVATE);
    }

    public static String serialize(Object any) {
        return Try.of(() -> objectMapper.writeValueAsString(any)).get();
    }

    public static <R> Try<R> deserialize(String json, Class<R> klass) {
        return Try.of(() -> objectMapper.readValue(json, klass));
    }

    public static <R> Try<R> deserialize(String json, TypeReference<R> typeReference) {
        return Try.of(() -> objectMapper.readValue(json, typeReference));
    }

}