package es.marser.backgroundtools.containers.fragments.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.controller.SelectionControllerD;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.simple.adapter.SimpleListAdapterDecrep;

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
@Deprecated
public abstract class BaseFragmentBinList<T extends Parcelable>
        extends BaseFragmentList
        implements
        TouchableViewHandler<T>,
        ViewItemHandler<T> {


    protected SimpleListAdapterDecrep<T> adapter;

    //SAVED AND RESTORE_________________________________________________________________________________

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        if (adapter != null) {
            adapter.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

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
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        adapter = new SimpleListAdapterDecrep<T>() {

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
        adapter.adapterController.setSelectionmode(null, getInitialSelectionMode());
    }

    @Override
    public boolean isEmpty() {
        return adapter.adapterController.isEmpty();
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
     * @return Controlador de selección {@link SelectionControllerD} [EN]  Selection controller {@link SelectionControllerD}
     */
    @SuppressWarnings("SameReturnValue")
    public SelectionControllerD<T> getSelectionController() {
        return null;
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
        adapter.adapterController.clear();
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
     * @param id    posición en el selecionador [EN]  position in the selector
     * @param value valor de selección [EN]  selection value
     */
    public void setSelected(int id, boolean value) {
        adapter.adapterController.getSelectionItemsController().setSelected(id, value);
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
