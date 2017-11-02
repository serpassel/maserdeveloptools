package es.marser.backgroundtools.dialogs.widget.calendar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseDialogHeadBodyBinList;
import es.marser.backgroundtools.dialogs.model.CalendarObservable;
import es.marser.backgroundtools.dialogs.model.DayWeek;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.tools.DateTools;
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
        extends BaseDialogHeadBodyBinList<DayWeek, CalendarObservable>
        implements ViewHandler<Void> {

    protected OnResult<CalendarObservable> result;

    protected CalendarObservable headmodel;

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
        instance.setResult(null);
        return instance;
    }

    /**
     * Creador de argumentos del cuadro de dialogo
     * <p>
     * [EN]  Dialog Box Argument Creator
     *
     * @param icon   Icono para la barra de título [EN]  Icon for the title bar
     * @param title  Título de la barra [EN]  Title of the bar
     * @param path   Directorio de búsqueda [EN]  Search directory
     * @param ok     Texto de botón aceptar [EN]  Accept button text
     * @param cancel Texto de botón cancelar [EN]  Cancel button text
     * @return Bundle argumentado [EN]  Bundle argued
     */
    @SuppressWarnings("All")
    public static Bundle createBundle(DialogIcon icon, String title, String ok, String cancel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
        bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
        return bundle;
    }

    /**
     * Valores de prueba por defecto
     * <p>
     * [EN]  Default test values
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static Bundle createBundle(Context context) {
        return createBundle(
                DialogIcon.CALENDAR_2_ICON,
                context.getResources().getString(R.string.bt_dialog_select_title),
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL)
        );
    }

    //CREATION METHODS_________________________________________________________________________________________________________
    @Override
    protected void preBuild() {
        headmodel = new CalendarObservable();

        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));

        buttonsSetModel.ok_name.set(getArguments().getString(DialogExtras.OK_EXTRA.name()));
        buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.headmodel, headmodel);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.handler, this);
        viewDataBinding.executePendingBindings();
    }


    @Override
    protected void postBuild() {
        super.postBuild();
        getHeadGlobalController().selectionController.setSelectionMode(ListExtra.NOT_SELECTION_MODE);
        loadDayWeek();
        load();
    }

    //OVERWRITING OF SUPERCLASS METHODS________________________________________________________________

    @Override
    public int getHeadHolderLayout() {
        return R.layout.mvp_item_calendar_week_day;
    }

    @Override
    public int getBodyHolderLayout() {
        return R.layout.mvp_item_calendar_month_day;
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_calendar_chooser;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new GridLayoutManager(getContext(), 7);
        } else {
            return new GridLayoutManager(getActivity(), 7);
        }
    }

    @Override
    protected void load() {
        loadDayMoth();
    }

    /**
     * Carga la cabecera
     * <p>
     * [EN]  Load the header
     */
    private void loadDayWeek() {

        String[] names = getContext().getResources().getStringArray(R.array.day_of_week_sort_name);

        for (int i = 0; i < names.length; ++i) {
            DayWeek value = new DayWeek();
            value.day.set(names[i]);
            getHeadGlobalController().arrayListController.add(value);
        }
    }

    /**
     * Carga los días del mes
     * <p>
     * [EN]  Load the days of the month
     */
    private void loadDayMoth() {
       final int[] datepos = new int[1];
        /*Cagar datos [EN]  Download data*/
        new AsyncMonthDays(new DataUploaderTask<Void, Integer, List<CalendarObservable>>() {
            @Override
            public void onStart(Void start) {
            }

            @Override
            public void onUpdate(Integer update) {
                datepos[0] = update;
            }

            @Override
            public void onFinish(List<CalendarObservable> finish) {
                getBodyGlobalController().arrayListController.replaceAllItems(finish);
                getBodyGlobalController().selectionController.inputSelected(datepos[0], true);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        }).execute(headmodel.getCalendar());

    }

    //PROPERTIES_______________________________________________________________________________

    /*{@link OnResult}*/
    @SuppressWarnings("unused")
    public OnResult<CalendarObservable> getResult() {
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    public void setResult(OnResult<CalendarObservable> result) {
        this.result = result;
    }

    //ACTIONS__________________________________________________________________________________
    /* {@link es.marser.backgroundtools.handlers.WindowAction}*/
    @Override
    public void onOk(View view) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, headmodel);
        }
        close();
    }

    @Override
    public void onCancel(View view) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, headmodel);
        }
        close();
    }

    /* {@link ViewHandler}*/
    @Override
    public void onClick(View view, Void item) {

    }

    @Override
    public boolean onLongClick(View view, Void item) {
        return true;
    }

    //ASYNCHRONOUS LOADING__________________________________________________________________
    /**
     * Método asíncrono de carga de días el calendario
     * <p>
     * [EN]  Asynchronous method of loading days on the calendar
     */
    private class AsyncMonthDays extends AsyncTask<GregorianCalendar, Integer, List<CalendarObservable>> {

        private DataUploaderTask<Void, Integer, List<CalendarObservable>> result;

        public AsyncMonthDays(DataUploaderTask<Void, Integer, List<CalendarObservable>> result) {
            this.result = result;
        }

        @Override
        protected List<CalendarObservable> doInBackground(GregorianCalendar... calendars) {

            List<CalendarObservable> list = new ArrayList<>();
            GregorianCalendar in = calendars[0];
            /*Día de partida [EN]  Day of departure*/
            GregorianCalendar firstMonth = DateTools.firstDaysOfTheMonth(in);
            GregorianCalendar firstWeek = DateTools.firstDayOfTheWeek(firstMonth);

            /*Día de finalización [EN]  End day*/
            GregorianCalendar lastMonth = DateTools.lastDayOfTheMonth(in);
            GregorianCalendar lastWeek = DateTools.lastDayOfTheWeek(lastMonth);

            int iterNum = lastWeek.get(Calendar.DAY_OF_YEAR) - firstWeek.get(Calendar.DAY_OF_YEAR);

            for (int d = 0; d <= iterNum; ++d) {
                /*Clonar resultado [EN]  Clone result*/
                GregorianCalendar iter = new GregorianCalendar();
                iter.setTimeInMillis(firstWeek.getTimeInMillis());
                /*Agregar días [EN]  Add days*/
                iter.add(Calendar.DAY_OF_YEAR, d);
                /*Publicar la posición del día vigente [EN]  Publish the current day position*/
                if(DateTools.sameDay(iter,in)){
                    publishProgress(d);
                }

                CalendarObservable citer = new CalendarObservable(iter);
                citer.setOthermonth(in.get(Calendar.MONTH) != iter.get(Calendar.MONTH));
                citer.setHoliday(!DateTools.isBusinessDay(iter));

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
}
