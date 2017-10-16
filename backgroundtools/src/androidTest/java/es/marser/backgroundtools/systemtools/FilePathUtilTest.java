package es.marser.backgroundtools.systemtools;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import es.marser.LOG_TAG;

/**
 * @author sergio
 *         Created by sergio on 7/10/17.
 */
@SuppressWarnings({"unused", "CanBeFinal"})
@RunWith(AndroidJUnit4.class)
public class FilePathUtilTest {

    private String[] exts = new String[]{".txt", ".jpg", ".png", ".dwg", ".xls", ".odt", ".mp4", ".doc", "", ".bc3"};

    @Test
    public void channel2() {
        Assert.assertNotEquals(null, FilePathUtil.getAndroidPath());
        Assert.assertNotEquals(null, FilePathUtil.getDownloadPath());
    }

    @Test
    public void channel3() {
        File file = new File(FilePathUtil.getAndroidPath(), "PRUEBA.txt");
        Assert.assertEquals(FilePathUtil.getFileSimpleName(FilePathUtil.AutoRenameFile(file)), "PRUEBA_(00)");
        Assert.assertEquals(FilePathUtil.getExtension(file), "txt");
    }

    @Test
    public void channel4() {
        for (String s : exts) {
            Log.i(LOG_TAG.TAG, "MymeType for " + s + ": " + FilePathUtil
                    .getMimeType(
                            new File(FilePathUtil.getAndroidPath(), "PRUEBA" + s))
            );
        }
    }
}