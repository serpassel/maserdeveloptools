package es.marser.backgroundtools.containers.fragments.widget;

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
import es.marser.backgroundtools.containers.fragments.base.BaseFragmentBinHeadBinTable;
import es.marser.backgroundtools.dialogs.model.CalendarObservable;
import es.marser.backgroundtools.dialogs.model.DayWeek;
import es.marser.backgroundtools.dialogs.model.MonthTitle;
import es.marser.backgroundtools.dialogs.widget.calendar.AsyncMonthDays;
import es.marser.backgroundtools.dialogs.widget.calendar.DateLoader;
import es.marser.backgroundtools.dialogs.widget.confirmation.NotificationDialogBinModel;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Fragmento selector de fechas
 *         <p>
 *         [EN]  Date selector fragment
 */

@SuppressWarnings("unused")
public class CalendarChooserFragment
        extends BaseFragmentBinHeadBinTable<CalendarObservable, DayWeek, CalendarObservable>
        implements ViewHandler<Void> {

    //INSTANCE__________________________________________________________________________________________
    public static CalendarChooserFragment newInstance() {

        return new CalendarChooserFragment();
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
    public int getTitleHolderLayout() {
        return R.layout.mvp_item_calendar_month_title;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvp_frag_calendar_chooser;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        final GridLayoutManager manager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = new GridLayoutManager(getContext(), 7);
        } else {
            manager = new GridLayoutManager(getActivity(), 7);
        }

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                ViewHolderType type = ViewHolderType.values()[adapter.getItemViewType(position)];

                switch (type) {
                    case TITLE:
                        return manager.getSpanCount();
                    case HEAD:
                        return 1;
                    case BODY:
                        return 1;
                    default:
                        return -1;
                }
            }
        });

        return manager;
    }

    @Override
    protected ListExtra getInitialSelectionMode() {
        return ListExtra.ONLY_SINGLE_SELECTION_MODE;
    }

    @Override
    public void binObjects(@Nullable Bundle savedInstanceState) {
        super.binObjects(savedInstanceState);
        viewDataBinding.setVariable(BR.handler, this);
        viewDataBinding.executePendingBindings();
    }

    @NonNull
    @Override
    public CalendarObservable getNewModelInstance() {
        return new CalendarObservable();
    }

    @Override
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        super.bindAdapter(savedInstanceState);
        if (savedInstanceState == null) {
            getHeadGlobalController().setSelectionmode(ListExtra.NOT_SELECTION_MODE);

            //getHeadGlobalController().clear();
            // loadDayWeek();
            load(null);

            //loadDayWeek();
            load(null);

        } else {
            adapter.onRestoreInstanceState(savedInstanceState);
        }
    }

    //LOAD_________________________________________________________________________
    @Override
    protected void load(Bundle bundle) {
        loadDayMoth();
    }

    /**
     * Carga la cabecera
     * <p>
     * [EN]  Load the header
     */
    private void loadDayWeek() {

        // getHeadGlobalController().clear();

        getTitleGlobalController().add(new MonthTitle("OCTUBRE"));

        String[] names = getContext().getResources().getStringArray(R.array.day_of_week_sort_name);

        for (String name : names) {
            DayWeek value = new DayWeek();
            value.day.set(name);
            getHeadGlobalController().add(value);
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
                getBodyGlobalController().addAll(finish);
                getBodyGlobalController().selectionController.inputSelected(datepos[0], true);
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


    //PRESENTERS___________________________________________________________________

    /* {@link ViewHandler}*/
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
                NotificationDialogBinModel dialog =
                        NotificationDialogBinModel.newInstance(
                                getContext(),
                                NotificationDialogBinModel.createInformationBundle(getContext(),
                                        title,
                                        list.replace(TextTools.POINT_COMMA, TextTools.SALTO_LINEA_CHAR))
                        );
                dialog.show();
            }
        }

        return true;
    }


    //CALCS_____________________________________________________________________________

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

}
