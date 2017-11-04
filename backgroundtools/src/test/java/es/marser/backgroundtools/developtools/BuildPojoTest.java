package es.marser.backgroundtools.developtools;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author sergio
 *         Created by sergio on 21/10/17.
 */
@SuppressWarnings("unused")
public class BuildPojoTest {

    @Test
    public void buildPruebaObject() {
        String tablename = "P";

        String nameclass = "Prueba";

        BuildPojo.FieldBuilder[] list = {
                BuildPojo.newfb("field1", String.class),
                BuildPojo.newfb("date1", Date.class),
                BuildPojo.newfb("num1", BigDecimal.class),
                BuildPojo.newfb("num2", int.class),
                BuildPojo.newfb("flag1", boolean.class),
                BuildPojo.newfb("obj1", Object.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename, nameclass, list);
        buildPojo.print(true);
    }

    @Test
    public void buildCalendarModel() {
        String tablename = "P";

        String nameclass = "CalendarObservable";

        BuildPojo.FieldBuilder[] list = {
                BuildPojo.newfb("holiday", boolean.class),
                BuildPojo.newfb("othermonth", boolean.class),
                BuildPojo.newfb("otherholiday", int.class),
                BuildPojo.newfb("calendar", GregorianCalendar.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename, nameclass, list);
        buildPojo.print(true);
    }

    @Test
    public void buildAutonomousModel() {
        //  (CCAA) ID | CODAUTO | NOMBRE|PRO_NUM|
        String tablename = "CCAA";

        String nameclass = "AutonomousModel";

        BuildPojo.FieldBuilder[] list = {
                BuildPojo.newfb("codauto", int.class),
                BuildPojo.newfb("name", String.class), BuildPojo.newfb("provincesCount", int.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename, nameclass, list);
        buildPojo.print(true);
    }

    @Test
    public void buildProvincieModel() {
        // ID | CODAUTO | CPRO | NOMBRE | MUN_COUNT|
        String tablename = "PRO";

        String nameclass = "ProvincieModel";

        BuildPojo.FieldBuilder[] list = {
                BuildPojo.newfb("codauto", int.class),
                BuildPojo.newfb("cpro", int.class),
                BuildPojo.newfb("name", String.class),
                BuildPojo.newfb("villagesCount", int.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename, nameclass, list);
        buildPojo.print(true);
    }

    @Test
    public void buildVillageModel(){
        // (MUN) ID | CODAUTO | CPRO |CMUN | DC | NOMBRE
        String tablename = "MUN";

        String nameclass = "VillageModel";

        BuildPojo.FieldBuilder[] list = {
                BuildPojo.newfb("codauto", String.class),
                BuildPojo.newfb("cpro", String.class),
                BuildPojo.newfb("cmun", String.class),
                BuildPojo.newfb("dc", String.class),
                BuildPojo.newfb("name", String.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename, nameclass, list);
        buildPojo.print(true);
    }
}