package es.marser.backgroundtools.objectslistables.base.listeners;

/**
 * @author sergio
 *         Created by Sergio on 16/08/2017.
 *         Oyente de cambios en una lista de elementos
 *         <p>
 *         [EN]  Listener of changes in a list of elements
 */

public interface OnItemChangedListener {
    /**
     * Cambios en un elemento
     * <p>
     * [EN]  Changes in an element
     *
     * @param position posición del elemento modificado [EN]  position of the modified element
     */
    void onItemChaged(int position);

    /**
     * Cambios en la selección de elementos
     * <p>
     * [EN]  Changes in item selection
     */
    void onSelectionChanged();
}
