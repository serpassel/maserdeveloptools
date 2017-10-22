package es.marser.backgroundtools.recyclerviews.simple.controllers;

import android.view.View;

/**
 * @author sergio
 *         Created by sergio on 22/10/17.
 *         Conjunto de controladores para el colector de vistas de una una lista
 *         <p>
 *         [EN]  Set of drivers for the view manifold from a list
 */

@SuppressWarnings("UnusedReturnValue")
public interface ViewHolderController<T> {

    /**
     * Indica si la vista esta expandida
     * <p>
     * [EN]  Indicates if the view is expanded
     *
     * @return verdadero si la vista está expandida [EN]  true if the view is expanded
     */
    boolean isExpaned(int position);

    /**
     * Indica si la vista esta seleccionada
     * <p>
     * [EN]  Indicates whether the view is selected
     *
     * @return verdadero si la vista está seleccionada [EN]  true if the view is selected
     */
    boolean isSelected(int position);

    /**
     * Pulsación sencilla sobre un elemento
     * <p>
     * [EN]  Single click on an element
     *
     * @param view     vista pulsada [EN]  pulsed view
     * @param position posición de la vista en el adapter [EN]  position of the view on the adapter
     */
    void onClick(View view, int position);

    /**
     * Pulsación prolongada sobre un elemento
     * <p>
     * [EN]  Long press on an element
     *
     * @param view     vista pulsada [EN]  pulsed view
     * @param position posición de la vista en el adapter [EN]  position of the view on the adapter
     */
    boolean onLongClick(View view, int position);

    /**
     * Recupera el objeto generíco de un elemento de la lista para una posición
     * <p>
     * [EN]  Retrieves the generic object of an item from the list for a position
     *
     * @param position posición del elemento [EN]  position of the element
     * @return Objeto genérico [EN]  Generic object
     */
    T getItemAt(int position);
}
