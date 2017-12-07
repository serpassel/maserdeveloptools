package es.marser.backgroundtools.events;

import android.view.View;

/**
 * @author sergio
 *         Created by sergio on 6/10/17.
 *         Modelo manejador de eventos de ventana
 *         <p>
 *         [EN]  Window event handler model
 * @see es.marser.backgroundtools.events.ViewHandler
 */

@SuppressWarnings({"unused", "EmptyMethod"})
public interface WindowAction {
    /**
     * Acción aceptar
     * <p>
     * [EN]  Accept action
     *
     * @param v Vista pulsada [EN]  Pulsed view
     */
    void onOk(View v);

    /**
     * Acción cancelar
     * <p>
     * [EN]  Action cancel
     *
     * @param v Vista pulsada [EN]  Pulsed view
     */
    void onCancel(View v);

    /**
     * Acción en opción
     * <p>
     * [EN] Action in option
     *
     * @param v Vista pulsada [EN]  Pulsed view
     */
    void onOption(View v);
}
