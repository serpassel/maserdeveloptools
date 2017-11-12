package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
    public ArrayListController<T> arrayListController;
    public ExpandController expandController;

    /*Oyente de cambios en la lista [EN]  Listener of changes in the list*/
    protected AdapterNotifier adapterNotifier;

    public static String itemskey = "items_key";
    public static String itemscountkey = "items_count_key";
    public int viewHolderType;

    public GlobalController(int viewHolderType) {
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
            selectionController.removedOnSelectionChanged();
        }

        //Expanded
        expandController.onSaveInstanceState(savedInstanceState, String.valueOf(viewHolderType));

        //ArrayList
        if (savedInstanceState != null) {
            savedInstanceState.putInt(TextTools.nc(viewHolderType) + itemscountkey, getItemCount());
            if (arrayListController != null && getItemCount() > 0) {
                savedInstanceState.putParcelableArrayList(TextTools.nc(viewHolderType) + itemskey, arrayListController);
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
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

        //Selection
        if (selectionController != null) {
            selectionController.onRestoreInstanceState(savedInstanceState, String.valueOf(viewHolderType));
        } else {
            selectionController = new SelectionController<>(
                    arrayListController,
                    ListExtra.SINGLE_SELECTION_MODE);
        }
        selectionController.setOnSelectionChanged(this);

        //Expanded
        if (expandController != null) {
            expandController.onRestoreInstanceState(savedInstanceState, String.valueOf(viewHolderType));
        } else {
            expandController = new ExpandController();
        }
        expandController.setOnSelectionChanged(this);

        //ArrayList
        if (savedInstanceState != null
                && arrayListController != null
                && arrayListController.size() > 0
                && savedInstanceState.getInt(TextTools.nc(viewHolderType) + itemscountkey, 0) > 0) {

            try {
                arrayListController = (ArrayListController<T>) savedInstanceState
                        .getParcelableArrayList(TextTools.nc(viewHolderType) + itemskey);
            } catch (ClassCastException e) {
                arrayListController = new ArrayListController<>();
            }
        }

        if (arrayListController == null) {
            arrayListController = new ArrayListController<>();
        }

        arrayListController.setOnChangedListListener(this);

        adapterNotifier.notifyDataSetChanged(viewHolderType);
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
        return arrayListController != null ? arrayListController.size() : 0;
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

    @Override
    public void setSelected(int position, boolean value) {
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
