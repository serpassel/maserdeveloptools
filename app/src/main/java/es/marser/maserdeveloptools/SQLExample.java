package es.marser.maserdeveloptools;

import android.content.Context;

import junit.framework.Assert;

import es.marser.sqltools.CRUDHandler;
import es.marser.sqltools.DatabaseSettings;
import es.marser.sqltools.examples.PojoExample;
import es.marser.tools.DateTools;

/**
 * @author sergio
 *         Created by sergio on 16/10/17.
 *         Manejo de datos
 *         <p>
 *         [EN]  Data management
 */

@SuppressWarnings("unused")
public class SQLExample {

    public static void createDatabase(Context context) {
        CRUDHandler crudHandler;
        DatabaseSettings databaseSettings = new DatabaseSettings();
        databaseSettings.setTables(PojoExample.class);
        crudHandler = new CRUDHandler(context, databaseSettings);
        crudHandler.deleteDatabase();
        crudHandler.conectDatabase();

        PojoExample p1, p2, p3;
        p1 = new PojoExample(DateTools.getTimeStamp(), "P1", "10");
        p2 = new PojoExample(DateTools.getTimeStamp(), "P2", "10");
        p3 = new PojoExample(DateTools.getTimeStamp(), "P3", "10");

        Assert.assertNull(crudHandler.addRecord(p1));
        Assert.assertNull(crudHandler.addRecord(p2));
        Assert.assertNull(crudHandler.addRecord(p3));
    }
}
