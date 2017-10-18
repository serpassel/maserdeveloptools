package es.marser.generic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.marser.examples.ExampleItem;
import es.marser.examples.ExampleObject;
import es.marser.tools.SystemColor;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 17/10/17.
 */
public class GenericFactoryTest {
    private String[] data;
    private String m1, m2,m3, m4;

    @Before
    public void setUp() throws Exception {
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
        data = TextTools.getRecordSplit(reading, TextTools.REG_SEPARATOR);


        /*Comprobar valores de ejemplo*/
        Assert.assertEquals(data.length, 24);
        Assert.assertEquals(data[0], "M|1#\\1.01|1\\1\\|1|\\\\1\\\\\\\\|");
        Assert.assertEquals(data[23], "M|3#\\3.13|3\\13\\|4|\\\\4\\\\\\\\|");
        Assert.assertEquals(data[11], "M|3#\\3.01|3\\1\\|87.91|\\MURO VIVIENDA\\\\\\\\\\\\muro sótano -1\\1\\17.61\\0.3\\3.6\\\\\\1\\15.14\\0.3\\3.6\\\\\\1\\5.26\\0.3\\3.6\\\\muro sótano -2\\1\\35.1\\0.3\\4.45\\|");

        /*Malformados*/
        m1 = "M|1#\\1.01|1\\1\\|1|\\\\1\\\\\\\\| | | | | | |";//Mayor número de campos
        m2 = "M|1#\\1.01|1";//Menor número de campos
        m3 = "0\\muro sótano -1\\1\\17,61\\0,3\\3,6\\";//Malformación numérica
        m4 = "0\\muro sótano -1\\1\\numero\\0,3\\3,6\\";//Error de clases

    }

    @Test
    public void channel1() {
        /*Creación de registro [EN]  Registry Creation */
        ExampleObject o1 = GenericFactory.BuildSingleObject(ExampleObject.class, data[11]);
        Assert.assertEquals(o1.toString().substring(1), data[11] + TextTools.RETORNO_CARRO_SALTO_LINEA);
        /*Número de campos leíbles en el registro padre [EN]  Number of readable fields in the parent record*/
        Assert.assertEquals(4, GenericFactory.getReadableColumnsCount(ExampleObject.class));

        /*Sub-campos leíbles [EN]  Readable sub-fields*/
        Assert.assertEquals(6, GenericFactory.getReadableColumnsCount(ExampleItem.class));

        int occurrences = TextTools.charOccurrences(o1.getLines(), TextTools.ITEM_SEPARATOR_CHAR);
        int regs = occurrences / GenericFactory.getReadableColumnsCount(ExampleItem.class);

        /*Número de sub-registros [EN]  Number of sub-registers*/
        Assert.assertEquals(GenericFactory.itemsBuilder(ExampleItem.class, o1.getLines(), null).size(), regs);
    }

    @Test
    public void channel2() {
        //Bugs Control

        /*Formación de registros vacíos [EN]  Formation of empty registers*/
        ExampleObject o1 = GenericFactory.BuildSingleObject(ExampleObject.class, null);
        Assert.assertNull(o1);
        ExampleObject o2 = GenericFactory.BuildSingleObject(ExampleObject.class, "");
        Assert.assertNull(o2);

        /*Entradas vacías [EN]  Empty entries*/
        Assert.assertEquals(0, TextTools.getRecordSplit("", null).length);
        Assert.assertEquals(0, TextTools.getRecordSplit(null, null).length);
        Assert.assertEquals(0, TextTools.getRecordSplit("", "").length);


        /*Registro con más campos que los esperados [EN]  Register with more fields than expected*/
        Assert.assertNotNull(GenericFactory.BuildSingleObject(ExampleObject.class, m1));
        Assert.assertEquals(
                GenericFactory.BuildSingleObject(ExampleObject.class, m1).toString(),
                GenericFactory.BuildSingleObject(ExampleObject.class,data[0]).toString()
        );

        Assert.assertEquals("0\\1.01|1\\0.0\\0.0\\0.0\\0.0\\", GenericFactory.BuildSingleItem(ExampleItem.class, m2).toString());
        Assert.assertEquals("0\\muro sótano -1\\1.0\\17.61\\0.3\\3.6\\", GenericFactory.BuildSingleItem(ExampleItem.class, m3).toString());
        Assert.assertEquals("0\\muro sótano -1\\1.0\\0.0\\0.3\\3.6\\", GenericFactory.BuildSingleItem(ExampleItem.class, m4).toString());

/*
        System.out.println("M1      " + GenericFactory.BuildSingleObject(ExampleObject.class, m1).toString());
        System.out.println("data[0] " + GenericFactory.BuildSingleObject(ExampleObject.class,data[0]).toString());
        System.out.println("M2      " + GenericFactory.BuildSingleObject(ExampleObject.class, m2).toString());

        System.out.println("lines " + GenericFactory.BuildSingleObject(ExampleObject.class, data[11]).getLines());
        for(ExampleItem ei: GenericFactory.itemsBuilder(
                ExampleItem.class,
                GenericFactory.BuildSingleObject(ExampleObject.class, data[11]).getLines(), null)){
            System.out.println("sub-reg " + ei.toString());
        }
*/
    }
}