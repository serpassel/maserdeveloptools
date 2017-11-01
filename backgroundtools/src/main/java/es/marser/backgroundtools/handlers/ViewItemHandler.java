package es.marser.backgroundtools.handlers;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;

/**
 * @author sergio
 *         Created by Sergio on 31/08/2017.
 *         Eventos de pulsación sobre la vista principal en una lista de elementos
 *         <p>
 *         [EN]  Keystroke events on the main view in a list of items
 */

@SuppressWarnings("ALL")
public interface ViewItemHandler<T> {
    //Eventos

    /**
     * Pulsación corta sobre vista del elemento
     * <p>
     * [EN]  Short press on element view
     *
     * @param v        vista principal
     * @param item     Objeto de datos
     * @param position posición de datos
     * @param mode     modo de pulsación en el adapter
     */
    void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode);

    /**
     *  Pulsación larga sobre vista del elemento
     * <p>
     * [EN]  Long press on element view

     * @param v        vista principal
     * @param item     Objeto de datos
     * @param position posición de datos
     * @param mode     modo de pulsación en el adapter
     * @return devolver true si está activado
     */
    boolean onLongClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode);
}
