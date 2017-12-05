package es.marser.backgroundtools.containers.dialogs.bases;

import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.controller.AdapterController;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.table.adapter.TableListAdapterDECREP;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base de construcción de dialogo con lista de multiples vistas cabecera y cuerpo
 *         <p>
 *         [EN]  Base construction dialog with list of multiple views header and body
 */

@SuppressWarnings({"SameReturnValue", "unused"})
@Deprecated
public abstract class BaseDialogBinTableDECREP<H extends Parcelable,B extends Parcelable> extends BaseDialogList {

    protected TableListAdapterDECREP<H,B> adapter;

    //OBLIGATORY OVERWRITING___________________________________________________________________________
    public abstract  int getHeadHolderLayout();

    public abstract int getBodyHolderLayout();

    //OPTIONAL OVERWRITE________________________________________________________________________________
    /*Controlador de pulsaciones*/
    public ViewItemHandler<H> getHeadItemHandler() {
        return new ViewItemHandler<H>() {
            @Override
            public void onClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                BaseDialogBinTableDECREP.this.onClickHeadItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                return BaseDialogBinTableDECREP.this.onLongClickHeadItem(holder, item, position, mode);
            }
        };
    }

    public ViewItemHandler<B> getBodyItemHandler() {
        return new ViewItemHandler<B>() {
            @Override
            public void onClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                BaseDialogBinTableDECREP.this.onClickBodyItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                return BaseDialogBinTableDECREP.this.onLongClickBodyItem(holder, item, position, mode);
            }
        };
    }

    /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/
    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return new TouchableViewHandler<H>() {
            @Override
            public void onClick(View view, int position, H item, View root) {
                BaseDialogBinTableDECREP.this.onClickHead(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, H item, View root) {
                return BaseDialogBinTableDECREP.this.onLongClickHead(view, position, item, root);
            }
        };
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return new TouchableViewHandler<B>() {
            @Override
            public void onClick(View view, int position, B item, View root) {
                BaseDialogBinTableDECREP.this.onClickBody(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, B item, View root) {
                return BaseDialogBinTableDECREP.this.onLongClickBody(view, position, item, root);
            }
        };
    }

    //SUPERCLASS OVERWRITING________________________________________________________________________

    @Override
    protected void bindAdapter() {
        adapter = new TableListAdapterDECREP<H, B>() {
            @Override
            public int getHeadHolderLayout() {
                return BaseDialogBinTableDECREP.this.getHeadHolderLayout();
            }

            @Override
            public int getBodyHolderLayout() {
                return BaseDialogBinTableDECREP.this.getBodyHolderLayout();
            }

            @Override
            public ViewItemHandler<H> getHeadItemHandler() {
                return BaseDialogBinTableDECREP.this.getHeadItemHandler();
            }

            @Override
            public ViewItemHandler<B> getBodyItemHandler() {
                return BaseDialogBinTableDECREP.this.getBodyItemHandler();
            }

            @Override
            public TouchableViewHandler<H> getHeadTouchableViewHandler() {
                return BaseDialogBinTableDECREP.this.getHeadTouchableViewHandler();
            }

            @Override
            public TouchableViewHandler<B> getBodyTouchableViewHandler() {
                return BaseDialogBinTableDECREP.this.getBodyTouchableViewHandler();
            }
        };


        recyclerView.setAdapter(adapter);

        adapter.hAdapterController.setSelectionmode(null,ListExtra.SINGLE_SELECTION_MODE);
        adapter.bAdapterController.setSelectionmode(null,ListExtra.SINGLE_SELECTION_MODE);
    }

    @Override
    public boolean isEmpty() {
        return getHeadGlobalController().isEmpty() && getBodyGlobalController().isEmpty();
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
    public AdapterController<H> getHeadGlobalController(){
        return adapter.hAdapterController;
    }

    /**
     * @return Controladora de cuerpo
     * [EN]  Body controller
     */
    public AdapterController<B> getBodyGlobalController(){
        return adapter.bAdapterController;
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
            getHeadGlobalController().replace(items);
        }
    }

    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    public void clearBody() {
        getBodyGlobalController().clear();
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
            getBodyGlobalController().add(item);
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
            getBodyGlobalController().add(id, item);
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
            getBodyGlobalController().set(id, item);
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
            adapter.adapterController.remove(id);
            adapter.adapterController.getSelectionItemsController().clear();
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
