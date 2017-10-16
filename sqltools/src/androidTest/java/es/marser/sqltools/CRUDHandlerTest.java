package es.marser.sqltools;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.marser.LOG_TAG;
import es.marser.sqltools.examples.PojoExample;
import es.marser.tools.DateTools;

/**
 * @author sergio
 *         Created by sergio on 16/10/17.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CRUDHandlerTest {

    private CRUDHandler crudHandler;

    @Before
    public void setUp() throws Exception {
        DatabaseSettings databaseSettings = new DatabaseSettings();
        databaseSettings.setTables(PojoExample.class);
        crudHandler = new CRUDHandler(InstrumentationRegistry.getTargetContext(), databaseSettings);
        Log.i(LOG_TAG.TAG, "BEFORE");
        crudHandler.deleteDatabase();
        crudHandler.conectDatabase();
    }

    @After
    public void tearDown() throws Exception {
        crudHandler.close();
    }

    @Test
    public void channel1() {
        Assert.assertTrue(crudHandler.isOpen());

        //Insertar registros
        PojoExample p1, p2, p3;
        p1 = new PojoExample(DateTools.getTimeStamp(), "P1", "10");
        p2 = new PojoExample(DateTools.getTimeStamp(), "P2", "10");
        p3 = new PojoExample(DateTools.getTimeStamp(), "P3", "10");

        Assert.assertNull(crudHandler.addRecord(p1));
        Assert.assertNull(crudHandler.addRecord(p2));
        Assert.assertNull(crudHandler.addRecord(p3));
    }

}