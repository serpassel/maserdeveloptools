package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.AsyncTask;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.listeners.AdapterNotifier;
import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Controlodador global de la lista
 *         <p>
 *         [EN]  Global controller from the list
 */

@SuppressWarnings("unused")
public class GlobalController<T> implements ViewHolderController<T>, OnItemChangedListener {
    /*Variables de control [EN]  Control variables*/
    public SelectionController<T> selectionController;
    public ArrayListController<T> arrayListController;
    public ExpandController expandController;

    /*Oyente de cambios en la lista [EN]  Listener of changes in the list*/
    protected AdapterNotifier adapterNotifier;

    public int viewHolderType;

    public GlobalController(int viewHolderType){
        this.viewHolderType = viewHolderType;

         /*Nueva instancia de controladores [EN]  New controller instance*/
        arrayListController = new ArrayListController<>();
        selectionController = new SelectionController<>(
                arrayListController,
                ListExtra.SINGLE_SELECTION_MODE);

        expandController = new ExpandController();

     /*Definición de oyentes a los controladores [EN]  Definition of listeners to the controllers*/
        arrayListController.setOnChangedListListener(this);
        selectionController.setOnSelectionChanged(this);
        expandController.setOnSelectionChanged(this);
    }

    public GlobalController() {
        this(ViewHolderType.SIMPLE.ordinal());
    }

    /**
     * Número total de registros
     * <p>
     * [EN]  Total number of records
     *
     * @return [EN]  Total number of records
     */
    public int getItemCount() {
        return arrayListController.size();
    }
    //SET LISTENERS________________________________________________________________________________

    /*Oyentes de pulsación [EN]  Pulsation listeners*/
    public void removeViewItemHandler() {
        selectionController.removeItemHandler();
    }

    public void setViewItemHandler(ViewItemHandler<T> viewItemHandler) {
        selectionController.setItemHandler(viewItemHandler);
    }

    public void removeAdapterNotifier() {
        this.adapterNotifier = null;
    }

    public void setChangedListener(AdapterNotifier adapterNotifier) {
        this.adapterNotifier = adapterNotifier;
    }

    /**
     * {@link ViewHolderController}
     */
    @Override
    public boolean isExpaned(int posicion) {
        return expandController.get(adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public boolean isSelected(int posicion) {
        return selectionController.get(adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public void onClick(BaseViewHolder<T> holder, int posicion) {
        selectionController.onClick(holder, adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public boolean onLongClick(BaseViewHolder<T> holder, int posicion) {
        return selectionController.onLongClick(holder, adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public T getItemAt(int posicion) {
        return arrayListController.getItemAt(adapterNotifier.indexPos(posicion, viewHolderType));
    }


    /* {@link OnItemChangedListener}*/

    /**
     * {@link OnItemChangedListener}
     */
    @Override
    public void onSelectionChanged() {
        if (adapterNotifier != null) {
            adapterNotifier.notifyDataSetChanged(viewHolderType);
        }
    }

    @Override
    public void onItemChaged(int index) {
        if (adapterNotifier != null) {
            adapterNotifier.notifyItemChanged(index, viewHolderType);
        }
    }

    @Override
    public void onAddItem(int index) {
        expandController.clear();
        if (adapterNotifier != null) {
            adapterNotifier.notifyItemInserted(index, viewHolderType);
        }
    }

    @Override
    public void onRemoveItem(int index) {
        expandController.delete(index);
        selectionController.delete(index);

        if (adapterNotifier != null) {
            adapterNotifier.notifyItemRemoved(index, viewHolderType);
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    expandController.collapseAll();
                } catch (Exception ignored) {
                }
            }
        });
    }

    @Override
    public void removeAllItems() {
        // Log.i(MainCRUD.TAG, "Eliminados todos los registros;");
        selectionController.clear();
        expandController.clear();
        if (adapterNotifier != null) {
            adapterNotifier.notifyDataSetChanged(viewHolderType);
        }
    }

    @Override
    public void onAllChanged() {
        if (adapterNotifier != null) {
            adapterNotifier.notifyDataSetChanged(viewHolderType);
        }
    }
}
