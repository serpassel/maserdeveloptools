package es.marser.backgroundtools.containers.fragments.base;

import android.view.View;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.controller.ArrayListController;
import es.marser.backgroundtools.objectslistables.base.controller.SelectionController;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapter;

/**
 * @author sergio
 *         Created by Sergio on 31/03/2017.
 *         Base de construcción de fragments con lista de objetos enlazados
 *         <ul>
 *         <il>Inicio de variables</il>
 *         <il>Métodos abstractos de configuración</il>
 *         <il>Manejadores de eventos de las vistas</il>
 *         <il>Control de elementos</il>
 *         <il>Oyentes del cambio en fragments</il>
 *         </ul>
 *         <p>
 *         [EN]  Fragments building base with list of linked objects
 *         <ul>
 *         <il>Variable start</il>
 *         <il>Abstract Methods of Configuration</il>
 *         <il>View event handlers</il>
 *         <il>Control of items</il>
 *         <il>Change listeners in fragments</il>
 *         <il>Item Decorator</il>
 *         </ul>
 */

@SuppressWarnings({"JavaDoc", "unused"})
public abstract class BaseFragmentBinList<T>
        extends BaseFragmentList
        implements
        TouchableViewHandler<T>,
        ViewItemHandler<T> {


    protected SimpleListAdapter<T> adapter;

    //ABSTRACT METHODS OF CONFIGURATION_______________________________________________________________

    /*Vistas [EN] Views*/

    /**
     * Definición de la vista de los items de la lista
     * <p>
     * [EN]  Defining the view of items in the list
     *
     * @return R.layout.XXXX Vista de los items [EN]  View items
     */
    protected abstract int getHolderLayout();

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvc_frag_simple_list;
    }

    @Override
    protected void bindAdapter() {
        adapter = new SimpleListAdapter<T>() {

            @Override
            public TouchableViewHandler<T> getTouchableViewHandler() {
                return BaseFragmentBinList.this;
            }

            @Override
            public ViewItemHandler<T> getItemHandler() {
                return BaseFragmentBinList.this;
            }

            @Override
            protected int getHolderLayout() {
                return BaseFragmentBinList.this.getHolderLayout();
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.globalController.selectionController.setSelectionMode(getInitialSelectionMode());
    }

    @Override
    public boolean isEmpty() {
        return adapter.globalController.arrayListController.isEmpty();
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }


    //VIEW EVENT HANDLERS_____________________________________________________________________________

    /*{@link TouchableViewHandler}*/
    /*Eventos de pulsación sobre la vista raiz
    [EN]  Pulsation Events on the Root View*/
    @Override
    public void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
    }

    @Override
    public boolean onLongClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
        return true;
    }

    /*{@link ViewItemHandler}*/
    /*Eventos de pulsación en las vistas anidadas sobre la vista principal
    [EN]  Pulsation events in nested views over the main view*/
    @Override
    public void onClick(View view, int position, T item, View root) {

    }

    @Override
    public boolean onLongClick(View view, int position, T item, View root) {
        return true;
    }


    //CONTROL OF ITEMS____________________________________________________________________________

    /**
     * Acceso al controlador de selección del adaptador de elementos
     * <p>
     * [EN]  Access to the items Adapter Selection Controller
     *
     * @return Controlador de selección {@link SelectionController} [EN]  Selection controller {@link SelectionController}
     */
    public SelectionController<T> getSelectionController() {
        return adapter.globalController.selectionController;
    }

    /**
     * Acceso a los elementos de la lista
     * <p>
     * [EN]  Access to list items
     *
     * @return Controlador de la lista de elementos {@link ArrayListController}
     * [EN]  Item List Controller {@link ArrayListController}
     */
    public ArrayListController<T> getArrayListController() {
        return adapter.globalController.arrayListController;
    }

    /**
     * Reemplaza todos los elementos del adaptador
     * <p>
     * [EN]  Replaces all adapter elements
     *
     * @param items Lista de elementos de tipo genérico [EN]  List of elements of generic type
     */
    public void setItems(ArrayList<T> items) {
        if (items != null) {
            adapter.globalController.arrayListController.replaceAllItems(items);
        }
    }


    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    public void clear() {
        adapter.globalController.arrayListController.removeAllITems();
    }

    /**
     * Agregar un elemento no nulo al final de la lista
     * <p>
     * [EN]  Add a non-null element to the end of the list
     *
     * @param item Objeto genérico nuevo [EN]  New Generic Object
     */
    public void addItem(T item) {
        if (item != null) {
            adapter.globalController.arrayListController.addItem(item);
        }
    }

    /**
     * Agrega un elemento nuevo en la posición señalada
     * <p>
     * [EN]  Add a new item in the highlighted position
     *
     * @param id   posición donde inserta el nuevo elemento [EN]  position where you insert the new item
     * @param item Objeto genérico nuevo [EN]  New Generic Object
     */
    public void insertItem(int id, T item) {
        if (item != null && id > -1 && id < getItemCount()) {
            adapter.globalController.arrayListController.insertItem(id, item);
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Actualizar un registro existente
     * <p>
     * [EN]  Update an existing record
     *
     * @param id   posición del elemento en el adapter [EN]  position of the element in the adapter
     * @param item elemento actualizado [EN]  item updated
     */
    public void updateItem(int id, T item) {
        if (item != null && id > -1 && id < getItemCount()) {
            adapter.globalController.arrayListController.updateItem(id, item);
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Eliminar un registro en la posición indicada
     * <p>
     * [EN]  Delete a record in the indicated position
     *
     * @param id posición del elemento a eliminar
     *           [EN]  position of the item to be deleted
     */
    public void deleteItem(int id) {
        if (id > -1 && id < getItemCount()) {
            adapter.globalController.arrayListController.removeItem(id);
            adapter.globalController.selectionController.clear();
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Notificar cambios en el adapter
     * <p>
     * [EN]  Notify changes to the adapter
     */
    public void notifyChangedData() {
        adapter.notifyDataSetChanged();
    }

}
