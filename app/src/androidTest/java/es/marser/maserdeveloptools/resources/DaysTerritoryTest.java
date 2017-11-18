package es.marser.maserdeveloptools.resources;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.marser.LOG_TAG;
import es.marser.async.TaskLoadingResult;
import es.marser.backgroundtools.dialogs.model.HolidayModel;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DaysTerritoryTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        // hcrud.saveYear(model.getCalendar().get(Calendar.YEAR));
        for (int i = 2015; i < new GregorianCalendar().get(Calendar.YEAR) + 2; ++i) {
           // print(appContext, i);
            checkYearHolidays(appContext, i);
        }

    }

    @Test
    public void getHolidays() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        for (int i = 2015; i < new GregorianCalendar().get(Calendar.YEAR) + 2; ++i) {
            int size = ResourcesAccess.getHolidays(appContext, i, new TaskLoadingResult<HolidayModel>() {

                @Override
                public void onUpdate(HolidayModel update) {
                    Log.w(LOG_TAG.TAG, "Día " + update.toString());
                }
            }).size();


            Log.i(LOG_TAG.TAG, "Recursos " + i + ": " + size);
        }

    }


    private void checkYearHolidays(Context appContext, int year) {
        Assert.assertEquals(
                ResourcesAccess.getAutonomyHolidays(appContext, year).length,
                ResourcesAccess.getStringArray(appContext, "text_holidays_" + year).length
        );

        Log.i(LOG_TAG.TAG, "days " + ResourcesAccess.getAutonomyHolidays(appContext, year).length);
        Log.i(LOG_TAG.TAG, "text " + ResourcesAccess.getStringArray(appContext, "text_holidays_" + year).length);
    }

    private void print(Context context, int year) {
        StringBuilder builder = new StringBuilder();
        builder.append("<string-array name=\"holidays_").append(year).append("\">\n");
        String[] nationalHolidays = ResourcesAccess.getNationalHolidays(context, year);
        for (String s : nationalHolidays) {
            builder
                    .append("<item>")
                    .append("AH").append(TextTools.OBJECT_SEPARATOR)
                    .append(s).append(TextTools.OBJECT_SEPARATOR)
                    .append("0\\").append(TextTools.OBJECT_SEPARATOR)
                    .append(0).append(TextTools.OBJECT_SEPARATOR)
                    .append("</item>\n");
        }

        String[] autonomyHolidays = ResourcesAccess.getAutonomyHolidays(context, year);
        for (String s : autonomyHolidays) {
            builder
                    .append("<item>")
                    .append("AH").append(TextTools.OBJECT_SEPARATOR)
                    .append(s).append(TextTools.OBJECT_SEPARATOR)
                    .append(transform(ResourcesAccess.getHolidayText(context, DateTools.parseShortDate(s)))).append(TextTools.OBJECT_SEPARATOR)
                    .append(0).append(TextTools.OBJECT_SEPARATOR)
                    .append("</item>\n");
        }
        builder.append("</string-array>\n");
        Log.w(LOG_TAG.TAG, "**************************AÑO " + year + "*************************************");
        Log.i(LOG_TAG.TAG, builder.toString());
    }

    private static String transform(String in) {
        return in
                .replace("ANDALUCIA", "1")
                .replace("ARAGON", "2")
                .replace("ASTURIAS", "3")
                .replace("ISLAS BALEARES", "4")
                .replace("CANARIAS", "5")
                .replace("CANTABRIA", "6")
                .replace("CASTILLA Y LEON", "7")
                .replace("CASTILLA-LA MANCHA", "8")
                .replace("CATALUÑA", "9")
                .replace("VALENCIA", "10")
                .replace("EXTREMADURA", "11")
                .replace("GALICIA", "12")
                .replace("MADRID", "13")
                .replace("MURCIA", "14")
                .replace("NAVARRA", "15")
                .replace("PAIS VASCO", "16")
                .replace("LA RIOJA", "17")
                .replace("CEUTA", "18")
                .replace("MELILLA", "19")
                .replace(";", TextTools.ITEM_SEPARATOR_SPLIT);
    }
}
