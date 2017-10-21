package es.marser.backgroundtools.viewbindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.MathTools;

/**
 * @author sergio
 *         Created by sergio on 19/10/17.
 *         Adaptadores de vistas. Para uso de vinculación de datos MVP
 *         <p>
 *         [EN]  View adapters.  To use MVP data binding
 */
@SuppressWarnings("unused")
@BindingMethods({
        @BindingMethod(type = ProgressBar.class, attribute = "android:max", method = "setMax"),
        @BindingMethod(type = ProgressBar.class, attribute = "android:progress", method = "setProgress")
})
public class DialogBA {
    @BindingAdapter(value = {"android:max"})
    public static void setMax(ProgressBar bar, String value) {
        bar.setMax(MathTools.parseInt(value));
    }

    @BindingAdapter(value = {"android:progress"})
    public static void setProgress(ProgressBar bar, String value) {
        bar.setProgress(MathTools.parseInt(value));
    }

    @BindingAdapter(value = {"colorHeadText"})
    public static void setDialogHeadTextColor(TextView v, DialogIcon state) {
        switch (state) {
            case WARNING_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_warning_color));
                break;
            case ERROR_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_error_color));
                break;
            case INFORMATION_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_information_color));
                break;
            default:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_primary_light));
                break;
        }
    }

    @BindingAdapter(value = {"colorDialogBodyBackground"})
    public static void setSecondaryToastColor(View v, DialogIcon state) {
        switch (state) {
            case WARNING_ICON:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.bt_warning_color_light));
                break;
            case ERROR_ICON:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.bt_error_color_light));
                break;
            case INFORMATION_ICON:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.bt_information_color_light));
                break;
            default:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.bt_primary_light));
                break;
        }
    }

    @BindingAdapter(value = {"iconDialog"})
    public static void setDialogIcon(ImageView view, DialogIcon icon) {
        switch (icon) {
            case BC3_ICON:
                view.setImageResource(R.drawable.ic_bc3);
                break;
            case EXCEL_ICON:
                view.setImageResource(R.drawable.ic_xls);
                break;
            case PDF_ICON:
                view.setImageResource(R.drawable.ic_pdf);
                break;
            case DATABASE_ICON:
                view.setImageResource(R.drawable.ic_database);
                break;
            case CALC_ICON:
                view.setImageResource(R.drawable.ic_calculator);
                break;
            case WARNING_ICON:
                view.setImageResource(R.drawable.ic_warning);
                break;
            case ERROR_ICON:
                view.setImageResource(R.drawable.ic_error);
                break;
            case INFORMATION_ICON:
                view.setImageResource(R.drawable.ic_information);
                break;
            case HELP_ICON:
                view.setImageResource(R.drawable.ic_help);
                break;
            case ADD_ICON:
                view.setImageResource(R.drawable.ic_add);
                break;
            case EDIT_ICON:
                view.setImageResource(R.drawable.ic_edit);
                break;
            case QUESTION_ICON:
                view.setImageResource(R.drawable.ic_question);
                break;
            case LOADING_ICON:
            default:
                view.setImageResource(R.drawable.ic_sand_clock);
                break;
        }
    }
}
