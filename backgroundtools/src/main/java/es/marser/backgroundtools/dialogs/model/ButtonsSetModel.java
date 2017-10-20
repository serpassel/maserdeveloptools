package es.marser.backgroundtools.dialogs.model;

import android.databinding.ObservableField;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de notificaciones
 *         <p>
 *         [EN]  Observable object for handling notifications dialogs
 *
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ButtonsSetModel {
    public final ObservableField<String> ok_name = new ObservableField<>("ACEPTAR");//Título de carga [EN]  Loading title
    public final ObservableField<String> cancel_name= new ObservableField<>("CANCELAR");//Texto del cuerpo [EN]  Body text
    public final ObservableField<String> option_name= new ObservableField<>();//Nombre de la clave de reiteración [EN]  Name of the reiteration key
}
