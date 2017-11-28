package es.marser.backgroundtools.objectslistables.complex.models;

import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.List;

import es.marser.async.TaskLoadingResult;

/**
 * @author sergio
 *         Created by Sergio on 01/05/2017.
 *         Modelo de objeto con listas anidadas
 *         <ul>
 *         <il>Constructores</il>
 *         <il>Acceso a elementos globales</il>
 *         <il>Manipulación de elementos, unitarios</il>
 *         <il>Acceso a variables</il>
 *         </ul>
 *         <p>
 *         <p>
 *         <p>
 *         [EN]  Object model with nested lists
 *         <ul>
 *         <il>Constructors</il>
 *         <il>Access to global elements</il>
 *         <il>Manipulation of elements, unit</il>
 *         <il>Access to variables</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public abstract class ListableItems<T> extends BaseObservable {
    protected ArrayList<T> items;

    //CONSTRUCTORS________________________________________________________________________________
    public ListableItems() {
        items = new ArrayList<>();
    }

    public ListableItems(List<T> list) {
        this();
        if (list != null) {
            items.addAll(list);
        }
    }

    //ACCESS TO ELEMENTS_____________________________________________________________________________________

    /**
     * Número de elementos anidados en el objeto
     * <p>
     * [EN]  Number of elements nested in the object
     *
     * @return número de elementos anidados [EN]  number of nested elements
     */
    public int getItemCount() {
        if (items.isEmpty()) {
            items.addAll(getItems(null));
        }
        return items.size();
    }

    /**
     * Recuperación de elementos síncrona
     * <p>
     * [EN]  Synchronous Element Recovery
     *
     * @return Lista de elementos anidados [EN]  List of nested elements
     */
    public List<T> getItems() {
        return getItems(null);
    }

    /**
     * Recuperación de elementos asíncrona
     * <p>
     * Metodo para sobreescribir en caso de varibles con arreglos de elementos separados con un marcador
     * <p>
     * [EN]  Asynchronous Element Recovery
     * <p>
     * Method for overwriting in case of varibles with arrangements of separate elements with a marker
     *
     * @param onResult Variable de recuparación de datos asíncrona [EN]  Asynchronous Data Recovering Variable
     * @return Lista de elementos anidados [EN]  List of nested elements
     */
    @SuppressWarnings("All")
    public List<T> getItems(TaskLoadingResult<T> onResult) {
        if (onResult != null) {
            onResult.onStart(null);
            for (T t : items) {
                onResult.onUpdate(t);
            }
            onResult.onFinish(null);
        }
        return items;
    }

    /**
     * Carga de elementos en el controlador
     * <p>
     * [EN]  Load items into the controller
     */
    protected void loadData() {
        /*Comprobar si la lista está vacía [EN]  Check if the list is empty*/
        if (items.isEmpty()) {
            items.addAll(getItems());
        }
    }

    /**
     * Reemplazar los elementos anidados
     * <p>
     * [EN]  Replace nested elements
     *
     * @param list Nueva listas de elementos [EN]  New item lists
     */
    public void setItems(List<T> list) {
        
        items.clear();
        items.addAll(list);
    }

    /**
     * Eliminar elementos anidados
     * <p>
     * [EN]  Delete nested elements
     *
     * @param todelete Listado de posiciones de los elementos a eliminar [EN]  List of items to be deleted
     * @see #loadData()
     */
    public void deleteItems(List<Integer> todelete) {
      /*Cargar datos [EN]  Load data*/
        loadData();
        /*Eliminar los elementos de la lista [EN]  Remove items from the list*/
        for (int i : todelete) {
            items.remove(i);
        }
        /*Grabar datos [EN]  Save data*/
        save();
    }

    /**
     * Grabación de elementos
     * <p>
     * [EN]  Recording Elements
     */
    public void save() {
        setItems(items);
    }

//MANIPULATION OF ELEMENTS, UNIT_____________________________________________________________________

    /**
     * Devuleve un objeto genérico del eleento correspondiente con la posición
     * <p>
     * [EN]  Returns a generic object of the corresponding element with the position
     *
     * @param position posición del elemento [EN]  position of the element
     * @return Objeto genérico [EN]  Generic object
     */
    public T getItem(int position) {
        /*Recargar la lista [EN]  Reload the list*/
        loadData();
        /*Comprobar si la posición está dentro del rango de elementos
        * [EN]  Check if the position is within the range of elements*/
        if (position > -1 && position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    /**
     * Devuelve el objeto genérico correspondiente al último elemento de la lista
     * <p>
     * [EN]  Returns the generic object corresponding to the last item in the list
     *
     * @return Objeto genérico [EN]  Generic object
     */
    public T getLastItem() {
        return getItem(items.size() - 1);
    }

    /**
     * Agrega un elemento al final de la lista
     * <p>
     * [EN]  Adds an item to the bottom of the list
     *
     * @param item Nuevo elemento genérico [EN]  New Generic Element
     */
    public void addItem(T item) {
        /*Recargar la lista [EN]  Reload the list*/
        loadData();
        /*Agregar elemento a la lista [EN]  Add item to list*/
        items.add(item);
        /*Grabar cambios [EN]  Record Changes*/
        save();
    }

    /**
     * Eliminar un elemento de una posición
     * <p>
     * [EN]  Delete an item from a position
     *
     * @param position posición del elemento a eliminar [EN]  position of the item to be deleted
     */
    public void deleteItem(int position) {
          /*Recargar la lista [EN]  Reload the list*/
        loadData();
        /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if (position > -1 && position < items.size()) {

        /*Eliminar elemento de la lista [EN]  Remove item from the list*/
            items.remove(position);
        /*Grabar cambios [EN]  Record Changes*/
            save();
        }
    }

    /**
     * Actualizar el elemento de una posición
     * <p>
     * [EN]  Update item in a position
     *
     * @param position posición del elemento [EN]  position of the element
     * @param item     Objeto generíco actualizado [EN]  Updated general purpose object
     */
    public void updateItem(int position, T item) {
           /*Recargar la lista [EN]  Reload the list*/
        loadData();
        /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((position > -1 && position < items.size()) || item == null) {

        /*Actualizar el elemento [EN]  Update item*/
            items.set(position, item);
        /*Grabar cambios [EN]  Record Changes*/
            save();
        }
    }

    /**
     * Insertar un elemento en una posición
     * <p>
     * [EN]  Insert an element in a position
     *
     * @param position posición de insercción [EN]  insertion position
     * @param item     Elemento genérico [EN]  Generic element
     */
    public void insertItem(int position, T item) {
            /*Recargar la lista [EN]  Reload the list*/
        loadData();
        /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((position > -1 && position < items.size()) || item == null) {
        /*Insertar el elemento en la posición indicada [EN]  Insert the element in the indicated position*/
            items.add(position, item);
        /*Grabar cambios [EN]  Record Changes*/
            save();
        }
    }



}
