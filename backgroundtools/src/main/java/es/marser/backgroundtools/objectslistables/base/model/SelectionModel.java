package es.marser.backgroundtools.objectslistables.base.model;

import java.util.ArrayList;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de acciones de lectura y escritura de estado de selección
 *         <p>
 *         [EN]  Definition of read and write actions of selection status
 */

@SuppressWarnings("unused")
public interface SelectionModel {

    /**
     * Indica el estado de selección para la posición  indicada
     * <p>
     * [EN]  Indicates the selection status for the indicated position
     *
     * @param position posición a comprobar
     * @return verdadero si la posición se encuentra selecionada [EN]  true if the position is selected
     */
    boolean get(int position);

    /**
     * Elimima la selección para la posición indicada
     * <p>
     * [EN]  Delete the selection for the indicated position
     *
     * @param position posición de selección
     *                 [EN]  selection position
     */
    void delete(int position);

    /**
     * Limpia la lista de selección
     * <p>
     * [EN]  Clear selection list
     */
    void clear();

    /**
     * Comprueba si hay registros de selección seleccionados
     * <p>
     * [EN]  Check if selected selection records
     *
     * @return verdadero si la lista está vacía [EN]  true if the list is empty
     */
    boolean isEmptySelected();

    /**
     * Devuelve la lista de las posiciones seleccionadas
     * <p>
     * [EN]  Returns the list of selected positions
     *
     * @return Listas con las posiciones seleccionadas [EN]  Lists with selected positions
     */
    ArrayList<Integer> getIdSelecteds();

    /**
     * Marca el estado de selección de una posición
     * <p>
     * [EN]  Mark the selection state of a position
     *
     * @param id    posición a modificar [EN]  position to modify
     * @param value valor de estado para la posición [EN]  status value for position
     */
    void setSelected(int id, boolean value);

    /**
     * Predefinción del estado de selección para la posición indicada
     * <p>
     * [EN]  Default selection state for the indicated position
     *
     * @param id    posición a predefinir [EN]  position to preset
     * @param value valor de preselección para la posición indicada [EN]  preset value for the indicated position
     */
    void inputSelected(int id, boolean value);

    /**
     * Devuelve la posición del último elemento pulsado si está seleccionado o el primer registro seleccionado
     * <p>
     * [EN]  Returns the position of the last element pressed if selected or the first selected record
     *
     * @return posición de la última posición pulsada o seleccionada -1 en caso de no existir
     * [EN]  position of the last position pressed or selected -1 if there is no
     */
    int getIdSelected();

    /**
     * Seleccionar todos los elementos
     * <p>
     * [EN]  Select all items
     *
     * @param count Número de registros a seleccionar [EN]  Number of records to select
     */
    void selectedAll(int count);

    /**
     * Deseleccionar todos los registros
     * <p>
     * [EN]  Deselect all records
     */
    void deselectedAll();

    /**
     * Variable de almacenamiento de último cambios de selección
     * <p>
     * [EN]  Storage variable of last selection changes
     *
     * @return devuelve la penultima posición seleccionada [EN]  returns the last selected position
     */
    int getLastposition();

    /**
     * Establece la penúltima posición cuya selección ha sufrido cambios
     * <p>
     * [EN]  Set the penultimate position whose selection has undergone changes
     *
     * @param lastposition valor de la penúltima modificada [EN]  value of the penultimate modified
     */
    void setLastposition(int lastposition);

    /**
     * Variable de almacenamiento de último cambios de selección
     * <p>
     * [EN]  Storage variable of last selection changes
     *
     * @return ultima posición seleccionada o -1 si no existe [EN]  last selected position or -1 if it does not exist
     */
    int getPosition();

    /**
     * Establece la última posición cuya selección ha sufrido cambios
     * <p>
     * [EN]  Set the ultimate position whose selection has undergone changes
     * @param position Valor de la última posición. -1 para limpiar [EN]  Value of the last position.  -1 to clean
     */
    void setPosition(int position);

}
