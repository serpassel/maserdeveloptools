package es.marser.maserdeveloptools.sqltools;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import es.marser.LOG_TAG;
import es.marser.sqltools.CRUDHandler;
import es.marser.sqltools.DatabaseSettings;
import es.marser.sqltools.examples.PojoExample;


/**
 * @author sergio
 *         Created by sergio on 16/10/17.
 */
@SuppressWarnings("unused")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CRUDHandlerTest {

    private CRUDHandler crudHandler;
    private PojoExample p1;
    private PojoExample p2;


    @Before
    public void setUp() throws Exception {
        DatabaseSettings databaseSettings = new DatabaseSettings();
        databaseSettings.setTables(PojoExample.class);
        crudHandler = new CRUDHandler(InstrumentationRegistry.getTargetContext(), databaseSettings);
        // Log.i(LOG_TAG.TAG, "BEFORE");
        crudHandler.deleteDatabase();
        crudHandler.conectDatabase();

        Assert.assertTrue(crudHandler.isOpen());

        //Insertar registros
        p1 = new PojoExample("key1", "P1", "10");
        p2 = new PojoExample("key2", "P2", "10");
        PojoExample p3 = new PojoExample("key3", "P3", "10");

        Assert.assertNull(crudHandler.addRecord(p1));
        Assert.assertNull(crudHandler.addRecord(p2));
        Assert.assertNull(crudHandler.addRecord(p3));


    }

    @After
    public void tearDown() throws Exception {
        crudHandler.close();
    }

    @Test
    public void synchronous() {
        //Lectura
        Assert.assertEquals(3, printlist(crudHandler.getAllRecords(PojoExample.class)).size());

        //Eliminación
        Assert.assertNull(crudHandler.delRecord(p2));
        //Lectura
        Assert.assertEquals(2, printlist(crudHandler.getAllRecords(PojoExample.class)).size());
        //Búsqueda
        Assert.assertEquals("key1", crudHandler.findRecordByKey(p1.getKey(), PojoExample.class).getKey());

        //Actualización
        p1.setName("newP1");
        Assert.assertNull(crudHandler.updateRecord(p1));
    }

    private List<PojoExample> printlist(List<PojoExample> list) {
        for (PojoExample pe : list) {
            Log.d(LOG_TAG.TAG, "Registro " + pe.toString());
        }
        return list;
    }

    @Test
    public void asynchronous() {
        //Assert.assertEquals(3, printlist(crudHandler.getAllRecords(PojoExample.class)).size());

        crudHandler.getAllRecords(PojoExample.class, new CRUDHandler.OnRead<PojoExample>() {
            @Override
            public void onStart(Void start) {
            }

            @Override
            public void onUpdate(PojoExample update) {
                Log.d(LOG_TAG.TAG, " Entrada " + update.toString());
            }

            @Override
            public void onFinish(Void finish) {
                Log.d(LOG_TAG.TAG, " FINALIZACIÓN");
             }

            @Override
            public void onFailure(Throwable e) {
                Log.d(LOG_TAG.TAG, " Failure " + e.getMessage());
            }
        });
    }

}