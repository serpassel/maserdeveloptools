package es.marser.backgroundtools.presenters.base;

import android.os.Parcelable;

import java.util.List;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de acciones de lectura y escritura en listas de objetos
 *         <p>
 *         [EN]  Definition of read and write actions in object lists
 */

@SuppressWarnings("unused")
public interface ListCrud<T extends Parcelable> {
    //CRUD_____________________________________________________________________________

    /**
     * Agrega una lista de elementos
     * <p>
     * [EN]  Add a list of items
     *
     * @param items lista de elementos [EN]  list of elements
     */
    void addAll(List<T> items);

    /**
     * Agrega elemento al final de la lista
     * <p>
     * [EN]  Add item to end of list
     *
     * @param item nuevo objeto genérico [EN]  new generic object
     */
    void add(T item);

    /**
     * Agregar un registro en una posición definida
     * <p>
     * [EN]  Add a record in a defined position
     *
     * @param position posición para insertar elemento [EN]  position to insert element
     * @param item     Nuevo elemento a insertar [EN]  New item to insert
     */
    void add(int index, T item);

    /**
     * Eliminar un elemento por su posición
     * <p>
     * [EN]  Delete an item by its position
     *
     * @param index posicion del elemento [EN]  position of the element
     */
    void remove(int index);

    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    void clear();

    /**
     * Actualiza el elemento de la posición indicada
     * <p>
     * [EN]  Update item from indicated position
     *
     * @param index posición sobre la que se actualiza [EN]  index on which it is updated
     * @param item  elemento actualizado [EN]  item updated
     */
    void set(Integer index, T item);

    /**
     * Sustituye todos los registros
     * <p>
     * [EN]  Replaces all records
     *
     * @param items nueva lista de registros [EN]  new list of records
     */
    void replace(List<T> items);

    /**
     * @return Número de elementos de la lista [EN]  Number of items in the list
     */
    int size();

    /**
     *
     * @return verdadero si la vista está vacía [EN]  true if the view is empty
     */
    boolean isEmpty();
}
