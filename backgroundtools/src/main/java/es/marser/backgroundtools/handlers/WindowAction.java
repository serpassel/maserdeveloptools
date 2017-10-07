package es.marser.backgroundtools.handlers;

import android.view.View;

/**
 * @author sergio
 *         Created by sergio on 6/10/17.
 *         Modelo manejador de eventos de ventana
 *         <p>
 *         [EN]  Window event handler model
 * @see es.marser.backgroundtools.handlers.ViewHandler
 */

@SuppressWarnings("unused")
public interface WindowAction<T> extends ViewHandler<T> {
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
}
