package es.marser.backgroundtools.dialogs.model;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.ImageView;
import android.widget.ProgressBar;

import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.tools.MathTools;
import es.marser.backgroundtools.R;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de progreso
 *         <p>
 *         [EN]  Observable Object for handling progress dialogs
 *
 */

@BindingMethods({
        @BindingMethod(type = ProgressBar.class, attribute = "android:max", method = "setMax"),
        @BindingMethod(type = ProgressBar.class, attribute = "android:progress", method = "setProgress")
})
@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogProgressModel {

    public final ObservableField<String> max = new ObservableField<>();//Longitud máxima de progreso [EN]  Maximum length of progress
    public final ObservableField<String> progress = new ObservableField<>();//progreso [EN]  progress
    public final ObservableBoolean indeterminate = new ObservableBoolean(true); //Barra indeterminada [EN]  Indeterminate bar
    public final ObservableField<String> temp = new ObservableField<>("");//Texto de progreso temporal [EN]  Temporary progress text
    public final ObservableField<String> progresstext = new ObservableField<>("");//Texto de progreso [EN]  Progress text
    public final ObservableField<String> title = new ObservableField<>("Cargando...");//Título de carga [EN]  Loading title
    public final ObservableField<String> error = new ObservableField<>("");//Mensaje de error [EN]  Error message
    public final ObservableField<BaseDialog.DIALOG_ICON> icon = new ObservableField<>();//Icono del título [EN]  Title Icon

    @BindingAdapter(value = {"android:max"})
    public static void setMax(ProgressBar bar, String value) {
        bar.setMax(MathTools.parseInt(value));
    }

    @BindingAdapter(value = {"android:progress"})
    public static void setProgress(ProgressBar bar, String value) {
        bar.setProgress(MathTools.parseInt(value));
    }
}
