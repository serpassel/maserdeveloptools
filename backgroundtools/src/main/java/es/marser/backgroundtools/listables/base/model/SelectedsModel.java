package es.marser.backgroundtools.listables.base.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de modelo de selección con acceso a elementos de una lista
 *         <p>
 *         [EN]  Definition of a selection model with access to elements of a list
 */

@SuppressWarnings("unused")
public interface SelectedsModel<T extends Parcelable> {
    /**
     * Elimina los elementos seleccionados
     * <p>
     * [EN]  Delete selected items
     *
     * @return lista de elementos seleccionados [EN]  list of selected items
     */
    @Nullable
    List<T> removeSelectedItems();

    /**
     *
     * @return devuelve los elementos seleccionados [EN]  returns the selected items
     */
    @Nullable
    List<T> getSelectds();
    /**
     * Devuelve el último registro pulsado si está seleccionado o el primer registro seleccionado
     * <p>
     * [EN]  Returns the last recorded record if selected or the first selected record
     *
     * @return Objeto seleccionado [EN]  Selected Object
     */
    @Nullable
    T getItemSelected();
}
