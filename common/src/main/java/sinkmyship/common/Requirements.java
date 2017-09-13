package sinkmyship.common;

/**
 *
 */
public class Requirements {

    private final static Object[] EMPTY_ARGS = new Object[0];

    public static void require(boolean condition, String message) {
        require(condition, message, EMPTY_ARGS);
    }

    public static void require(boolean condition, String message, Object... args) {
        if (!condition) {
            String formatted = String.format(message, args);
            throw new IllegalArgumentException(formatted);
        }
    }

    public static boolean nonEmpty(String value) {
        return !(value == null || value.isEmpty());
    }
}