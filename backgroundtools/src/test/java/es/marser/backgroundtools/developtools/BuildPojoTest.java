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

        BuildPojo buildPojo = new BuildPojo(tablename,nameclass,list);
        buildPojo.print(true);
    }

    @Test
    public void buildCalendarModel(){
        String tablename = "P";

        String nameclass = "CalendarObservable";

        BuildPojo.FieldBuilder[] list = {
                BuildPojo.newfb("holiday", boolean.class),
                BuildPojo.newfb("othermonth", boolean.class),
                BuildPojo.newfb("otherholiday", int.class),
                BuildPojo.newfb("calendar", GregorianCalendar.class)
        };

        BuildPojo buildPojo = new BuildPojo(tablename,nameclass,list);
        buildPojo.print(true);
    }

}