package es.marser.backgroundtools.viewbindingadapters;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.model.CalendarObservable;
import es.marser.tools.BooleanTools;

/**
 * @author sergio
 *         Created by sergio on 2/11/17.
 *         Adaptadores para calendario
 *         <p>
 *         [EN]  Adapters for calendar
 */

public class CalendarBA {

    @BindingAdapter(value = {"isholiday", "isOtherholiday", "isOtherMonth"}, requireAll = false)
    public static void formatCalendarDay(TextView view, Boolean isHoliday, Boolean isOtherHolidays, Boolean isOtherMonth) {
        Log.d(LOG_TAG.TAG, "ENTRADA" + "festivo " + isHoliday + " Otro mes " + isOtherMonth);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = view.getContext().getTheme();

        theme.resolveAttribute(R.attr.dialogs_edittext_hint_color, typedValue, true);
        @ColorInt int textSecondary = typedValue.data;

        theme.resolveAttribute(R.attr.dialogs_edittext_text_color, typedValue, true);
        @ColorInt int textPrimary = typedValue.data;

        view.setTextColor(textPrimary);

        if (BooleanTools.nc(isHoliday)) {
            Log.d(LOG_TAG.TAG, "FESTIVO");
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.bt_wrong_color_red_500));
        }

        if (BooleanTools.nc(isOtherMonth)) {
            Log.d(LOG_TAG.TAG, "OTRO MES");
            view.setTextColor(textSecondary);
        }

    }
}
