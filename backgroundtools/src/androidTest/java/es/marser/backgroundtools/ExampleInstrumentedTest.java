package es.marser.backgroundtools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.systemtools.FilePathUtil;

import static org.junit.Assert.assertEquals;

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

        assertEquals("es.marser.backgroundtools.test", appContext.getPackageName());
    }

    @Test
    public void FilePath() {
        for (File f : FilePathUtil.getUnSyncFiles(FilePathUtil.getAndroidPath(), null)) {
            if (!f.isHidden()) {
                if (f.isDirectory()) {
                        Log.i(LOG_TAG.TAG, "Directorio " + f.getAbsolutePath());
                } else if (f.isFile()) {
                        Log.i(LOG_TAG.TAG, "Archivo " + f.getAbsolutePath());
                }
            }
        }
    }

    @Test
    public void FileAbsolute() {
        Log.i(LOG_TAG.TAG, "es absoluto " + FilePathUtil.getAndroidPath().isAbsolute());
        Log.i(LOG_TAG.TAG, "es absoluto " + new File("/").isAbsolute());
        Log.i(LOG_TAG.TAG, "es absoluto " + new File("/storage/emulated/0/495999632").isAbsolute());
    }

    @Test
    public void FileArchivePath() {
        File ar = new File("/storage/emulated/0/495999632");

        for (File f : FilePathUtil.getUnSyncFiles(ar.getParentFile(), null)) {
            if (f.isDirectory()) {
                if (f.isHidden()) {
                    Log.w(LOG_TAG.TAG, "Directorio " + f.getAbsolutePath());
                } else {
                    Log.i(LOG_TAG.TAG, "Directorio " + f.getAbsolutePath());
                }
            } else if (f.isFile()) {
                if (f.isHidden()) {
                    Log.w(LOG_TAG.TAG, "Archivo " + f.getAbsolutePath());
                } else {
                    Log.i(LOG_TAG.TAG, "Archivo " + f.getAbsolutePath());
                }
            }

        }
    }

    @Test
    public void FileNullPath() {
        File ar = new File("/&&&&&&/&&&&&&&&&");

        for (File f : FilePathUtil.getUnSyncFiles(ar, null)) {
            if (f.isDirectory()) {
                if (f.isHidden()) {
                    Log.w(LOG_TAG.TAG, "Directorio " + f.getAbsolutePath());
                } else {
                    Log.i(LOG_TAG.TAG, "Directorio " + f.getAbsolutePath());
                }
            } else if (f.isFile()) {
                if (f.isHidden()) {
                    Log.w(LOG_TAG.TAG, "Archivo " + f.getAbsolutePath());
                } else {
                    Log.i(LOG_TAG.TAG, "Archivo " + f.getAbsolutePath());
                }
            }

        }
    }
}
