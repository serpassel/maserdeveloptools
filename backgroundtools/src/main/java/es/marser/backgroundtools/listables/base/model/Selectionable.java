package es.marser.backgroundtools.listables.base.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.enums.ListExtra;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de objeto seleccionable. Define el modo de selección
 *         <p>
 *         [EN]  Definition of selectable object.  Defines the selection mode
 */

@SuppressWarnings("unused")
public interface Selectionable {
    /**
     * @return Modo de selección de la lista [EN]  Selection mode of the list
     */
    @Nullable
    ListExtra getSelectionmode(@Nullable Integer viewtype);

    /**
     * Filjar el modo de selección de la lista
     * <p>
     * [EN]  Filtering the mode selection mode of the list
     *
     * @param selectionmode Modo de slección de la lista
     */
    void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode);
}
