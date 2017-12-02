package es.marser.backgroundtools.listables.base.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de clase que contiene un Modelo de selección de elementos
 *         <p>
 *         [EN]  Class definition that contains an Element Selection Model
 *         {@link SelectedsModel}
 */

@SuppressWarnings("unused")
public interface SelectedsModelManager<T extends Parcelable> {
    /**
     * Devuelve el contralodor de elementos seleccionados
     * <p>
     * [EN]  Returns the selected elements controller
     *
     * @return Devuelve el contralodor de elementos seleccionados [EN]  Returns the selected elements controller
     */
    @Nullable
    SelectedsModel<T> getSelectedsModel();
}
