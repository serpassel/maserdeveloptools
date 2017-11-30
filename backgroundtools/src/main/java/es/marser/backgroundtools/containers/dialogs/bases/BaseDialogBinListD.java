package es.marser.backgroundtools.containers.dialogs.bases;

import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.controller.AdapterController;
import es.marser.backgroundtools.objectslistables.base.controller.SelectionControllerD;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapterDecrep;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Base de construccion de dialogos de lista
 *         <p>
 *         [EN]  Basis of constructing list dialogs
 */

@SuppressWarnings("unused")
@Deprecated
public abstract class BaseDialogBinListD<T extends Parcelable>
        extends BaseDialogList
        implements
        TouchableViewHandler<T>,
        ViewItemHandler<T> {

    protected SimpleListAdapterDecrep<T> adapter;


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
    protected void bindAdapter() {
        adapter = new SimpleListAdapterDecrep<T>() {

            @Override
            public TouchableViewHandler<T> getTouchableViewHandler() {
                return BaseDialogBinListD.this;
            }

            @Override
            public ViewItemHandler<T> getItemHandler() {
                return BaseDialogBinListD.this;
            }

            @Override
            protected int getHolderLayout() {
                return BaseDialogBinListD.this.getHolderLayout();
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.adapterController.setSelectionmode(getInitialSelectionMode());
    }

    @Override
    public boolean isEmpty() {
        return getController().isEmpty();
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
     * @return Controlador de selección {@link SelectionControllerD} [EN]  Selection controller {@link SelectionControllerD}
     */
    public AdapterController<T> getController() {
        return adapter.adapterController;
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
            adapter.adapterController.replace(items);
        }
    }

    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    public void clear() {
        getController().clear();
    }

    /**
     * Número de elementos en la lista
     * <p>
     * [EN]  Number of items in the list
     *
     * @return número de elementos de la lista [EN]  number of items in the list
     */
    public int getItemCount() {
        return adapter.getItemCount();
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
            adapter.adapterController.add(item);
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
            adapter.adapterController.add(id, item);
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
            adapter.adapterController.set(id, item);
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
            adapter.adapterController.remove(id);
            adapter.adapterController.getSelectionItemsController().clear();
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Marcar como seleccionado
     * <p>
     * [EN]  Mark as selected
     *
     * @param id posición en el selecionador [EN]  position in the selector
     * @param value valor de selección [EN]  selection value
     */
    public void setSelected(int id, boolean value) {
        adapter.adapterController.getSelectionItemsController().setSelected(id, value);
    }

    /**
     * Marcar como seleccionado
     * <p>
     * [EN]  Mark as selected
     *
     * @param id posición en el selecionador [EN]  position in the selector
     * @param value valor de selección [EN]  selection value
     */
    public void inputSelected(int id, boolean value){
        adapter.adapterController.getSelectionItemsController().inputSelected(id, value);
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
