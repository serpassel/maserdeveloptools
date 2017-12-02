package es.marser.backgroundtools.listables.base.model;

import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición que indica que la clase tiene un controlador de selección de una lista de elementos
 *         <p>
 *         [EN]  Definition that indicates that the class has a controller that selects a list of elements
 */

@SuppressWarnings("unused")
public interface SelectionItemsManager {
    /**
     *
     * @return Devuelve la controladora de selección [EN]  Returns the selection controller
     */
    @Nullable
    SelectionItemsController getSelectionItemsController();
}
