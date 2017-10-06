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

    /*Referencia a los iconos admitidos [EN]  Reference to supported icons*/
    public static final String WARNING_ICON = "warning";
    public static final String ERROR_ICON = "error";
    public static final String INFORMATION_ICON = "info";


    public final ObservableField<String> msg = new ObservableField<>("");//Texto de progreso temporal [EN]  Temporary progress text
    public final ObservableField<String> title = new ObservableField<>("");//Título de carga [EN]  Loading title
    public final ObservableField<String> icon = new ObservableField<>("");//Icono del título [EN]  Title Icon


    @BindingAdapter(value = {"iconToast"})
    public static void setToastIcon(ImageView view, String icon) {
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
    public static void setPrimaryToastTextColor(TextView v, String state) {
        switch (state) {
            case ToastModel.WARNING_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_warning_color));
                break;
            case ToastModel.ERROR_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_error_color));
                break;
            case ToastModel.INFORMATION_ICON:
            default:
                v.setTextColor(ContextCompat.getColor(v.getContext(),R.color.bt_information_color));
                break;
        }
    }

    @BindingAdapter(value = {"colorSecondaryToastText"})
    public static void setSecondaryToastTextColor(TextView v, String state) {
        switch (state) {
            case ToastModel.WARNING_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_warning_color_light));
                break;
            case ToastModel.ERROR_ICON:
                v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.bt_error_color_light));
                break;
            case ToastModel.INFORMATION_ICON:
            default:
                v.setTextColor(ContextCompat.getColor(v.getContext(),R.color.bt_information_color_light));
                break;
        }
    }
}
