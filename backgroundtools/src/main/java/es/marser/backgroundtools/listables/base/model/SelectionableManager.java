package es.marser.backgroundtools.listables.base.model;

import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de con objetos de patrón seleccionable
 *         <p>
 *         [EN]  Definition of with selectable pattern objects
 */

@SuppressWarnings("unused")
public interface SelectionableManager {

    /**
     *
     * @param cls clase del objeto seleccionable [EN]  class of the selectable object
     * @return Objeto Seleccionable [EN]  Selectable object
     */
    @Nullable
    Selectionable getSelectionable(@Nullable Class cls);

}
