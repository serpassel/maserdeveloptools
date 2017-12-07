package es.marser.backgroundtools.widget.calendar.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinTableDECREP;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.EventsExtras;
import es.marser.backgroundtools.widget.calendar.model.CalendarObservable;
import es.marser.backgroundtools.widget.calendar.model.DayWeek;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Selector de fechas
 *         <p>
 *         [EN]  Date chooser
 */

@SuppressWarnings("ALL")
public class CalendarChooser
        extends BaseDialogBinTableDECREP<DayWeek, CalendarObservable>
       {


    /**
     * Nueva instancia {@link CalendarChooser}
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param bundle  Argumentos de inicio [EN]  Start arguments
     * @param result  Variable de resultados [EN]  Variable of results
     * @return nueva instancia del dialogo [EN]  new instance of dialogue
     */
    @SuppressWarnings("All")
    public static CalendarChooser newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @Nullable OnResult<CalendarObservable> result) {

        CalendarChooser instance = new CalendarChooser();
        instance.setContext(context);
        instance.setArguments(bundle);
        instance.setResult(result);
        return instance;
    }


    @SuppressWarnings("All")

    @Override
    protected void postBuild() {
        super.postBuild();
        loadDayWeek();
        load();
    }
    //ACTIONS__________________________________________________________________________________

    /*{@link es.marser.backgroundtools.systemtools.events.SimpleGestureFilter.SimpleGestureListener}*/
    @Override
    public void onSwipe(EventsExtras eventsExtras) {
        super.onSwipe(eventsExtras);
        switch (eventsExtras){
            case SWIPE_UP:
                nextYear();
                break;
            case SWIPE_DOWN:
                previousYear();
                break;
            case SWIPE_LEFT:
                previousMonth();
                break;
            case SWIPE_RIGHT:
                nextMonth();
                break;
        }
    }

}
