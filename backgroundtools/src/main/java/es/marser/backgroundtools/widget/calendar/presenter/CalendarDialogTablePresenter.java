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
import es.marser.backgroundtools.containers.dialogs.presenter.TableDialogListPresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.ViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.widget.calendar.async.AsyncMonthDays;
import es.marser.backgroundtools.widget.calendar.async.DateLoader;
import es.marser.backgroundtools.widget.calendar.model.CalendarObservable;
import es.marser.backgroundtools.widget.calendar.model.CalendarTableAdapterModel;
import es.marser.backgroundtools.widget.calendar.model.DayWeek;
import es.marser.backgroundtools.widget.confirmation.dialog.NotificationDialog;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Presentador de fechas de calendario
 *         <p>
 *         [EN]  Presenter of calendar dates
 */

public class CalendarDialogTablePresenter
        extends TableDialogListPresenter<DayWeek, CalendarObservable, CalendarTableAdapterModel>
        implements ViewHandler<Void> {

    /*RESULT*/
    protected OnResult<CalendarObservable> result;

    /*MODEL*/
    protected CalendarObservable headmodel;

    //CONSTRUCTORS________________________________

    public CalendarDialogTablePresenter(@NonNull Context context) {
        this(context, R.layout.mvp_dialog_calendar_chooser);
    }

    public CalendarDialogTablePresenter(@NonNull Context context, int viewlayout) {
        this(context, viewlayout, new CalendarTableAdapterModel(context));
    }

    public CalendarDialogTablePresenter(@NonNull Context context, int viewlayout, @NonNull CalendarTableAdapterModel listModel) {
        super(context, viewlayout, listModel);
        headmodel = new CalendarObservable();
        setSelectionmode(ViewHolderType.TITLE.ordinal(), ListExtra.NOT_SELECTION_MODE);
        setSelectionmode(ViewHolderType.HEAD.ordinal(), ListExtra.NOT_SELECTION_MODE);
        setSelectionmode(ViewHolderType.BODY.ordinal(), ListExtra.ONLY_SINGLE_SELECTION_MODE);
    }

    //OVERRIDE______________________________________
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.headmodel, headmodel);
        binderContainer.bindObject(BR.handler, this);
    }

    @Override
    public void load(@Nullable Bundle bundle) {
        loadDayMoth();
    }

    //LOAD__________________________________________

    /**
     * Carga la cabecera
     * <p>
     * [EN]  Load the header
     */
    private void loadDayWeek() {

        String[] names = getContext().getResources().getStringArray(R.array.day_of_week_sort_name);

        for (String name : names) {
            DayWeek value = new DayWeek();
            value.day.set(name);
            getListmodel().addHead(value);
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
                if (getListmodel() != null) {
                    getListmodel().replaceBody(finish);
                    SelectionItemsController itemsController = getListmodel().getSelectionItemsController();

                    if (itemsController != null) {
                        itemsController.inputSelected(datepos[0], true);
                    }

                }else{
                    throw new NullPointerException("List model null");
                }
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

    //WINACTION_____________________________________
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

    //VIEW HANDLER ITEM_____________________________
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
                                context,
                                NotificationDialog.createInformationBundle(context, title, list.replace(TextTools.POINT_COMMA, TextTools.SALTO_LINEA_CHAR))
                        );
                dialog.show();
            }
        }

        return true;
    }

    //VIEW HANDLER__________________________________
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

    //PROPERTIES___________________________________
    public OnResult<CalendarObservable> getResult() {
        return result;
    }

    public void setResult(OnResult<CalendarObservable> result) {
        this.result = result;
    }

    public CalendarObservable getHeadmodel() {
        return headmodel;
    }

    public void setHeadmodel(CalendarObservable headmodel) {
        this.headmodel = headmodel;
    }

    //OPERATIVE_____________________________________
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
