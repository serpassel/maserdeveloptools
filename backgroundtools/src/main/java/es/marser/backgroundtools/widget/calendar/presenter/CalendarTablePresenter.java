package es.marser.backgroundtools.widget.calendar.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.ViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.table.presenter.TableListPresenter;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.widget.calendar.async.AsyncMonthDays;
import es.marser.backgroundtools.widget.calendar.async.DateLoader;
import es.marser.backgroundtools.widget.calendar.model.CalendarObservable;
import es.marser.backgroundtools.widget.calendar.model.CalendarTableAdapterModel;
import es.marser.backgroundtools.widget.calendar.model.DayWeek;
import es.marser.backgroundtools.widget.calendar.model.MonthTitle;
import es.marser.backgroundtools.widget.confirmation.dialog.NotificationDialog;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 9/12/17.
 *         Presentador para fragmentos de calendarios
 *         <p>
 *         [EN]  Announcer for calendar fragments
 */

public class CalendarTablePresenter
        extends TableListPresenter<DayWeek, CalendarObservable, CalendarTableAdapterModel>
        implements ViewHandler<Void> {

    private CalendarObservable model;

    //CONSTRUCTORS____________________________________________________________
    public CalendarTablePresenter(@NonNull Context context) {
        this(context, R.layout.mvp_frag_calendar_chooser);
    }

    public CalendarTablePresenter(@NonNull Context context, int viewlayout) {
        this(context, viewlayout, new CalendarTableAdapterModel(context));
    }

    public CalendarTablePresenter(@NonNull Context context, int viewlayout, @NonNull CalendarTableAdapterModel listModel) {
        super(context, viewlayout, listModel);
        setSelectionmode(ViewHolderType.TITLE.ordinal(), ListExtra.NOT_SELECTION_MODE);
        setSelectionmode(ViewHolderType.HEAD.ordinal(), ListExtra.NOT_SELECTION_MODE);
        setSelectionmode(ViewHolderType.BODY.ordinal(), ListExtra.ONLY_SINGLE_SELECTION_MODE);
        model = new CalendarObservable();
    }

    //OVERRIDE______________________________________

    /**
     * Indicador del conmienzo de la vinculación de vistas {@link ViewDataBinding}
     * <p>
     * [EN]  Join linking view indicator
     *
     * @param binderContainer Objeto de enlace de vistas [EN]  View link object
     */
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.handler, this);
        binderContainer.bindObject(BR.model, model);
    }


    //LOAD DATA_____________________________________

    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    @Override
    public void load(@Nullable Bundle bundle) {
        loadDayMoth();
    }

    /**
     * Carga la cabecera
     * <p>
     * [EN]  Load the header
     */
    private void loadDayWeek() {

        // getHeadGlobalController().clear();

        getListmodel().addTitle(new MonthTitle("OCTUBRE"));

        String[] names = getContext().getResources().getStringArray(R.array.day_of_week_sort_name);

        for (String name : names) {
            DayWeek value = new DayWeek();
            value.day.set(name);
            getListmodel().addHead(value);
        }

        //Log.i(LOG_TAG.TAG, "DAYS " + getHeadGlobalController().getItemCount());
    }

    /**
     * Carga los días del mes
     * <p>
     * [EN]  Load the days of the month
     */
    private void loadDayMoth() {
        final int[] datepos = new int[1];

        if (model == null || model.getCalendar() == null) {
            return;
        }

        int year = model.getCalendar().get(Calendar.YEAR);

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
                loadDayWeek();
                getListmodel().addAllBody(finish);
                SelectionItemsController itemsController = getListmodel().getSelectionItemsController();
                if (itemsController != null) {
                    itemsController.inputSelected(datepos[0], true);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        }).execute(new DateLoader(
                        model.getCalendar(),
                        ResourcesAccess.getNatinoalHolidaysFilter(getContext(), year),
                        ResourcesAccess.getAutonomyHolidaysFilter(getContext(), year)
                )
        );

    }


    //VIEW HANDLER__________________________________
    @Override
    public void onClick(View view, Void item) {
        if (view.getId() == R.id.id_calendar_sum_month) {
            sumDateValues(Calendar.MONTH, 1);
        } else if (view.getId() == R.id.id_calendar_subtract_month) {
            sumDateValues(Calendar.MONTH, -1);
        } else if (view.getId() == R.id.id_calendar_sum_year) {
            sumDateValues(Calendar.YEAR, 1);
        } else if (view.getId() == R.id.id_calendar_subtract_year) {
            sumDateValues(Calendar.YEAR, -1);
        }
    }

    @Override
    public boolean onLongClick(View view, Void item) {
        return false;
    }

    //VIEW ITEM HANDLER_____________________________
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
                NotificationDialog dialog =
                        NotificationDialog.newInstance(
                                getContext(),
                                NotificationDialog.createInformationBundle(getContext(),
                                        title,
                                        list.replace(TextTools.POINT_COMMA, TextTools.SALTO_LINEA_CHAR))
                        );
                dialog.show();
            }
        }

        return true;
    }

    //OPERATIVE_____________________________________

    /**
     * Fijar festivos en la fecha de cabecera
     * <p>
     * [EN]  Set holidays on the header date
     */
    private void completeHeadmodel() {
        if (model == null || model.getCalendar() == null) {
            return;
        }

        int year = model.getCalendar().get(Calendar.YEAR);

        model.setHoliday(
                !DateTools.isBusinessDay(model.getCalendar(),
                        ResourcesAccess.getNatinoalHolidaysFilter(getContext(), year)
                )
        );

        model.setOtherholiday(
                !DateTools.isBusinessDay(model.getCalendar(),
                        ResourcesAccess.getAutonomyHolidaysFilter(getContext(), year)) && !model.isHoliday()
        );
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
        int actualMonth = model.getCalendar().get(Calendar.MONTH);
        int inMonth = in.get(Calendar.MONTH);

        int actualYear = model.getCalendar().get(Calendar.YEAR);
        int inYear = in.get(Calendar.YEAR);

        model.setCalendar(in);

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
        calendar.setTimeInMillis(model.getCalendar().getTimeInMillis());

        calendar.add(field, amont);
        changedDate(calendar);
    }

    //PROPERTIES____________________________________
    @SuppressWarnings("unused")
    public CalendarObservable getModel() {
        return model;
    }

    @SuppressWarnings("unused")
    public void setModel(CalendarObservable model) {
        this.model = model;
    }
}
