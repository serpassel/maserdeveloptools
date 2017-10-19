package es.marser.backgroundtools.dialogs.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de progreso
 *         <p>
 *         [EN]  Observable Object for handling progress dialogs
 *
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogProgressModel extends DialogModel{

    public final ObservableField<String> max = new ObservableField<>();//Longitud máxima de progreso [EN]  Maximum length of progress
    public final ObservableField<String> progress = new ObservableField<>();//progreso [EN]  progress
    public final ObservableBoolean indeterminate = new ObservableBoolean(true); //Barra indeterminada [EN]  Indeterminate bar
    public final ObservableField<String> progresstext = new ObservableField<>("");//Texto de progreso [EN]  Progress text
    public final ObservableField<String> error = new ObservableField<>("");//Mensaje de error [EN]  Error message
}
