package es.marser.maserdeveloptools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.util.SparseIntArray;

import org.junit.Test;
import org.junit.runner.RunWith;

import es.marser.LOG_TAG;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("unused")
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("es.marser.maserdeveloptools", appContext.getPackageName());
    }

    @Test
    public void sparseInt() {
        SparseIntArray types = new SparseIntArray();

        for (int i = 0; i < 5; ++i) {
            types.append(i, 10);
        }

        Log.d(LOG_TAG.TAG,"Types size " + types.size());

        for (int i = 0; i < 5; ++i) {
            types.append(i, 9);
        }

        Log.d(LOG_TAG.TAG, "Types size " + types.size());
    }
}
