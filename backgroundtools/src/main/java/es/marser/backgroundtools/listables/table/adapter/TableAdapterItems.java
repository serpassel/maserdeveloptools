package es.marser.backgroundtools.listables.table.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.List;

import es.marser.backgroundtools.definition.Selectable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definición de acciones de lectura y escritura en listas de objetos
 *         <p>
 *         [EN]  Definition of read and write actions in object lists
 */

@SuppressWarnings("unused")
public interface TableAdapterItems<H extends Parcelable, B extends Parcelable> {
    //CRUD_____________________________________________________________________________

    /**
     * Devuelve la lista de elementos del título
     * <p>
     * [EN]  Returns the list of title elements
     *
     * @return Lista de elementos [EN]  List of items
     */
    @Nullable
    List<Selectable> getTitleItems();

    /**
     * Devuelve la lista de elementos de cabecera
     * <p>
     * [EN]  Returns the list of header elements
     *
     * @return Lista de elementos [EN]  List of items
     */
    @Nullable
    List<H> getHeadItems();

    /**
     * Devuelve la lista de elementos del cuerpo
     * <p>
     * [EN]  Returns the list of body elements
     *
     * @return Lista de elementos [EN]  List of items
     */
    @Nullable
    List<B> getBodyItems();

    /**
     * Devuelve el registro para la posición indicada
     * <p>
     * [EN]  Returns the record for the indicated position
     *
     * @param index índice del elemento
     */
    @Nullable
    Selectable getTitle(int index);

    /**
     * Devuelve el registro para la posición indicada
     * <p>
     * [EN]  Returns the record for the indicated position
     *
     * @param index índice del elemento
     */
    @Nullable
    H getHead(int index);

    /**
     * Devuelve el registro para la posición indicada
     * <p>
     * [EN]  Returns the record for the indicated position
     *
     * @param index índice del elemento
     */
    @Nullable
    B getBody(int index);

    /**
     * Agrega una lista de elementos de tipo titulo
     * <p>
     * [EN]  Add a list of title elements
     *
     * @param items lista de elementos [EN]  list of elements
     */
    void addAllTitle(@Nullable List<Selectable> items);

    /**
     * Agrega una lista de elementos de cabecera
     * <p>
     * [EN]  Add a list of header elements
     *
     * @param items lista de elementos [EN]  list of elements
     */
    void addAllHead(@Nullable List<H> items);

    /**
     * Agrega una lista de elementos de cuerpo
     * <p>
     * [EN]  Add a list of body elements
     *
     * @param items lista de elementos [EN]  list of elements
     */
    void addAllBody(@Nullable List<B> items);

    /**
     * Agrega elemento al final de la lista de títulos
     * <p>
     * [EN]  Add item to the end of the title list
     *
     * @param item nuevo objeto genérico [EN]  new generic object
     */
    void addTitle(@Nullable Selectable item);

    /**
     * Agrega elemento al final de la lista de cabeceras
     * <p>
     *[EN]  Add item to the end of the list of headers
     *
     * @param item nuevo objeto genérico [EN]  new generic object
     */
    void addHead(@Nullable H item);

    /**
     * Agrega elemento al final de la lista de cuerpo
     * <p>
     * [EN]  Add item to the end of the body list
     *
     * @param item nuevo objeto genérico [EN]  new generic object
     */
    void addBody(@Nullable B item);

    /**
     * Agregar un registro en una posición definida
     * <p>
     * [EN]  Add a record in a defined position
     *
     * @param index indice para insertar elemento [EN]  position to insert element
     * @param item     Nuevo elemento a insertar [EN]  New item to insert
     */
    void addTitle(int index, @Nullable Selectable item);

    /**
     * Agregar un registro en una posición definida
     * <p>
     * [EN]  Add a record in a defined position
     *
     * @param index indice para insertar elemento [EN]  position to insert element
     * @param item     Nuevo elemento a insertar [EN]  New item to insert
     */
    void addHead(int index, @Nullable H item);

    /**
     * Agregar un registro en una posición definida
     * <p>
     * [EN]  Add a record in a defined position
     *
     * @param index indice para insertar elemento [EN]  position to insert element
     * @param item     Nuevo elemento a insertar [EN]  New item to insert
     */
    void addBody(int index, @Nullable B item);

    /**
     * Actualiza el elemento de la posición indicada
     * <p>
     * [EN]  Update item from indicated position
     *
     * @param index posición sobre la que se actualiza [EN]  index on which it is updated
     * @param item  elemento actualizado [EN]  item updated
     */
    void setTitle(@Nullable Integer index, @Nullable Selectable item);

    /**
     * Actualiza el elemento de la posición indicada
     * <p>
     * [EN]  Update item from indicated position
     *
     * @param index posición sobre la que se actualiza [EN]  index on which it is updated
     * @param item  elemento actualizado [EN]  item updated
     */
    void setHead(@Nullable Integer index, @Nullable H item);

    /**
     * Actualiza el elemento de la posición indicada
     * <p>
     * [EN]  Update item from indicated position
     *
     * @param index posición sobre la que se actualiza [EN]  index on which it is updated
     * @param item  elemento actualizado [EN]  item updated
     */
    void setBody(@Nullable Integer index, @Nullable B item);

    /**
     * Sustituye todos los registros
     * <p>
     * [EN]  Replaces all records
     *
     * @param items nueva lista de registros [EN]  new list of records
     */
    void replaceTitle(@Nullable List<Selectable> items);

    /**
     * Sustituye todos los registros
     * <p>
     * [EN]  Replaces all records
     *
     * @param items nueva lista de registros [EN]  new list of records
     */
    void replaceHead(@Nullable List<H> items);

    /**
     * Sustituye todos los registros
     * <p>
     * [EN]  Replaces all records
     *
     * @param items nueva lista de registros [EN]  new list of records
     */
    void replaceBody(@Nullable List<B> items);

    /**
     * Eliminar un elemento por su posición
     * <p>
     * [EN]  Delete an item by its position
     *
     * @param flatpos posicion del elemento [EN]  position of the element
     */
    void remove(int flatpos);

    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    void clear();

    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    void clearTitle();

    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    void clearHead();

    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    void clearBody();

    /**
     * @return Número de elementos de la lista [EN]  Number of items in the list
     */
    int size();

    /**
     * @return verdadero si la vista está vacía [EN]  true if the view is empty
     */
    boolean isEmpty();
}
