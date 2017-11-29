package es.marser.backgroundtools.objectslistables.base.model;

import android.support.annotation.NonNull;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición para objetos que disponen de un intercambiador entre posición plana global e índice parcial
 *         <p>
 *         [EN]  Definition for objects that have an exchanger between global flat position and partial index
 */

@SuppressWarnings("unused")
public interface TogglePositionIndexManager {
    /**
     * Devuelve el objeto intercambiador de posiciones
     * <p>
     * [EN]  Returns the item exchanger object
     *
     * @return Objeto intercambiador de posiciones [EN]  Position exchanger object
     * {@link TogglePositionIndex}
     */
    @NonNull
    TogglePositionIndex getTogglePositionIndex();
}
