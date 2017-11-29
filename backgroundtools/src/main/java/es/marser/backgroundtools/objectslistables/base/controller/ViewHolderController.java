package es.marser.backgroundtools.objectslistables.base.controller;

import es.marser.backgroundtools.enums.ListExtra;

/**
 * @author sergio
 *         Created by sergio on 22/10/17.
 *         Conjunto de controladores para el colector de vistas de una una lista
 *         <p>
 *         [EN]  Set of drivers for the view manifold from a list
 */

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface ViewHolderController<T> extends OnViewHolderClickListener<T> {
    /**
     * Indica si la vista esta expandida
     * <p>
     * [EN]  Indicates if the view is expanded
     *
     * @param position posición de la vista [EN]  position of the view
     * @return verdadero si la vista está expandida [EN]  true if the view is expanded
     */
    boolean isExpaned(int position);

    /**
     * Indica si la vista esta seleccionada
     * <p>
     * [EN]  Indicates whether the view is selected
     *
     * @param position posición de la vista [EN]  position of the view
     * @return verdadero si la vista está seleccionada [EN]  true if the view is selected
     */
    boolean isSelected(int position);

    /**
     * Recupera el objeto generíco de un elemento de la lista para una posición
     * <p>
     * [EN]  Retrieves the generic object of an item from the list for a position
     *
     * @param position posición del elemento [EN]  position of the element
     * @return Objeto genérico [EN]  Generic object
     */
    T getItemAt(int position);

    /**
     * Setea el estado de la selección
     * <p>
     * [EN]  Set the state of the selection
     *
     * @param position posición plana en el adapter [EN]  flat position on the adapter
     * @param value    valor del estado de selección [EN]  Selection status value
     */
    void setSelected(int position, boolean value);
}
