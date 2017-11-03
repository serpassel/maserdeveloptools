package es.marser.backgroundtools.viewbindingadapters;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
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
    public static void formatTextCalendarDay(TextView view, Boolean isHoliday, Boolean isOtherHolidays, Boolean isOtherMonth) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = view.getContext().getTheme();

        theme.resolveAttribute(R.attr.calendar_normal_text, typedValue, true);
        @ColorInt int textNormal = typedValue.data;
        view.setTextColor(textNormal);

        if (BooleanTools.nc(isHoliday)) {

            theme.resolveAttribute(R.attr.calendar_holiday_text, typedValue, true);
            @ColorInt int textholiday = typedValue.data;
            view.setTextColor(textholiday);
        }

        if (BooleanTools.nc(isOtherHolidays)) {
            theme.resolveAttribute(R.attr.calendar_other_holiday_text, typedValue, true);
            @ColorInt int textmonth = typedValue.data;
            view.setTextColor(textmonth);
        }

        if (BooleanTools.nc(isOtherMonth)) {
            theme.resolveAttribute(R.attr.calendar_other_month_text, typedValue, true);
            @ColorInt int textmonth = typedValue.data;
            view.setTextColor(textmonth);
        }



    }
}
