package es.marser.backgroundtools.objectslistables.base.listeners;

import es.marser.backgroundtools.objectslistables.base.model.TogglePositionIndex;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Notificador de adapter
 *         <p>
 *         [EN]  Adapter Notifier
 */

@SuppressWarnings("unused")
public interface AdapterNotifier extends TogglePositionIndex {

    /**
     * Notifica la inserción de un elemento nuevo en una lista parcial
     * <p>
     * [EN]  Notifies the insertion of a new item in a partial list
     *
     * @param index    índice parcial [EN]  partial index
     * @param count    cantidad de registros totales en la lista parcial [EN]  number of total records in the partial list
     * @param viewType Vista asociada a la lista parcial [EN]  View associated with the partial list
     */
    void notifyItemInserted(int index, int count, int viewType);

    /**
     * Notifica que se ha eliminado un elemento en una lista parcial
     * <p>
     * [EN]  Notify that an item has been removed from a partial list
     *
     * @param index    índice parcial [EN]  partial index
     * @param viewType Vista asociada a la lista parcial [EN]  View associated with the partial list
     */
    void notifyItemRemoved(int index, int viewType);

    /**
     * Notifica el cambio de estado de un elemento en una lista parcial
     * <p>
     * [EN]  Notifies the change of state of an item in a partial list
     *
     * @param index    índice parcial [EN]  partial index
     * @param viewType Vista asociada a la lista parcial [EN]  View associated with the partial list
     */
    void notifyItemChanged(int index, int viewType);

    /**
     * Notificca cambios generales en una lista parcial
     * <p>
     * [EN]  Notify general changes in a partial list
     *
     * @param viewType Vista asociada a la lista parcial [EN]  View associated with the partial list
     */
    void notifyDataSetChanged(int viewType);

    /**
     * Notifica la adicción de todos los elementos de una lista parcial
     * <p>
     * Notifica la adicción de todos los elementos de una lista parcial
     *
     * @param count    cantidad de registros totales en la lista parcial [EN]  number of total records in the partial list
     * @param viewType Vista asociada a la lista parcial [EN]  View associated with the partial list
     */
    void notifyDataAdd(int count, int viewType);

    /**
     * Notifica la eleimnación de todos los registros de una lista parcial
     * <p>
     * [EN]  Notifies the selection of all records in a partial list
     *
     * @param count    cantidad de registros totales en la lista parcial [EN]  number of total records in the partial list
     * @param viewType Vista asociada a la lista parcial [EN]  View associated with the partial list
     */
    void notifyDataRemoved(int count, int viewType);
}
