package es.marser;

/**
 * @author sergio
 *         Created by sergio on 7/10/17.
 */

public class LOG_TAG {
    public static final String TAG = "MARSER_TAG";

    public static <T> T assertNotNull(T object) {
        if (object == null)
            throw new AssertionError("Object cannot be null");
        return object;
    }
}
