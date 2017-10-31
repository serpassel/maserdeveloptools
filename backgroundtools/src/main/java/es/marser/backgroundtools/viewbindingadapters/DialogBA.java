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
            case JPG_ICON:
                view.setImageResource(R.drawable.ic_jpg);
                break;
            case PNG_ICON:
                view.setImageResource(R.drawable.ic_png);
                break;
            case DOC_ICON:
                view.setImageResource(R.drawable.ic_doc);
                break;
            case HTML_ICON:
                view.setImageResource(R.drawable.ic_html);
                break;
            case PPT_ICON:
                view.setImageResource(R.drawable.ic_ppt);
                break;
            case XML_ICON:
                view.setImageResource(R.drawable.ic_xml);
                break;
            case ZIP_ICON:
                view.setImageResource(R.drawable.ic_zip);
                break;
            case MP3_ICON:
                view.setImageResource(R.drawable.ic_mp3);
                break;
            case TXT_ICON:
                view.setImageResource(R.drawable.ic_txt);
                break;
            case JSON_ICON:
                view.setImageResource(R.drawable.ic_json_file);
                break;
            case CSV_ICON:
                view.setImageResource(R.drawable.ic_csv);
                break;
            case PSD_ICON:
                view.setImageResource(R.drawable.ic_psd);
                break;
            case MP4_ICON:
                view.setImageResource(R.drawable.ic_mp4);
                break;
            case AVI_ICON:
                view.setImageResource(R.drawable.ic_avi);
                break;
            case RTF_ICON:
                view.setImageResource(R.drawable.ic_rtf);
                break;
            case DWG_ICON:
                view.setImageResource(R.drawable.ic_dwg);
                break;
            case CSS_ICON:
                view.setImageResource(R.drawable.ic_css);
                break;
            case JS_ICON:
                view.setImageResource(R.drawable.ic_javascript);
                break;
            case AI_ICON:
                view.setImageResource(R.drawable.ic_ai);
                break;
            case EXE_ICON:
                view.setImageResource(R.drawable.ic_exe);
                break;
            case ISO_ICON:
                view.setImageResource(R.drawable.ic_iso);
                break;
            case DBF_ICON:
                view.setImageResource(R.drawable.ic_dbf);
                break;
            case FLA_ICON:
                view.setImageResource(R.drawable.ic_fla);
                break;
            case PACKED_ICON:
                view.setImageResource(R.drawable.ic_packed);
                break;
            case FILE_ICON:
                view.setImageResource(R.drawable.ic_file);
                break;
            case SEARCH_ICON:
                view.setImageResource(R.drawable.ic_search);
                break;
            case FOLDER_UP_ICON:
                view.setImageResource(R.drawable.ic_folder_up);
                break;
            case FOLDER_ICON:
                view.setImageResource(R.drawable.ic_folder);
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
