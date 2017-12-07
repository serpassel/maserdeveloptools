package es.marser.backgroundtools.widget.calendar.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

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

@SuppressWarnings("unused")
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

        this(context,
                new GridLayoutManager(context, 7),
                titleHolderLayout, headHolderLayout, bodyHolderLayout);
    }

    public CalendarTableAdapterModel(@NonNull Context context,
                                     @NonNull GridLayoutManager layoutManager,
                                     int titleHolderLayout,
                                     int headHolderLayout,
                                     int bodyHolderLayout) {
        super(context, layoutManager, titleHolderLayout, headHolderLayout, bodyHolderLayout);
        //Expansor de celdas de la tabla
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                ViewHolderType type = ViewHolderType.values()[getAdapter().getItemViewType(position)];

                switch (type) {
                    case TITLE:
                        return 7;
                    case HEAD:
                        return 1;
                    case BODY:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
    }
}
