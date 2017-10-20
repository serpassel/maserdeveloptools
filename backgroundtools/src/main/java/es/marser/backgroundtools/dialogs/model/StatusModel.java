package es.marser.backgroundtools.dialogs.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

/**
 * @author sergio
 *         Created by sergio on 20/10/17.
 *         Modelo de estado de las vistas
 *         <p>
 *         [EN]  View State Model
 */

@SuppressWarnings("unused")
public class StatusModel {
    /**
     * Variable para bloqueo de accions
     * <p>
     * [EN]  Variable for stock blocking
     */
    public final ObservableBoolean blockAction = new ObservableBoolean(false);

    /**
     * Variable de definición de estado de la vista
     * <p>
     * [EN]  Event handler model in MVP views
     */
    public final ObservableInt state = new ObservableInt(0);

}
