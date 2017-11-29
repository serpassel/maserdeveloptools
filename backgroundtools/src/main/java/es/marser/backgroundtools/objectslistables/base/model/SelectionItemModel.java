package es.marser.backgroundtools.objectslistables.base.model;

import android.os.Parcelable;

import java.util.List;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de modelo de selección con acceso a elementos de una lista
 *         <p>
 *         [EN]  Definition of a selection model with access to elements of a list
 */

@SuppressWarnings("unused")
public interface SelectionItemModel<T extends Parcelable> {
    /**
     * Elimina los elementos seleccionados
     * <p>
     * [EN]  Delete selected items
     *
     * @return lista de elementos seleccionados [EN]  list of selected items
     */
    List<T> removeSelectedItems();

    /**
     *
     * @return devuelve los elementos seleccionados [EN]  returns the selected items
     */
    List<T> getSelectds();
    /**
     * Devuelve el último registro pulsado si está seleccionado o el primer registro seleccionado
     * <p>
     * [EN]  Returns the last recorded record if selected or the first selected record
     *
     * @return Objeto seleccionado [EN]  Selected Object
     */
    T getItemSelected();
}
