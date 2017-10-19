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
public class DialogModel {

    public final ObservableField<String> title = new ObservableField<>("NOTIFICACION");//Título de carga [EN]  Loading title
    public final ObservableField<String> body= new ObservableField<>("");//Texto del cuerpo [EN]  Body text
    public final ObservableField<BaseDialog.DIALOG_ICON> icon = new ObservableField<>();//Icono del título [EN]  Title Icon
    public final ObservableBoolean key = new ObservableBoolean(false); //Clave de reiteración [EN]  Repeat Key
    public final ObservableField<String> keyname= new ObservableField<>("");//Nombre de la clave de reiteración [EN]  Name of the reiteration key
}
