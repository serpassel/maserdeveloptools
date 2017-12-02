package es.marser.backgroundtools.listables.base.model;

import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de objeto de con controladora de expansión
 *         <p>
 *         [EN]  Definition of object of with expansion controller
 */

@SuppressWarnings("unused")
public interface ExpandItemsManager {

    /**
     *
     * @return Devuelve el objeto de control de expansión de objetos [EN]  Returns the object expansion control object
     */
    @Nullable
    ExpandItemsController getExpandItemsController();
}
