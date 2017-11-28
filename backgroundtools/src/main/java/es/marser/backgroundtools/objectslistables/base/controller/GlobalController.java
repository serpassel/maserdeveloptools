package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.listeners.AdapterNotifier;
import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Controlodador global de la lista
 *         <p>
 *         [EN]  Global controller from the list
 */

@SuppressWarnings("unused")
public class GlobalController<T extends Parcelable> implements ViewHolderController<T>, OnItemChangedListener {
    /*Variables de control [EN]  Control variables*/
    public SelectionController<T> selectionController;
    public ArrayList<T> items;
    public ExpandController expandController;

    /*Oyente de cambios en la lista [EN]  Listener of changes in the list*/
    protected AdapterNotifier adapterNotifier;

    public static String itemskey = "items_key";
    public static String itemscountkey = "items_count_key";
    public static String viewholdertypekey = "view_holder_type_key";

    public int viewHolderType;

    public GlobalController(int viewHolderType) {
        this.viewHolderType = viewHolderType;

         /*Nueva instancia de controladores [EN]  New controller instance*/
        items = new ArrayList<>();
        selectionController = new SelectionController<>(
                items,
                ListExtra.SINGLE_SELECTION_MODE);

        expandController = new ExpandController();

     /*Definición de oyentes a los controladores [EN]  Definition of listeners to the controllers*/
        selectionController.setOnSelectionChanged(this);
        expandController.setOnSelectionChanged(this);
    }

    //SAVED AND RESTORE_____________________________________________________________

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
     */
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        //Selection
        if (selectionController != null) {
            selectionController.onSaveInstanceState(savedInstanceState, String.valueOf(viewHolderType));
            //  selectionController.removedOnSelectionChanged();
        }

        //Expanded
        expandController.onSaveInstanceState(savedInstanceState, String.valueOf(viewHolderType));

        //ArrayList
        if (savedInstanceState != null) {
            savedInstanceState.putInt(TextTools.nc(viewHolderType) + itemscountkey, size());

            if (items != null && size() > 0) {
                savedInstanceState.putParcelableArrayList(TextTools.nc(viewHolderType) + itemskey, items);
            }
        }
    }

    /**
     * Called when all saved state has been restored into the view hierarchy
     * of the fragment.  This can be used to do initialization based on saved
     * state that you are letting the view hierarchy track itself, such as
     * whether check box widgets are currently checked.  This is called
     * after {@link #onActivityCreated(Bundle)} and before
     * {@link #onStart()}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @SuppressWarnings("unchecked")
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

        Log.i(LOG_TAG.TAG, "Restore controller ");

        //Selection
        if (selectionController != null) {
            selectionController.onRestoreInstanceState(savedInstanceState, String.valueOf(viewHolderType));
        }

        //Expanded
        if (expandController != null) {
            expandController.onRestoreInstanceState(savedInstanceState, String.valueOf(viewHolderType));
        }

        //ArrayList
        if (savedInstanceState != null
                && items != null) {

            try {
                items.addAll(savedInstanceState.getParcelableArrayList(TextTools.nc(viewHolderType) + itemskey) != null
                        ? (ArrayList<T>) savedInstanceState.getParcelableArrayList(TextTools.nc(viewHolderType) + itemskey)
                        : new ArrayList<T>()
                );
            } catch (ClassCastException ignored) {
            }
        }
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
    public int size() {
        return items != null ? items.size() : 0;
    }

    public boolean isEmpty(){
        return items != null && items.isEmpty();
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
        return expandController != null && adapterNotifier != null && expandController.get(adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public boolean isSelected(int posicion) {
        // Log.d(LOG_TAG.TAG, "Selector nulo " + (selectionController == null));
        // Log.d(LOG_TAG.TAG, "DialogProgressModello " + (adapterNotifier == null));
        return selectionController != null && adapterNotifier != null && selectionController.get(adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public void onClick(BaseViewHolder<T> holder, int posicion) {
        if (selectionController != null && adapterNotifier != null) {
            selectionController.onClick(holder, adapterNotifier.indexPos(posicion, viewHolderType));
        }
    }

    @Override
    public boolean onLongClick(BaseViewHolder<T> holder, int posicion) {
        return selectionController.onLongClick(holder, adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public T getItemAt(int position) {
        if (items == null || adapterNotifier == null) {
            return null;
        }

        int index = adapterNotifier.indexPos(position, viewHolderType);

        if ((index > -1 && index < this.items.size())) {
            return this.items.get(index);
        }
        return null;
    }

    @Override
    public void setSelected(int position, boolean value) {
        if (selectionController == null || adapterNotifier == null) {
            return;
        }
        selectionController.setSelected(adapterNotifier.indexPos(position, viewHolderType), value);
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

    /**
     * Insertado un objeto en la posición indicada
     * <p>
     * [EN]  Inserted an object in the indicated position
     *
     * @param position posición de insercción [EN]  insertion position
     */
    @Override
    public void onInsertItem(int index) {
        if (expandController != null) {
            expandController.clear();
        }

        if (adapterNotifier != null) {
            adapterNotifier.notifyItemInserted(index, viewHolderType);
        }
    }

    @Override
    public void onAddItem(int index) {
        if (expandController != null) {
            expandController.clear();
        }

        if (adapterNotifier != null) {
            adapterNotifier.notifyAddItem(index, viewHolderType);
        }
    }

    @Override
    public void onRemoveItem(int index) {
        if (expandController != null) {
            expandController.delete(index);
        }
        if (selectionController != null) {
            selectionController.delete(index);
        }
        if (adapterNotifier != null) {
            adapterNotifier.notifyItemRemoved(index, viewHolderType);
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (expandController != null) {
                        expandController.collapseAll();
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }

    @Override
    public void removeAllItems() {
        // Log.i(MainCRUD.TAG, "Eliminados todos los registros;");
        if (selectionController != null) {
            selectionController.clear();
        }
        if (expandController != null) {
            expandController.clear();
        }

        if (adapterNotifier != null) {
            adapterNotifier.notifyRemoveRange(0, size(), viewHolderType);
        }

    }

    @Override
    public void onAddAll() {
        if (adapterNotifier != null) {
            adapterNotifier.notifyAddRange(0, size(), viewHolderType);
        }
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
        // Log.d(LOG_TAG.TAG, "items nulos " + (items == null));
        if (items != null) {
            //   Log.d(LOG_TAG.TAG, "Añadidos " + items.size());
            /*Agregar la lista de registros [EN]  Add the list of records*/
            this.items.addAll(items);

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            onAddAll();
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
        removeAllITems();
        addAllItems(items);
    }


    /**
     * Eliminar todos los elementos de la lista
     * <p>
     * [EN]  Remove all items from the list
     */
    public void removeAllITems() {
          /*Notificar cambios de selección [EN]  Notify selection changes*/
        removeAllItems();
          /*Limpiar lista [EN]  Clean list*/
        this.items.clear();
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
         /*Notificar cambios de selección [EN]  Notify selection changes*/
            onRemoveItem(position);
            /*Eliminar elemento [EN]  Delete item*/
            this.items.remove(position);
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
            this.items.add(item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            onAddItem(size() - 1);
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
        if ((position > -1 && position < size()) || item != null) {
        /*Agregar elemento [EN]  Add Item*/
            this.items.add(position, item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            onInsertItem(position);
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
            this.items.set(position, item);
       /*Notificar cambios de selección [EN]  Notify selection changes*/
            onItemChaged(position);
        }
    }

}
