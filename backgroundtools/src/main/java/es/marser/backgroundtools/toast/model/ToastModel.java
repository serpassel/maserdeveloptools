package es.marser.backgroundtools.toast.model;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.ObservableField;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de progreso
 *         <p>
 *         [EN]  Observable Object for handling progress dialogs
 */

@BindingMethods({
        @BindingMethod(type = ProgressBar.class, attribute = "android:max", method = "setMax"),
        @BindingMethod(type = ProgressBar.class, attribute = "android:progress", method = "setProgress")
})
@SuppressWarnings({"WeakerAccess", "unused"})
public class ToastModel {

    public final ObservableField<String> msg = new ObservableField<>("");//Texto de progreso temporal [EN]  Temporary progress text
    public final ObservableField<String> title = new ObservableField<>("");//Título de carga [EN]  Loading title
    public final ObservableField<BaseDialog.DIALOG_ICON> icon = new ObservableField<>();//Icono del título [EN]  Title Icon


    @BindingAdapter(value = {"iconToast"})
    public static void setToastIcon(ImageView view, BaseDialog.DIALOG_ICON icon) {
        switch (icon) {
            case WARNING_ICON:
                view.setImageResource(R.drawable.ic_warning);
                break;
            case ERROR_ICON:
                view.setImageResource(R.drawable.ic_error);
                break;
            case INFORMATION_ICON:
            default:
                view.setImageResource(R.drawable.ic_information);
                break;
        }
    }

    @BindingAdapter(value = {"colorPrimaryToastText"})
    public static void setPrimaryToastTextColor(TextView v, BaseDialog.DIALOG_ICON state) {
        switch (state) {
            case WARNING_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_warning_color));
                break;
            case ERROR_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_error_color));
                break;
            case INFORMATION_ICON:
            default:
                v.setTextColor(ContextCompat.getColor(v.getContext(),R.color.bt_information_color));
                break;
        }
    }

    @BindingAdapter(value = {"colorSecondaryToast"})
    public static void setSecondaryToastColor(View v, BaseDialog.DIALOG_ICON state) {
        switch (state) {
            case WARNING_ICON:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.bt_warning_color_light));
                break;
            case ERROR_ICON:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.bt_error_color_light));
                break;
            case INFORMATION_ICON:
            default:
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(),R.color.bt_information_color_light));
                break;
        }
    }
}
