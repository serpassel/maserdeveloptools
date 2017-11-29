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
import es.marser.backgroundtools.presenters.base.ListCrud;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Controlodador global de la lista
 *         <p>
 *         [EN]  Global controller from the list
 */

@SuppressWarnings("unused")
public class GlobalController<T extends Parcelable>
        implements ViewHolderController<T>, OnItemChangedListener, ListCrud<T> {
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


    //CONTROLLERS MANAGER_______________________________________________________________________

    /**
     * Limpia los controladores
     * <p>
     * [EN]  Clean the drivers
     */
    private void clearControllers() {
        clearSelected();
        clearExpanded();
    }

    /**
     * Limpia la lista de expansión
     * <p>
     * [EN]  Clean the expansion list
     */
    private void clearExpanded() {
        if (expandController != null) {
            expandController.clear();
        }
    }

    /**
     * Limpia los valores de selección
     * <p>
     * [EN]  Clean the selection values
     */
    private void clearSelected() {
        if (selectionController != null) {
            selectionController.clear();
        }
    }

    //CRUD_____________________________________________________________________________

    @Override
    public int size() {
        return items != null ? items.size() : 0;
    }

    @Override
    public boolean isEmpty() {
        return items != null && items.isEmpty();
    }

    @Override
    public void addAll(List<T> items) {
        // Log.d(LOG_TAG.TAG, "items nulos " + (items == null));
        if (items != null) {
            //   Log.d(LOG_TAG.TAG, "Añadidos " + items.size());
            /*Agregar la lista de registros [EN]  Add the list of records*/
            this.items.addAll(items);

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (adapterNotifier != null) {
                adapterNotifier.notifyDataAdd(items.size(), viewHolderType);
            }
        }
    }

    @Override
    public void add(T item) {
        if (item != null) {
        /*Agregar elemento [EN]  Add Item*/
            this.items.add(item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            clearExpanded();

            if (adapterNotifier != null) {
                adapterNotifier.notifyItemInserted(size() - 1, size(), viewHolderType);
            }
        }
    }

   @Override
    public void add(int index, T item) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((index > -1 && index < size()) || item != null) {
        /*Agregar elemento [EN]  Add Item*/
            this.items.add(index, item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            clearExpanded();

            if (adapterNotifier != null) {
                adapterNotifier.notifyItemInserted(index, size(), viewHolderType);
            }
        }
    }

    @Override
    public void remove(int index) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((index > -1 && index < size())) {

         /*Notificar cambios de selección [EN]  Notify selection changes*/
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



            /*Eliminar elemento [EN]  Delete item*/
            this.items.remove(index);
        }
    }

    @Override
    public void clear() {
          /*Notificar cambios de selección [EN]  Notify selection changes*/
        clearControllers();

        if (adapterNotifier != null) {
            adapterNotifier.notifyDataRemoved(size(), viewHolderType);
        }

          /*Limpiar lista [EN]  Clean list*/
        this.items.clear();
    }

    @Override
    public void set(Integer index, T item) {

   /*Validar entrada [EN]  Validate entry*/
        if (index != null && item != null && index > -1 && index < size()) {
            /*Actualizar registro [EN]  Update record*/
            this.items.set(index, item);
       /*Notificar cambios de selección [EN]  Notify selection changes*/
            onItemChaged(index);
        }
    }

   @Override
    public void replace(List<T> items) {
        clear();
        addAll(items);
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
    public void setSelected(int position, boolean value) {
        if (selectionController == null || adapterNotifier == null) {
            return;
        }
        selectionController.setSelected(adapterNotifier.indexPos(position, viewHolderType), value);
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

}
