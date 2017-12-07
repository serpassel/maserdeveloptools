package es.marser.backgroundtools.widget.calendar.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.table.model.TableAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Modelo de adaptador para listas de calendario
 *         <p>
 *         [EN]  Adapter model for calendar lists
 */

public class CalendarTableAdapterModel extends TableAdapterModel<DayWeek, CalendarObservable> {

   //CONTRUCTORS__________________________________
    public CalendarTableAdapterModel(@NonNull Context context) {
        this(context,
                R.layout.mvp_item_calendar_month_title,
                R.layout.mvp_item_calendar_week_day,
                R.layout.mvp_item_calendar_month_day);
    }

    public CalendarTableAdapterModel(@NonNull Context context,
                                     int titleHolderLayout,
                                     int headHolderLayout,
                                     int bodyHolderLayout) {
        super(context);
        setLayoutManager(createCalendarLayoutManager(context));
        getAdapter().setHolderLayout(ViewHolderType.TITLE, titleHolderLayout);
        getAdapter().setHolderLayout(ViewHolderType.HEAD, headHolderLayout);
        getAdapter().setHolderLayout(ViewHolderType.BODY, bodyHolderLayout);
    }


    //LINEAR LAYOUT MANAGER________________________
    private LinearLayoutManager createCalendarLayoutManager(@NonNull Context context) {
        final GridLayoutManager manager;

        manager = new GridLayoutManager(context, 7);

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
}
