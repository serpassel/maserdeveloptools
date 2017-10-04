package es.marser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         [ES]
 *         Comprobación funcional de DateTools
 *         <p>
 *         [EN]
 *         DateTools Functional Check
 */
@SuppressWarnings("CanBeFinal")
public class DateToolsTest {

    private GregorianCalendar in = new GregorianCalendar();
    private GregorianCalendar in4 = new GregorianCalendar();
    private String timeMil;
    private String intimeformat = "12:20:01";
    private String insqlite = "2010-01-01 " + intimeformat;
    private GregorianCalendar in2 = new GregorianCalendar();
    private GregorianCalendar in3 = new GregorianCalendar();
    private GregorianCalendar in5 = new GregorianCalendar();
    private GregorianCalendar in6 = new GregorianCalendar();
    private GregorianCalendar in7 = new GregorianCalendar();
    private GregorianCalendar in8 = new GregorianCalendar();
    private GregorianCalendar in9 = new GregorianCalendar();
    private GregorianCalendar in10 = new GregorianCalendar();
    private GregorianCalendar in11 = new GregorianCalendar();
    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");

    @Before
    public void init() {
        in.set(2010, 0, 1, 12, 20, 1);//01-ene-2010 12:20:01
        in4.set(2010, 0, 1, 0, 0, 0);//01-ene-2010 00:00:00

        timeMil = in.getTimeInMillis() + "";

        in2.set(2011, 8, 15, 14, 21, 1);//15-sep-2011 14:21:01
        in3.set(2011, 8, 15, 14, 27, 1);//15-sep-2011 14:27:01

        in5.set(2011, 8, 1, 0, 0, 0);//01-sep-2011 00:00:00
        in6.set(2011, 8, 30, 0, 0, 0);//01-sep-2011 00:00:00

        in7.set(2017, 8, 1, 0, 0, 0);//01-sep-2011 00:00:00
        in8.set(2017, 8, 30, 0, 0, 0);//01-sep-2011 00:00:00
        in9.set(2017, 8, 15, 0, 0, 0);//01-sep-2011 00:00:00
        in10.set(2017, 5, 15, 0, 0, 0);//01-sep-2011 00:00:00
        in11.set(2017, 9, 15, 0, 0, 0);//01-sep-2011 00:00:00
    }

    /**
     * 1.- Marca temporal. Creación de marcas temporales con diferentes formatos
     * [EN] 1.- Time Stamp. Creation of temporary marks with different formats
     */
    @Test
    public void channel1() {
        String informat1 = "01-ene-2010 12:20:01";
        Assert.assertEquals(true, es.marser.tools.DateTools.getRef(in.getTimeInMillis() + "").equals(
                "ID: " +
                        in.getTimeInMillis() +
                        "  " + informat1));
        Assert.assertEquals(true, es.marser.tools.DateTools.getDateTime(timeMil).equals(informat1));
        String indateformat = "01-ene-2010";
        Assert.assertEquals(true, es.marser.tools.DateTools.getDate(timeMil).equals(indateformat));
        Assert.assertEquals(true, es.marser.tools.DateTools.getTime(timeMil).equals(intimeformat));
    }

    /**
     * 2.- Formateo especial. Formateo para Bases de datos y archivos específicos
     * [EN] 2.- Special formatting.  Formatting for specific databases and files
     */
    @Test
    public void channel2() {
        Assert.assertEquals(true, es.marser.tools.DateTools.SQLiteDate(es.marser.tools.DateTools.setTimeMilis(timeMil)).equals(insqlite));
        String inbc3 = "01012010";
        Assert.assertEquals(true, es.marser.tools.DateTools.formatBC3Date(in).equals(inbc3));
        Assert.assertEquals(true, sdf.format(es.marser.tools.DateTools.parseShortDate("01012010").getTime()).equals(sdf.format(in4.getTime())));
        Assert.assertEquals(true, sdf.format(es.marser.tools.DateTools.parseShortDate("010110").getTime()).equals(sdf.format(in4.getTime())));
    }

    /**
     * 3.- Formateo estandar.
     * [EN]  3.- Standard formatting.
     */
    @Test
    public void channel3() {
        Assert.assertEquals(true, es.marser.tools.DateTools.formatShortDate(in).equals("01-01-2010"));
        Assert.assertEquals(true, es.marser.tools.DateTools.formatLongDate(in).equals("01 de enero de 2010"));
    }

    /**
     * 4.- Cálculos con fechas.
     * [EN] 4.- Calculations with dates.
     */
    @Test
    public void channel4() {
        Assert.assertEquals(true, es.marser.tools.DateTools.sameDay(in2, in3));
        Assert.assertEquals(true, es.marser.tools.DateTools.sameDay(es.marser.tools.DateTools.firstDaysOfTheMonth(in3), in5));
        Assert.assertEquals(false, es.marser.tools.DateTools.sameDay(es.marser.tools.DateTools.firstDaysOfTheMonth(in3), in2));
        Assert.assertEquals(true, es.marser.tools.DateTools.sameDay(es.marser.tools.DateTools.lastDayOfTheMonth(in3), in6));

        int saturdays = es.marser.tools.DateTools.countTheDayOfTheWeek(in7, in8, Calendar.SATURDAY);
        int wenddays = es.marser.tools.DateTools.countTheDayOfTheWeek(in7, in8, Calendar.WEDNESDAY);
        int fridays = es.marser.tools.DateTools.countTheDayOfTheWeek(in7, in8, Calendar.FRIDAY);
        Assert.assertEquals(true, saturdays == 5);
        Assert.assertEquals(true, wenddays == 4);
        Assert.assertEquals(true, fridays == 4);
        Assert.assertEquals(true, es.marser.tools.DateTools.businessDays(in7, in8) == 20);

        Assert.assertEquals(true, es.marser.tools.DateTools.betweenTwoDate(in7, in8, in9));
        Assert.assertEquals(false, es.marser.tools.DateTools.betweenTwoDate(in7, in8, in10));
        Assert.assertEquals(false, es.marser.tools.DateTools.betweenTwoDate(in7, in8, in11));
        Assert.assertEquals(true, es.marser.tools.DateTools.betweenTwoDate(in7, in8, in8));
        Assert.assertEquals(false, es.marser.tools.DateTools.isBusinessDay(new GregorianCalendar(2017, 8, 23)));
        Assert.assertEquals(true, es.marser.tools.DateTools.isBusinessDay(new GregorianCalendar(2017, 8, 13)));
        GregorianCalendar[] holidays = new GregorianCalendar[]{
                new GregorianCalendar(2017, 8, 5),
                new GregorianCalendar(2017, 8, 13),
                new GregorianCalendar(2017, 8, 23),
                new GregorianCalendar(2017, 8, 25)};
        GregorianCalendar[] holidays2 = new GregorianCalendar[]{
                new GregorianCalendar(2017, 8, 5),
                new GregorianCalendar(2017, 8, 13),
                new GregorianCalendar(2017, 8, 23),//sábado, saturday
                new GregorianCalendar(2017, 8, 25)};
        es.marser.tools.DateTools.Pair year = new es.marser.tools.DateTools.Pair(new GregorianCalendar(2016, 11, 31), new GregorianCalendar(2017, 11, 31));
        /*Filtro de fechas vacacionales [EN]  Holiday Dates Filter*/
        Assert.assertEquals(true, es.marser.tools.DateTools.filterBusinessListBetween(year, holidays).equals(es.marser.tools.DateTools.filterBusinessListBetween(year, holidays2)));
        Assert.assertEquals(false, es.marser.tools.DateTools.isBusinessDay(
                new GregorianCalendar(2017, 8, 13),
                es.marser.tools.DateTools.filterBusinessListBetween(es.marser.tools.DateTools.yearRange(2017), holidays)
                )
        );
        Assert.assertEquals(true, es.marser.tools.DateTools.isBusinessDay(
                new GregorianCalendar(2017, 8, 12),
                es.marser.tools.DateTools.filterBusinessListBetween(es.marser.tools.DateTools.yearRange(2017), holidays)
                )
        );

        /*Añadir días hábiles [EN]  Add business days*/
        Assert.assertEquals(true, es.marser.tools.DateTools.formatComparative(
                es.marser.tools.DateTools.addBusinessDays(
                        new GregorianCalendar(2017, 8, 13), 10)//27 de septiembre
                ).equals("27092017")
        );

        /*Añadir días hábiles [EN]  Add business days*/
        Assert.assertEquals(true, es.marser.tools.DateTools.formatComparative(
                es.marser.tools.DateTools.addBusinessDays(
                        new GregorianCalendar(2017, 8, 13),
                        10,
                        es.marser.tools.DateTools.filterBusinessListBetween(es.marser.tools.DateTools.yearRange(2017), holidays)
                )//28 de septiembre
                ).equals("28092017")
        );
    }

    @Test
    public void channel5() {
        /*Rango año 2017 [EN]  [EN]  Rank year 2017*/
        Assert.assertEquals(true, es.marser.tools.DateTools.yearRange(2017).toString().equals("Pair|31122016|31122017"));
         /*Rango febrero 2017 [EN]  Rank February 2017*/
        Assert.assertEquals(true, es.marser.tools.DateTools.monthRange(2017, 1).toString().equals("Pair|31012017|28022017"));
        Assert.assertEquals(true, es.marser.tools.DateTools.monthRange(2020, 1).toString().equals("Pair|31012020|29022020"));
        Assert.assertEquals(true, es.marser.tools.DateTools.monthRange(2017, 11).toString().equals("Pair|30112017|31122017"));
    }
}