package es.marser.backgroundtools.listables.base.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Defición de objeto que indica que maneja listas
 *         <p>
 *         [EN]  Object defición indicating that it handles lists
 */

@SuppressWarnings("unused")
public interface AdapterItemsManager<T extends Parcelable> {
    /**
     * @return devuelve el gestor de lectura y escritura asignado al manejador
     * <p>
     * [EN]  returns the read and write manager assigned to the handler
     */
    @Nullable
    AdapterItemsController<T> getAdapterItemsController();
}
