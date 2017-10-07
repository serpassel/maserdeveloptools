package es.marser.backgroundtools;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("unused")
public class ExampleUnitTest {
    @SuppressWarnings("WeakerAccess")
    public enum extras {
        INDEX_EXTRA,
        OBJECT_EXTRA,
        THROWABLE_EXTRA,
        MESSAGE_EXTRA
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void enum_() {
        System.out.println(String.valueOf(extras.INDEX_EXTRA));
        System.out.println(extras.INDEX_EXTRA.name());
        System.out.println(extras.INDEX_EXTRA.ordinal());
        Assert.assertEquals("INDEX_EXTRA", String.valueOf(extras.INDEX_EXTRA));
        Assert.assertEquals("INDEX_EXTRA", extras.INDEX_EXTRA.name());
        Assert.assertEquals(0, extras.INDEX_EXTRA.ordinal());
    }
}