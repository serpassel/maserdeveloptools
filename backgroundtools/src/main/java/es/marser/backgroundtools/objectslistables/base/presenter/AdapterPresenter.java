package es.marser.backgroundtools.objectslistables.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definción del presentador de listas
 *         <p>
 *         [EN]  List presenter definition
 */

public interface AdapterPresenter {

    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    void load(@Nullable Bundle bundle);
}
