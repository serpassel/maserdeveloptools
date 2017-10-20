package es.marser.backgroundtools.handlers;

import android.view.View;

/**
 * @author sergio
 *         Created by Sergio on 26/07/2017.
 *         Modelo manejador de eventos en vistas MVP
 *         <p>
 *         [EN]  Event handler model in MVP views
 */

@SuppressWarnings({"unused", "EmptyMethod", "SameReturnValue"})
public interface ViewHandler<T> {

    /**
     * Pulsación corta sobre una vista
     * <p>
     * [EN]  Short press on a view
     * <p>
     * \@{(v)- \> handler.onClick(v,item)}
     *
     * @param v    Vista pulsada [EN]  Press view
     * @param item modelo de datos de clase genérica [EN]  generic class data model
     */
    void onClick(View v, T item);

    /**
     * Pulsación larga sobre una vista
     * <p>
     * [EN]  Long press on a view
     * <p>
     * \@{(v)->handler.onLongClick(v,item)}
     *
     * @param v    Vista pulsada [EN]  Press view
     * @param item modelo de datos de clase genérica [EN]  generic class data model
     * @return verdadero si está activada la pulsación larga [EN]  true if long press is activated
     */
    boolean onLongClick(View v, T item);

}
