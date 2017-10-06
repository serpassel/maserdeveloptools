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

    void onOk(View v);
    void onCancel(View v);
}
