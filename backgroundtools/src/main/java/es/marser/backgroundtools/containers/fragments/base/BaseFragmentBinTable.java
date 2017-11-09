package es.marser.backgroundtools.containers.fragments.base;

import android.view.View;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.controller.GlobalController;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.table.adapter.TableListAdapter;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base de construcción de fragment con lista de multiples vistas cabecera y cuerpo
 *         <p>
 *         [EN]  Fragment construction base with list of multiple head and body views
 */

@SuppressWarnings({"unused", "SameReturnValue"})
public abstract class BaseFragmentBinTable<H, B> extends BaseFragmentList {

    protected TableListAdapter<H, B> adapter;

    //OBLIGATORY OVERWRITING___________________________________________________________________________
    public abstract int getHeadHolderLayout();

    public abstract int getBodyHolderLayout();

    //OPTIONAL OVERWRITE________________________________________________________________________________
    /*Controlador de pulsaciones*/
    public ViewItemHandler<H> getHeadItemHandler() {
        return new ViewItemHandler<H>() {
            @Override
            public void onClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                BaseFragmentBinTable.this.onClickHeadItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                return BaseFragmentBinTable.this.onLongClickHeadItem(holder, item, position, mode);
            }
        };
    }

    public ViewItemHandler<B> getBodyItemHandler() {
        return new ViewItemHandler<B>() {
            @Override
            public void onClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                BaseFragmentBinTable.this.onClickBodyItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                return BaseFragmentBinTable.this.onLongClickBodyItem(holder, item, position, mode);
            }
        };
    }

    /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/
    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return new TouchableViewHandler<H>() {
            @Override
            public void onClick(View view, int position, H item, View root) {
                BaseFragmentBinTable.this.onClickHead(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, H item, View root) {
                return BaseFragmentBinTable.this.onLongClickHead(view, position, item, root);
            }
        };
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return new TouchableViewHandler<B>() {
            @Override
            public void onClick(View view, int position, B item, View root) {
                BaseFragmentBinTable.this.onClickBody(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, B item, View root) {
                return BaseFragmentBinTable.this.onLongClickBody(view, position, item, root);
            }
        };
    }


    //SUPERCLASS OVERWRITING________________________________________________________________________
    @Override
    protected void bindAdapter() {
        adapter = new TableListAdapter<H, B>() {
            @Override
            public int getHeadHolderLayout() {
                return BaseFragmentBinTable.this.getHeadHolderLayout();
            }

            @Override
            public int getBodyHolderLayout() {
                return BaseFragmentBinTable.this.getBodyHolderLayout();
            }

            @Override
            public ViewItemHandler<H> getHeadItemHandler() {
                return BaseFragmentBinTable.this.getHeadItemHandler();
            }

            @Override
            public ViewItemHandler<B> getBodyItemHandler() {
                return BaseFragmentBinTable.this.getBodyItemHandler();
            }

            @Override
            public TouchableViewHandler<H> getHeadTouchableViewHandler() {
                return BaseFragmentBinTable.this.getHeadTouchableViewHandler();
            }

            @Override
            public TouchableViewHandler<B> getBodyTouchableViewHandler() {
                return BaseFragmentBinTable.this.getBodyTouchableViewHandler();
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.hGlobalController.selectionController.setSelectionMode(getInitialSelectionMode());
        adapter.bGlobalController.selectionController.setSelectionMode(getInitialSelectionMode());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvc_frag_simple_list;
    }

    @Override
    public boolean isEmpty() {
        return getHeadGlobalController().arrayListController.isEmpty() && getBodyGlobalController().arrayListController.isEmpty();
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    //CONTROL OF ITEMS____________________________________________________________________________

    /**
     * @return controladora de cabecera
     * [EN]  header controller
     */
    public GlobalController<H> getHeadGlobalController() {
        return adapter.hGlobalController;
    }

    /**
     * @return Controladora de cuerpo
     * [EN]  Body controller
     */
    public GlobalController<B> getBodyGlobalController() {
        return adapter.bGlobalController;
    }

    /**
     * Reemplaza todos los elementos del adaptador
     * <p>
     * [EN]  Replaces all adapter elements
     *
     * @param items Lista de elementos de tipo genérico [EN]  List of elements of generic type
     */
    public void setHeadItems(ArrayList<H> items) {
        if (items != null) {
            getHeadGlobalController().arrayListController.replaceAllItems(items);
        }
    }

    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    public void clearBody() {
        getBodyGlobalController().arrayListController.removeAllITems();
    }

    /**
     * Agregar un elemento no nulo al final de la lista
     * <p>
     * [EN]  Add a non-null element to the end of the list
     *
     * @param item Objeto genérico nuevo [EN]  New Generic Object
     */
    public void addBodyItem(B item) {
        if (item != null) {
            getBodyGlobalController().arrayListController.addItem(item);
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
    public void insertBodyItem(int id, B item) {
        if (item != null && id > -1 && id < getItemCount()) {
            getBodyGlobalController().arrayListController.insertItem(id, item);
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
    public void updateBodyItem(int id, B item) {
        if (item != null && id > -1 && id < getItemCount()) {
            getBodyGlobalController().arrayListController.updateItem(id, item);
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
    public void deleteBodyItem(int id) {
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

    //HANDLERS_____________________________________________________________________________

    /* @{link ViewItemHandler*/
    @SuppressWarnings("EmptyMethod")
    public void onClickHeadItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
    }

    public boolean onLongClickHeadItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
        return true;
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBodyItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
    }

    public boolean onLongClickBodyItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
        return true;
    }

    /*@{link TouchableViewHandler}*/
    @SuppressWarnings("EmptyMethod")
    public void onClickHead(View view, int position, H item, View root) {

    }

    public boolean onLongClickHead(View view, int position, H item, View root) {
        return true;
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBody(View view, int position, B item, View root) {

    }

    public boolean onLongClickBody(View view, int position, B item, View root) {
        return true;
    }
}
