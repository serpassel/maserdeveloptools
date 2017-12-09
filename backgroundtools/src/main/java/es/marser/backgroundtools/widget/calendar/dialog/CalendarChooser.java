package es.marser.backgroundtools.widget.calendar.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.widget.calendar.model.CalendarObservable;
import es.marser.backgroundtools.widget.calendar.presenter.CalendarDialogTablePresenter;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Selector de fechas
 *         <p>
 *         [EN]  Date chooser
 */

@SuppressWarnings("ALL")
public class CalendarChooser
        extends BaseDialogBinList<CalendarDialogTablePresenter> {


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

        /*PRESENTER*/
        CalendarDialogTablePresenter presenter = new CalendarDialogTablePresenter(context);
        presenter.setArguments(bundle);
        presenter.setResult(result);

        /*DIALOG*/
        CalendarChooser instance = new CalendarChooser();
        instance.setContext(context);
        instance.setPresenter(presenter);

        return instance;
    }
}
