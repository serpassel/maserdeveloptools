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
import es.marser.generic.GenericFactory;
import es.marser.sqltools.CRUDHandler;
import es.marser.sqltools.DatabaseSettings;
import es.marser.sqltools.examples.ExampleObjectB;
import es.marser.sqltools.examples.ExampleObjectD;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by sergio on 16/10/17.
 */
@SuppressWarnings("unused")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CRUDHandlerTest2 {

    private CRUDHandler crudHandler;

    private int size;


    @Before
    public void setUp() throws Exception {
        Log.i(LOG_TAG.TAG, "BEFORE");
        /*Lectura de datos*/
        String reading = "M|1#\\1.01|1\\1\\|1|\\\\1\\\\\\\\|" +
                "~M|1#\\1.02|1\\2\\|4101.64|\\\\1.4\\623.35\\\\4.7\\|" +
                "~M|1#\\1.03|1\\3\\|74.18|\\muro zapata\\\\\\\\\\\\muro de 4,45\\1\\36.5\\1.35\\0.6\\\\muro de 3,60\\1\\18.5\\1.1\\0.6\\\\\\1\\19.55\\1.1\\0.6\\\\zapatas\\\\\\\\\\\\P5,P6,P7,P13,P14,P15\\6\\1.2\\1.2\\0.5\\\\P9,P10,P11\\3\\1.4\\1.4\\0.5\\\\P17,P18,P19\\3\\1\\1\\0.5\\\\arranque escaleras\\\\\\\\\\\\\\4\\0.45\\1.2\\0.5\\\\viga riostra\\1\\4.36\\0.3\\0.5\\\\Muro Mikel\\2\\3.1\\1.1\\0.6\\\\\\1\\7.45\\1.1\\0.6\\|" +
                "~M|1#\\1.04|1\\4\\|19.36|\\zanjas\\1\\77.45\\0.5\\0.5\\|" +
                "~M|1#\\1.05|1\\5\\|344.04|\\\\1\\73.2\\1\\4.7\\|" +
                "~M|2#\\2.01|2\\1\\|127.15|\\muro zapata\\\\\\\\\\\\muro de 4,45\\1\\36.5\\1.35\\\\\\muro de 3,60\\1\\18.5\\1.1\\\\\\\\1\\19.55\\1.1\\\\\\zapatas\\\\\\\\\\\\P5,P6,P7,P13,P14,P15\\6\\1.2\\1.2\\\\\\P9,P10,P11\\3\\1.4\\1.4\\\\\\P17,P18,P19\\3\\1\\1\\\\\\arranque escaleras\\\\\\\\\\\\\\4\\0.45\\1.2\\\\\\viga riostra\\1\\4.36\\0.3\\\\\\Muro Mikel\\2\\3.1\\1.1\\\\\\\\1\\7.45\\1.1\\\\|" +
                "~M|2#\\2.02|2\\2\\|61.47|\\muro zapata\\\\\\\\\\\\muro de 4,45\\1\\36.5\\1.35\\0.5\\\\muro de 3,60\\1\\18.5\\1.1\\0.5\\\\\\1\\19.55\\1.1\\0.5\\\\zapatas\\\\\\\\\\\\P5,P6,P7,P13,P14,P15\\6\\1.2\\1.2\\0.4\\\\P9,P10,P11\\3\\1.4\\1.4\\0.4\\\\P17,P18,P19\\3\\1\\1\\0.4\\\\arranque escaleras\\\\\\\\\\\\\\4\\0.45\\1.2\\0.4\\\\viga riostra\\1\\4.36\\0.3\\0.4\\\\Muro Mikel\\2\\3.1\\1.1\\0.5\\\\\\1\\7.45\\1.1\\0.5\\\\\\\\\\\\\\|" +
                "~M|2#\\2.03|2\\3\\|638.75|\\P.SOTANO\\1\\309.7\\\\\\\\RAMPA GARAJE\\1.2\\197\\\\\\\\terraza viv 2\\1\\14.8\\\\\\\\terraza viv 3\\1\\37.15\\\\\\\\terraza viv 4\\1\\21.55\\\\\\\\solera mikel\\1\\19.15\\\\\\|" +
                "~M|2#\\2.04|2\\4\\|638.75|\\P.SOTANO\\1\\309.7\\\\\\\\RAMPA GARAJE\\1.2\\197\\\\\\\\terraza viv 2\\1\\14.8\\\\\\\\terraza viv 3\\1\\37.15\\\\\\\\terraza viv 4\\1\\21.55\\\\\\\\solera mikel\\1\\19.15\\\\\\|" +
                "~M|2#\\2.05|2\\5\\|546.1|\\P.SOTANO\\1\\309.7\\\\\\\\RAMPA GARAJE\\1.2\\197\\\\\\|" +
                "~M|2#\\2.06|2\\6\\|98.45|\\terraza viv 1\\1\\37.35\\\\\\\\terraza viv 2\\1\\14.8\\\\\\\\terraza viv 3\\1\\24.75\\\\\\\\terraza viv 4\\1\\21.55\\\\\\|" +
                "~M|3#\\3.01|3\\1\\|87.91|\\MURO VIVIENDA\\\\\\\\\\\\muro sótano -1\\1\\17.61\\0.3\\3.6\\\\\\1\\15.14\\0.3\\3.6\\\\\\1\\5.26\\0.3\\3.6\\\\muro sótano -2\\1\\35.1\\0.3\\4.45\\|" +
                "~M|3#\\3.02|3\\2\\|9.9|\\forjado\\1\\16.95\\0.2\\1.4\\\\cubierta\\1\\17.15\\0.2\\1.5\\|" +
                "~M|3#\\3.03|3\\3\\|14.23|\\Planta Sótano\\12\\0.3\\0.3\\4.45\\\\\\8\\0.3\\0.3\\3.6\\\\Planta baja\\2\\0.3\\0.3\\3.65\\\\\\8\\0.3\\0.3\\2.9\\\\\\4\\0.3\\0.3\\2.9\\\\\\2\\0.3\\0.3\\3.65\\\\Planta entrecubierta\\4\\0.3\\0.3\\1.75\\\\\\4\\0.3\\0.3\\3.1\\\\\\4\\0.3\\0.3\\1.75\\|" +
                "~M|3#\\3.04|3\\4\\|359.12|\\VIVIENDA 1\\\\\\\\\\\\upn 120\\2\\3.35\\\\13.4\\\\VIVIENDA 2\\\\\\\\\\\\upn 120\\2\\3.35\\\\13.4\\\\VIVIENDA 3\\\\\\\\\\\\upn 120\\2\\3.35\\\\13.4\\\\VIVIENDA 4\\\\\\\\\\\\upn 120\\2\\3.35\\\\13.4\\|" +
                "~M|3#\\3.05|3\\5\\|8|\\VIVIENDA 1\\2\\\\\\\\\\VIVIENDA 2\\2\\\\\\\\\\VIVIENDA 3\\2\\\\\\\\\\VIVIENDA 4\\2\\\\\\\\|" +
                "~M|3#\\3.06|3\\6\\|236.96|\\Suelo planta baja VIV3-4\\1\\127.3\\\\\\\\a deducir escalera\\-2\\4.41\\\\\\\\Suelo planta baja VIV1-2\\1\\127.3\\\\\\\\a deducir escalera\\-2\\4.41\\\\\\|" +
                "~M|3#\\3.07|3\\7\\|137.3|\\Suelo planta entrecubierta VIV3-4\\1\\74.55\\\\\\\\a deducir escalera\\-2\\2.95\\\\\\\\suelo planta entrecubierta VIV1-2\\1\\74.55\\\\\\\\a deducir escalera\\-2\\2.95\\\\\\|" +
                "~M|3#\\3.08|3\\8\\|125.4|\\terraza\\1\\125.4\\\\\\|" +
                "~M|3#\\3.09|3\\9\\|274.4|\\VIVIENDAS 3-4\\1\\17.15\\8.05\\\\\\VIVIENDAS 1-2\\1\\17.15\\7.95\\\\|" +
                "~M|3#\\3.10|3\\10\\|59.29|\\puerta de garaje\\1\\9.3\\\\\\\\VIVIENDA 1\\\\\\\\\\\\ventanas\\1\\1.8\\\\\\\\\\1\\3.44\\\\\\\\\\1\\3.3\\\\\\\\\\1\\1.05\\\\\\\\puerta\\1\\1.22\\\\\\\\VIVIENDA 2\\\\\\\\\\\\ventanas\\1\\3.3\\\\\\\\\\1\\3.44\\\\\\\\\\1\\1.8\\\\\\\\\\1\\1.05\\\\\\\\\\1\\1.65\\\\\\\\puerta\\1\\1.22\\\\\\\\VIVIENDA 3\\\\\\\\\\\\ventanas\\1\\1.65\\\\\\\\\\1\\1.8\\\\\\\\\\1\\1.8\\\\\\\\\\1\\3.44\\\\\\\\\\1\\3.3\\\\\\\\\\1\\1.05\\\\\\\\puerta\\1\\1.22\\\\\\\\VIVIENDA 4\\\\\\\\\\\\ventanas\\1\\1.65\\\\\\\\\\1\\1.05\\\\\\\\\\1\\3.3\\\\\\\\\\1\\3.44\\\\\\\\\\1\\1.8\\\\\\\\puerta\\1\\1.22\\\\\\|" +
                "~M|3#\\3.11|3\\11\\|4|\\VIVIENDA 1\\\\\\\\\\\\Planta sótano-baja\\1\\\\\\\\\\VIVIENDA 2\\\\\\\\\\\\Planta sótano-baja\\1\\\\\\\\\\VIVIENDA 3\\\\\\\\\\\\Planta sótano-baja\\1\\\\\\\\\\VIVIENDA 4\\\\\\\\\\\\Planta sótano-baja\\1\\\\\\\\|" +
                "~M|3#\\3.12|3\\12\\|4|\\VIVIENDA 1\\\\\\\\\\\\Planta baja-entrecubierta\\1\\\\\\\\\\VIVIENDA 2\\\\\\\\\\\\Planta baja-entrecubierta\\1\\\\\\\\\\VIVIENDA 3\\\\\\\\\\\\Planta baja-entrecubierta\\1\\\\\\\\\\VIVIENDA 4\\\\\\\\\\\\Planta baja-entrecubierta\\1\\\\\\\\|" +
                "~M|3#\\3.13|3\\13\\|4|\\\\4\\\\\\\\|";
        String[] data = TextTools.getRecordSplit(reading, TextTools.REG_SEPARATOR);
        size = data.length;

        /*Conexión de datos*/
        DatabaseSettings databaseSettings = new DatabaseSettings();
        databaseSettings.setTables(ExampleObjectD.class);
        crudHandler = new CRUDHandler(InstrumentationRegistry.getTargetContext(), databaseSettings);
        // Log.i(LOG_TAG.TAG, "BEFORE");
        crudHandler.deleteDatabase();
        crudHandler.conectDatabase();

        Assert.assertTrue(crudHandler.isOpen());

        for (String reg : data) {
           // ExampleObjectD p0 = GenericFactory.BuildSingleObject(ExampleObjectD.class, reg);
            Assert.assertNull(crudHandler.addRecord(GenericFactory.BuildSingleObject(ExampleObjectD.class, reg)));
        }

        /*
        //Insertar registros
        ExampleObjectD p0 = GenericFactory.BuildSingleObject(ExampleObjectD.class, data[1]);
        ExampleObjectD p1 = GenericFactory.BuildSingleObject(ExampleObjectD.class, data[5]);
        ExampleObjectD p2 = GenericFactory.BuildSingleObject(ExampleObjectD.class, data[7]);
        ExampleObjectD p3 = GenericFactory.BuildSingleObject(ExampleObjectD.class, data[10]);
        ExampleObjectD p4 = GenericFactory.BuildSingleObject(ExampleObjectD.class, data[18]);

        Assert.assertNull(crudHandler.addRecord(p0));
        Assert.assertNull(crudHandler.addRecord(p1));
        Assert.assertNull(crudHandler.addRecord(p2));
        Assert.assertNull(crudHandler.addRecord(p3));
        Assert.assertNull(crudHandler.addRecord(p4));
        */
    }

    @After
    public void tearDown() throws Exception {
        Log.i(LOG_TAG.TAG, "AFTER");
        crudHandler.close();
    }

    @Test
    public void dTest() {
        Log.i(LOG_TAG.TAG, "dTest");
        //Lectura
        Assert.assertEquals(size, printlist(crudHandler.getAllRecords(ExampleObjectD.class)).size());
        Assert.assertEquals(6, crudHandler.findRecordsKeyStartWith("2", ExampleObjectD.class).size());
        Assert.assertEquals(4, crudHandler.findRecordsKeyEndWith("3", ExampleObjectD.class).size());
        Assert.assertEquals(3, crudHandler.findRecordsKeyContains(".04", ExampleObjectD.class).size());


        List<ExampleObjectD> l = crudHandler.findRecordsKeyContains(".04", ExampleObjectD.class);

        for (ExampleObjectD e : l) {
            Log.w(LOG_TAG.TAG, "Registro " + e.toString());
        }
    }

    private <T> List<T> printlist(List<T> list) {
        for (T pe : list) {
            Log.d(LOG_TAG.TAG, "Registro " + pe.toString());
        }
        return list;
    }

    @Test
    public void bTest() {
        Log.i(LOG_TAG.TAG, "bTest");

        crudHandler.close();

        DatabaseSettings databaseSettings = new DatabaseSettings();
        databaseSettings.setTables(ExampleObjectB.class);
        crudHandler = new CRUDHandler(InstrumentationRegistry.getTargetContext(), databaseSettings);
        crudHandler.conectDatabase();
        Assert.assertTrue(crudHandler.isOpen());
        Assert.assertEquals(size, printlist(crudHandler.getAllRecords(ExampleObjectB.class)).size());


    }

    @Test
    public void countTest(){
        crudHandler.close();
        DatabaseSettings databaseSettings = new DatabaseSettings();
        databaseSettings.setTables(ExampleObjectB.class);
        crudHandler = new CRUDHandler(InstrumentationRegistry.getTargetContext(), databaseSettings);
        crudHandler.conectDatabase();

        Assert.assertEquals(size, crudHandler.countAll(ExampleObjectB.class));

        Log.w(LOG_TAG.TAG, "Datos " + crudHandler.countAll(ExampleObjectB.class));

    }
}