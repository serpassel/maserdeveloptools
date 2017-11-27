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
     * Agregado nuevo elemento
     * <p>
     * [EN]  Added new item
     *
     * @param position posición del nuevo elemento [EN]  position of the new element
     */
    void onAddItem(int position);

    /**
     * Eliminado un elemento de la lista
     * <p>
     * [EN]  Removed an item from the list
     *
     * @param position posición eliminada [EN]  position removed
     */
    void onRemoveItem(int position);

    /**
     * Insertado un objeto en la posición indicada
     * <p>
     * [EN]  Inserted an object in the indicated position
     *
     * @param position posición de insercción [EN]  insertion position
     */
    void onInsertItem(int position);

    /**
     * Cambios en un elemento
     * <p>
     * [EN]  Changes in an element
     *
     * @param position posición del elemento modificado [EN]  position of the modified element
     */
    void onItemChaged(int position);

    /**
     * Eliminados todos los elementos
     * <p>
     * [EN]  Deleted all items
     */
    void removeAllItems();

    /**
     * Agregar una colección de elementos
     * <p>
     * [EN]  Modifications in all elements
     */
    void onAddAll();

    /**
     * Cambios en la selección de elementos
     * <p>
     * [EN]  Changes in item selection
     */
    void onSelectionChanged();
}
