package es.marser.backgroundtools.dialogs.model;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.ImageView;
import android.widget.ProgressBar;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.tools.MathTools;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de notificaciones
 *         <p>
 *         [EN]  Observable object for handling notifications dialogs
 *
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogNotificationModel {

    public final ObservableField<String> title = new ObservableField<>("NOTIFICACION");//Título de carga [EN]  Loading title
    public final ObservableField<String> msg = new ObservableField<>("");//Texto de progreso temporal [EN]  Temporary progress text
    public final ObservableField<BaseDialog.DIALOG_ICON> icon = new ObservableField<>();//Icono del título [EN]  Title Icon
}
