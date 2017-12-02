package es.marser.backgroundtools.listables.base.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de controladora de estado de expansión de una lista de objetos
 *         <p>
 *         [EN]  Definition of expansion state controller of a list of objects
 */

@SuppressWarnings("unused")
public interface ExpandItemsController {

    /**
     *
     * @param id índice del objeto [EN]  object index
     * @param value valor de estado para expansión [EN]  state value for expansion
     */
    void setExpand(int id, boolean value);

    /**
     * Comprueba el estado de una posición
     * <p>
     * [EN]  Check the status of a position
     *
     * @param index index
     * @return verdadero si está expandido [EN]  true if expanded
     */
    boolean isExpaned(int index);

    /**
     * Colapsa todas las vistas
     * <p>
     * [EN]  Collapse all views
     */
    void collapseAll();

    /**
     * Eliminar la varible de expansión de una posición
     * <p>
     * [EN]  Remove the expansion variable from a position
     *
     * @param index posición a eliminar [EN]  position to be deleted
     */
    void delete(int index);

    /**
     * Invertir el estad de expansión de una posición
     * <p>
     * [EN]  Invert the expansion state of a position
     *
     * @param id posición a invertir [EN]  position to invest
     * @return valor del nuevo estado de la posición [EN]  value of the new status of the position
     */
    boolean toggleExpand(int id);

    /**
     * Limpiar selección. Notificada
     * <p>
     * [EN]  Clear selection.  Notified
     */
    void deselectedAll();

    /**
     * Estado de expansión de una posición
     * <p>
     * [EN]  Expansion status of a position
     *
     * @param index index consultada [EN]  consulted position
     * @return verdadero si la vista está expandida [EN]  true if the view is expanded
     */
    boolean get(int index);

    /**
     * Limpiar la lista de estados de expansión
     * <p>
     * [EN]  Clear list of expansion states
     */
    void clear();

    /**
     * Devuelve la lista de las posiciones expandidas
     * <p>
     * [EN]  Returns the list of expanded items
     *
     * @return Listas con las posiciones expandidas [EN]  Lists with expanded positions
     */
    @NonNull
    ArrayList<Integer> getIdExpaned();
}
