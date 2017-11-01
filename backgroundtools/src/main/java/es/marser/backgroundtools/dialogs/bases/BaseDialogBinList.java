package es.marser.backgroundtools.dialogs.bases;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapter;
import es.marser.backgroundtools.objectslistables.base.controller.ArrayListController;
import es.marser.backgroundtools.objectslistables.base.controller.SelectionController;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Base de construccion de dialogos de lista
 *         <p>
 *         [EN]  Basis of constructing list dialogs
 */

@SuppressWarnings("unused")
public abstract class BaseDialogBinList<T>
        extends BaseDialogBinModel
        implements
        TouchableViewHandler<T>,
        ViewItemHandler<T> {

    protected RecyclerView recyclerView;
    protected SimpleListAdapter<T> adapter;

    protected Integer lastScroll;

    //VARIABLE START____________________________________________________________________________________
    @Override
    protected void postBuild() {
        super.postBuild();
        recyclerView = view.findViewById(getRecyclerviewId());
        recyclerView.setHasFixedSize(hasFixedSize());
        recyclerView.setLayoutManager(getLayoutManager());
        bindAdapter();
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

    /*Vistas de componentes [EN]  Component views*/

    /**
     * Definición de la vista del recyclerview
     * <p>
     * [EN]  Definition of the view of recyclerview
     *
     * @return R.id.xxxxxx, por defecto {@link R.id#id_recyclerview} [EN]  default {@link R.id#id_recyclerview}
     */
    protected int getRecyclerviewId() {
        return R.id.id_recyclerview;
    }

    /*Configuración de recyclerview [EN]  Configuring recyclerview*/

    /**
     * Definición del gestor del layout de la lista. Por defecto se utilizará el lineal
     * <p>
     * [EN]  Definition of the layout manager of the list.  By default the linear
     *
     * @return gestor del layout {@link LinearLayoutManager#VERTICAL}
     * Opcional puede ser {@link GridLayoutManager}
     * [EN]  gestor del layout {@link LinearLayoutManager#VERTICAL}
     * Optional can be {@link GridLayoutManager}
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    /**
     * Verdadero si la aplicación ha especificado que los cambios
     * en el contenido del adaptador no pueden cambiar el tamaño de RecyclerView.
     * <p>
     * [EN] true if the app has specified that changes
     * in adapter content cannot change the size of the RecyclerView itself.
     *
     * @return Verdadero si la aplicación ha especificado que los cambios
     * en el contenido del adaptador no pueden cambiar el tamaño de RecyclerView.
     * [EN] true if the app has specified that changes
     * in adapter content cannot change the size of the RecyclerView itself.
     */
    @SuppressWarnings("SameReturnValue")
    protected boolean hasFixedSize() {
        return true;
    }

    /*Inicio de métodos [EN]  Start of methods*/

    /**
     * Modo de selección inicial de la lista. Por defecto Mode de selección sencilla
     * <p>
     * [EN]  Initial selection mode of the list.  Default Simple selection mode.
     *
     * @return Modo de selección sencilla {@link ListExtra#SINGLE_SELECTION_MODE}
     */
    @SuppressWarnings("SameReturnValue")
    protected ListExtra getInitialSelectionMode() {
        return ListExtra.SINGLE_SELECTION_MODE;
    }

    /**
     * Enlace del adaptador de la lista
     * <p>
     * [EN]  List adapter link
     */
    protected void bindAdapter() {
        adapter = new SimpleListAdapter<T>() {

            @Override
            public TouchableViewHandler<T> getTouchableViewHandler() {
                return BaseDialogBinList.this;
            }

            @Override
            public ViewItemHandler<T> getItemHandler() {
                return BaseDialogBinList.this;
            }

            @Override
            protected int getHolderLayout() {
                return BaseDialogBinList.this.getHolderLayout();
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.globalController.selectionController.setSelectionMode(getInitialSelectionMode());
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
     * Método de carga de datos
     * <p>
     * [EN]  Data Upload Method
     */
    protected abstract void load();

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
     * Comprobar si hay elementos en el adapter
     * <p>
     * [EN]  Check for items in the adapter
     *
     * @return verdadero si no hay elementos
     * [EN]  true if there are no elements
     */
    public boolean isEmpty() {
        return adapter.globalController.arrayListController.isEmpty();
    }

    /**
     * Posiciona la vista en el prier elemento de la lista
     * <p>
     * [EN]  Position the view on the first item in the list
     */
    public void scrollToFirst() {
        if (!isEmpty()) {
            scrollToId(0);
        }
    }

    /**
     * Posicionar la vista en la posición señalada
     * <p>
     * [EN]  Position the view in the indicated position
     *
     * @param position posición en el adapter
     */
    public void scrollToId(int position) {
        if (position > -1 && position < getItemCount()) {
            try {
                recyclerView.scrollToPosition(position);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Guarda la posición del pimer elemento visible
     * <p>
     * [EN]  Save the position of the visible element pimer
     */
    public void savedScroll() {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            lastScroll = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        } else {
            lastScroll = 0;
        }
    }

    /**
     * Posiciona la vista en a útlima posición guardada
     * <p>
     * [EN]  Position the view in the last saved position
     */
    public void restoreScroll() {
        if (lastScroll != null && lastScroll < recyclerView.getAdapter().getItemCount() && lastScroll > -1) {
            try {
                recyclerView.scrollToPosition(lastScroll);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Posiciona el foco en la última posición de la lista de elementos
     * <p>
     * [EN]  Position the focus in the last position in the list of elements
     */
    public void scrollToLast() {
        scrollToId(adapter.getItemCount() - 1);

    }

    /**
     * Notificar cambios en el adapter
     * <p>
     * [EN]  Notify changes to the adapter
     */
    public void notifyChangedData() {
        adapter.notifyDataSetChanged();
    }

//ITEM DECORATOR___________________________________________________________________________________

    /**
     * Añadir un separador de elementos
     * <p>
     * [EN]  Add an element separator
     *
     * @param itemDecoration separador de elementos [EN]  element separator
     */
    protected void addItemDecorator(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * Añadir separador de elementos según
     * <p>
     * [EN]  Add item separator according
     *
     * @param itemDecoration separador de elementos [EN]  element separator
     * @param index          índice del separador [EN]  index of the separator
     */
    protected void addItemDecorator(RecyclerView.ItemDecoration itemDecoration, int index) {
        recyclerView.addItemDecoration(itemDecoration, index);
    }


}
