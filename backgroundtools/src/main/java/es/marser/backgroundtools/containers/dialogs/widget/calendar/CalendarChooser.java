package es.marser.backgroundtools.containers.dialogs.widget.calendar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinTableDECREP;
import es.marser.backgroundtools.containers.dialogs.model.CalendarObservable;
import es.marser.backgroundtools.containers.dialogs.model.DayWeek;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.containers.dialogs.widget.confirmation.NotificationDialogBin;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.EventsExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
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
        extends BaseDialogBinTableDECREP<DayWeek, CalendarObservable>
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
        instance.setResult(result);
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
                context.getResources().getString(R.string.bt_dialog_calendar_title),
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
        getHeadGlobalController().setSelectionmode(null,ListExtra.NOT_SELECTION_MODE);
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
    protected ListExtra getInitialSelectionMode() {
        return ListExtra.ONLY_SINGLE_SELECTION_MODE;
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
            getHeadGlobalController().add(value);
        }
    }

    /**
     * Carga los días del mes
     * <p>
     * [EN]  Load the days of the month
     */
    private void loadDayMoth() {
        final int[] datepos = new int[1];

        if (headmodel == null || headmodel.getCalendar() == null) {
            return;
        }

        int year = headmodel.getCalendar().get(Calendar.YEAR);

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
                getBodyGlobalController().replace(finish);
                getBodyGlobalController().getSelectionItemsController().inputSelected(datepos[0], true);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        }).execute(new DateLoader(
                        headmodel.getCalendar(),
                        ResourcesAccess.getNatinoalHolidaysFilter(getContext(), year),
                        ResourcesAccess.getAutonomyHolidaysFilter(getContext(), year)
                )
        );

    }

    /**
     * Fijar festivos en la fecha de cabecera
     * <p>
     * [EN]  Set holidays on the header date
     */
    private void completeHeadmodel() {
        if (headmodel == null || headmodel.getCalendar() == null) {
            return;
        }

        int year = headmodel.getCalendar().get(Calendar.YEAR);

        headmodel.setHoliday(
                !DateTools.isBusinessDay(headmodel.getCalendar(),
                        ResourcesAccess.getNatinoalHolidaysFilter(getContext(), year)
                )
        );

        headmodel.setOtherholiday(
                !DateTools.isBusinessDay(headmodel.getCalendar(),
                        ResourcesAccess.getAutonomyHolidaysFilter(getContext(), year)) && !headmodel.isHoliday()
        );
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
        if (view.getId() == R.id.id_calendar_sum_month) {
            nextMonth();
        } else if (view.getId() == R.id.id_calendar_subtract_month) {
            previousMonth();
        } else if (view.getId() == R.id.id_calendar_sum_year) {
            nextYear();
        } else if (view.getId() == R.id.id_calendar_subtract_year) {
            previousYear();
        }
    }

    @Override
    public boolean onLongClick(View view, Void item) {
        return false;
    }

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

    /*{@link es.marser.backgroundtools.handlers.ViewItemHandler}*/
    @Override
    public void onClickBodyItem(BaseViewHolder<CalendarObservable> holder, CalendarObservable item, int position, ListExtra mode) {
        super.onClickBodyItem(holder, item, position, mode);
        changedDate(item.getCalendar());
    }

    @Override
    public boolean onLongClickBodyItem(BaseViewHolder<CalendarObservable> holder, CalendarObservable item, int position, ListExtra mode) {
        changedDate(item.getCalendar());
        if (item.isOtherholiday()) {
            String list = ResourcesAccess.getHolidayText(getContext(), item.getCalendar());

            if (!TextTools.isEmpty(list)) {
                String title = "Día festivo en, ";
                list = list.replace(TextTools.POINT_COMMA, TextTools.SALTO_LINEA_CHAR);
                NotificationDialogBin dialog =
                        NotificationDialogBin.newInstance(
                                context,
                                NotificationDialogBin.createInformationBundle(context, title, list.replace(TextTools.POINT_COMMA, TextTools.SALTO_LINEA_CHAR))
                        );
                dialog.show();
            }
        }

        return true;
    }

    //CALC_________________________________________________________________________________

    private void nextMonth() {
        sumDateValues(Calendar.MONTH, 1);
    }

    private void previousMonth() {
        sumDateValues(Calendar.MONTH, -1);
    }

    private void nextYear() {
        sumDateValues(Calendar.YEAR, 1);
    }

    private void previousYear() {
        sumDateValues(Calendar.YEAR, -1);
    }

    /**
     * Cambio de selección de fecha
     * <p>
     * [EN]  Change of date selection
     *
     * @param in       nueva fecha
     * @param position posición del registro
     */
    private void changedDate(GregorianCalendar in) {
        int actualMonth = headmodel.getCalendar().get(Calendar.MONTH);
        int inMonth = in.get(Calendar.MONTH);

        int actualYear = headmodel.getCalendar().get(Calendar.YEAR);
        int inYear = in.get(Calendar.YEAR);

        headmodel.setCalendar(in);

        if (actualMonth != inMonth || actualYear != inYear) {
            loadDayMoth();
        }
        completeHeadmodel();
    }

    /**
     * Sumar un valor a la fecha vigente
     * <p>
     * [EN]  Add a value to the current date
     *
     * @param field Type of value{@link Calendar#YEAR} or {@link Calendar#MONTH}
     * @param amont Value
     */
    private void sumDateValues(int field, int amont) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(headmodel.getCalendar().getTimeInMillis());

        calendar.add(field, amont);
        changedDate(calendar);
    }

}
