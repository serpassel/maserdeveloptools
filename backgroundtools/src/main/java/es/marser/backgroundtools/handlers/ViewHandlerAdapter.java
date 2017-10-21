package es.marser.backgroundtools.handlers;

import android.view.View;

/**
 * @author sergio
 *         Created by Sergio on 26/08/2017.
 *         Adaptador de oyente de eventos de pulsación de vistas
 *         <p>
 *         [EN]  View Pulse Event Listener Adapter
 */

@SuppressWarnings("unused")
public class ViewHandlerAdapter<T> implements ViewHandler<T> {

    @Override
    public void onClick(View v, T item) {

    }

    @Override
    public boolean onLongClick(View v, T item) {
        return false;
    }

}
