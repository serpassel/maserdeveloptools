package es.marser.backgroundtools.developtools;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import es.marser.tools.SystemColor;

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
    public void buildVillageModel() {
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

    @Test
    public void buildBoxSettings() {

        String nameclass = "BoxSettings";

        BuildPojo.FieldBuilder[] list = {
                /*edit*/
                BuildPojo.newfb("inputType", int.class),
                BuildPojo.newfb("lines", int.class),
                /*layout*/
                BuildPojo.newfb("error", boolean.class),
                BuildPojo.newfb("errorText", String.class),
                BuildPojo.newfb("password", boolean.class),
                BuildPojo.newfb("passwordCount", int.class),
                BuildPojo.newfb("counterCount", int.class),
                BuildPojo.newfb("counter", boolean.class),
                BuildPojo.newfb("hint", String.class)
        };

        BuildPojo buildPojo = new BuildPojo(null, nameclass, list);
        buildPojo.print(true);
    }

    @Test
    public void buildHolidayModel() {

        // (MUN) ID | DAY | autonomics{\...\....\} |TYPE |
        String tablename = "HA";

        String classpath = "/home/sergio/Dropbox/MaserDevelopTools/backgroundtools";
        String path = "dialogs/model";
        String packagen2 = "es.marser.backgroundtools";

        String nameclass = "HolidayModel";

        BuildPojo.FieldBuilder[] list = {

                BuildPojo.newfb("codigo", String.class),
                BuildPojo.newfb("lines", String.class),
                BuildPojo.newfb("type", int.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename, nameclass, list);
        System.out.println(SystemColor.ANSI_GREEN+ buildPojo.writeClass(classpath, packagen2, buildPojo.print(true), path, nameclass));
    }

    @Test
    public void buildMonthModel() {

        String tablename = null;

        String classpath = "/home/sergio/Dropbox/MaserDevelopTools/backgroundtools";
        String path = "dialogs/model";
        String packagen2 = "es.marser.backgroundtools";

        String nameclass = "MonthTitle";

        BuildPojo.FieldBuilder[] list = {

                BuildPojo.newfb("name", String.class)
        };

        BuildPojo buildPojo = new BuildPojo(null, nameclass, list);
        System.out.println(SystemColor.ANSI_GREEN+ buildPojo.writeClass(classpath, packagen2, buildPojo.print(true), path, nameclass));
    }
}