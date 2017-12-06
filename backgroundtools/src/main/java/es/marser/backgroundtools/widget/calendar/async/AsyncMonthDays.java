package es.marser.backgroundtools.widget.calendar.async;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.backgroundtools.widget.calendar.model.CalendarObservable;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Método asíncrono de carga de días el calendario
 *         <p>
 *         [EN]  Asynchronous method of loading days on the calendar
 */
public class AsyncMonthDays extends AsyncTask<DateLoader, Integer, List<CalendarObservable>> {

    private DataUploaderTask<Void, Integer, List<CalendarObservable>> result;

    public AsyncMonthDays(DataUploaderTask<Void, Integer, List<CalendarObservable>> result) {
        this.result = result;
    }

    @Override
    protected List<CalendarObservable> doInBackground(DateLoader... calendars) {

        List<CalendarObservable> list = new ArrayList<>();
        DateLoader dateLoader = calendars[0];

        GregorianCalendar in = dateLoader.getCalendar();
        String holidayFilter = dateLoader.getHolidayFilter();
        String otherHolidayFilter = dateLoader.getOtherHolidayFilter();

            /*Día de partida [EN]  Day of departure*/
        GregorianCalendar firstMonth = DateTools.firstDaysOfTheMonth(in);
        GregorianCalendar firstWeek = DateTools.firstDayOfTheWeek(firstMonth);

            /*Día de finalización [EN]  End day*/
        GregorianCalendar lastMonth = DateTools.lastDayOfTheMonth(in);
        GregorianCalendar lastWeek = DateTools.lastDayOfTheWeek(lastMonth);

            /*
          Log.d(LOG_TAG.TAG, "ORIGINAL " + DateTools.formatShortDate(in));
           Log.i(LOG_TAG.TAG, "First Month " + DateTools.formatShortDate(firstMonth));
            Log.w(LOG_TAG.TAG, "First Week " + DateTools.formatShortDate(firstWeek));
            Log.i(LOG_TAG.TAG, "Last Month " + DateTools.formatShortDate(lastMonth));
            Log.w(LOG_TAG.TAG, "Last Week " + DateTools.formatShortDate(lastWeek));
*/
        long iterNum = DateTools.daysBetweenTwoDates(firstWeek, lastWeek);

        //          Log.w(LOG_TAG.TAG, "Número de días " + iterNum);

        for (int d = 0; d <= iterNum; ++d) {
                /*Clonar resultado [EN]  Clone result*/
            GregorianCalendar iter = new GregorianCalendar();
            iter.setTimeInMillis(firstWeek.getTimeInMillis());
                /*Agregar días [EN]  Add days*/
            iter.add(Calendar.DAY_OF_YEAR, d);
                /*Publicar la posición del día vigente [EN]  Publish the current day position*/
            if (DateTools.sameDay(iter, in)) {
                publishProgress(d);
            }

            CalendarObservable citer = new CalendarObservable(iter);
            citer.setOthermonth(in.get(Calendar.MONTH) != iter.get(Calendar.MONTH));

            citer.setHoliday(!DateTools.isBusinessDay(iter, holidayFilter));

            if (!TextTools.isEmpty(otherHolidayFilter)) {
                citer.setOtherholiday(!DateTools.isBusinessDay(iter, otherHolidayFilter) && !citer.isHoliday());
            }
            list.add(citer);
        }
        return list;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (result != null && !isCancelled()) {
            result.onUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(List<CalendarObservable> calendarObservables) {
        super.onPostExecute(calendarObservables);
        if (result != null && !isCancelled()) {
            result.onFinish(calendarObservables);
        }
    }


}