package es.marser.backgroundtools.objectslistables.base.model;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Intercambiador entre posición plana e índice
 *         <p>
 *         [EN]  Exchanger between flat position and index
 */

@SuppressWarnings("unused")
public interface TogglePositionIndex {

    /**
     * Devuelve la posición plana en un lista global para un índice
     * <p>
     * [EN]  Return the flat position in a global list for an index
     *
     * @param index    Índice parcial [EN]  Partial index
     * @param viewType Tipo de vista asociada [EN]  Type of associated view
     * @return Posición plana [EN]  Flat position
     */
    int flatPos(int index, int viewType);

    /**
     * Devuelve el índece parcial partiendo de la posición plana global
     * <p>
     * [EN]  Returns the partial independence starting from the global flat position
     *
     * @param flaPos Posición plana [EN]  Flat position
     * @param viewType Tipo de vista asociada [EN]  Type of associated view
     * @return Índice parcial [EN]  Partial index
     */
    int indexPos(int flaPos, int viewType);
}
