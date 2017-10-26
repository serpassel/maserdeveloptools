package es.marser.backgroundtools.objectslistables.simple.controller;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.marser.backgroundtools.objectslistables.simple.listeners.OnItemChangedListener;

/**
 * @author sergio
 *         Created by Sergio on 30/04/2017.
 *         Lista de elementos
 *         Clase  extendida de la ArrayList, que incluye notifiaciones a oyentes
 *         <p>
 *         <ul>
 *         <il>Manejo de elementos</il>
 *         <il>Acceso a variables</il>
 *         </ul>
 *         <p>
 *         <p>
 *         <p>
 *         [EN]
 *         List of items
 *         Extended class of the ArrayList, which includes notifications to listeners
 *         <ul>
 *         <il>Element management</il>
 *         <il>Access to variables</il>
 *         </ul>
 * @see es.marser.backgroundtools.recyclerviews.simple.adapter.BaseBindAdapterList
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ArrayListController<T> extends ArrayList<T> {

    /*Variable oyente de modificaciones de slección*/
    protected OnItemChangedListener onChangedListListener;

    //CONSTRUCTORS____________________________________________________________________________________________

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ArrayListController(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    @SuppressWarnings("unchecked")
    public ArrayListController(@NonNull Collection c) {
        super(c);
    }

    public ArrayListController() {
    }

    //ELEMENT MANAGEMENT____________________________________________________________________________________

    /**
     * Agrega una lista de elementos
     * <p>
     * [EN]  Add a list of items
     *
     * @param items lista de elementos [EN]  list of elements
     */
    public void addAllItems(List<T> items) {
        if (items != null) {
            /*Agregar la lista de registros [EN]  Add the list of records*/
            addAll(items);

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (onChangedListListener != null) {
                onChangedListListener.onAllChanged();
            }
        }
    }

    /**
     * Sustituye todos los registros
     * <p>
     * [EN]  Replaces all records
     *
     * @param items nueva lista de registros [EN]  new list of records
     */
    public void replaceAllItems(List<T> items) {
        if (items != null) {
            setItems(items);

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (onChangedListListener != null) {
                onChangedListListener.removeAllItems();
            }
        }
    }

    /**
     * Recuperar el elemento de una posición
     * <p>
     * [EN]  Retrieve the item from a position
     *
     * @param position posición de búsqueda
     * @return Objeto genérico en la posición indicado [EN]  Generic object in the indicated position
     */
    public T getItemAt(int position) {
 /*Si la posición está fuera de rango terminamos el proceso
 [EN]  If the position is out of range we finish the process*/
        if ((position > -1 && position < size())) {
            return get(position);
        }
        return null;
    }

    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    public void removeAllITems() {
        /*Limpiar lista [EN]  Clean list*/
        clear();
          /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onChangedListListener != null) {
            onChangedListListener.removeAllItems();
        }
    }

    /**
     * Eliminar un elemento por su posición
     * <p>
     * [EN]  Delete an item by its position
     *
     * @param position posicion del elemento [EN]  position of the element
     */
    public void removeItem(int position) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((position > -1 && position < size())) {
         /*Eliminar elemento [EN]  Delete item*/
            remove(position);

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (onChangedListListener != null) {
                onChangedListListener.onRemoveItem(position);
            }
        }
    }

    /**
     * Agrega elemento al final de la lista
     * <p>
     * [EN]  Add item to end of list
     *
     * @param item nuevo objeto genérico [EN]  new generic object
     */
    public void addItem(T item) {
        if (item != null) {
        /*Agregar elemento [EN]  Add Item*/
            add(item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (onChangedListListener != null) {
                onChangedListListener.onAddItem(size() - 1);
            }
        }
    }

    /**
     * Agregar un registro en una posición definida
     * <p>
     * [EN]  Add a record in a defined position
     *
     * @param position posición para insertar elemento [EN]  position to insert element
     * @param item     Nuevo elemento a insertar [EN]  New item to insert
     */
    public void insertItem(int position, T item) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((position > -1 && position < size()) || item == null) {
        /*Agregar elemento [EN]  Add Item*/
            add(position, item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (onChangedListListener != null) {
                onChangedListListener.onAddItem(position);
            }
        }
    }

    /**
     * Actualiza el elemento de la posición indicada
     * <p>
     * [EN]  Update item from indicated position
     *
     * @param position posición sobre la que se actualiza [EN]  position on which it is updated
     * @param item     elemento actualizado [EN]  item updated
     */
    public void updateItem(Integer position, T item) {

   /*Validar entrada [EN]  Validate entry*/
        if (position != null && item != null && position > -1 && position < size()) {
            /*Actualizar registro [EN]  Update record*/
            set(position, item);
       /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (onChangedListListener != null) {
                onChangedListListener.onItemChaged(position);
            }
        }
    }

    //ACCESS TO VARIABLES_________________________________________________________________________________________

    /*Métodos de acceso de los datos de la variable [EN]  Methods of accessing the variable data */

    /**
     * Devolver lista de elementos
     * No se notifica
     * <p>
     * [EN]  Return list of items
     * [EN]  Not notified
     *
     * @return Lista de elementos [EN]  List of items
     */
    public List<T> getItems() {
        return this;
    }

    /**
     * Introducir una colección de elementos
     * No se notifica
     * <p>
     * [EN]  Enter a collection of elements
     * [EN]  Not notified
     *
     * @param items nuevos elementos [EN]  new elements
     */
    public void setItems(List<T> items) {
        if (items != null) {
        /*Limpiar la lista [EN]  Clear list*/
            clear();
        /*Agregar nuevos elementos [EN]  Add new items*/
            addAll(items);
        }
    }

    /*Variables de oyentes [EN]  Listener Variables*/
    public ArrayListController setOnChangedListListener(OnItemChangedListener onChangedListListener) {
        this.onChangedListListener = onChangedListListener;
        return this;
    }

    public ArrayListController removeOnChangedListListener() {
        this.onChangedListListener = null;
        return this;
    }

}
