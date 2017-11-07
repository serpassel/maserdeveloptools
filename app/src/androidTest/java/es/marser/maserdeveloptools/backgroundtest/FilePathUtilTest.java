package es.marser.maserdeveloptools.backgroundtest;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.systemtools.FilePathUtil;

/**
 * @author sergio
 *         Created by sergio on 7/10/17.
 */
@LargeTest
@SuppressWarnings({"unused", "CanBeFinal"})
@RunWith(AndroidJUnit4.class)
public class FilePathUtilTest {

    private String[] exts;

    @Before
    public void init(){
        exts = new String[]{".txt", ".jpg", ".png", ".dwg", ".xls", ".odt", ".mp4", ".doc", "", ".bc3"};
    }

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
    @Test
    public void channel5(){
        File[] list = FilePathUtil.getUnSyncFiles(FilePathUtil.getDownloadPath(), null);
        for(File f: list){
            Log.d(LOG_TAG.TAG,"Archivos " + f.getAbsolutePath());
        }
    }

    @Test
    public void channel6(){
        File[] list = FilePathUtil.getUnSyncFiles(FilePathUtil.getDownloadPath(), new String[]{".txt", ".jpg"});
        for(File f: list){
            Log.i(LOG_TAG.TAG,"Archivos " + f.getAbsolutePath());
        }

    }
}