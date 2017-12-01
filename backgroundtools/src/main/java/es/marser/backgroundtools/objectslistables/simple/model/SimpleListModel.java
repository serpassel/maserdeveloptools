package es.marser.backgroundtools.objectslistables.simple.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.model.AdapterItemsController;
import es.marser.backgroundtools.objectslistables.base.model.AdapterModel;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsController;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.SelectedsModel;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.Selectionable;
import es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapter;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Modelo de configuración de listas simples para patrón de diseño MVP
 *         <p>
 *         [EN]  Simple list configuration model for MVP design pattern
 */

@SuppressWarnings("unused")
public class SimpleListModel<T extends Parcelable>
        implements AdapterModel,
        AdapterItemsController<T>,
        SelectedsModel<T>,
        SelectionItemsManager,
        ExpandItemsManager, Selectionable {

    private Context context;
    protected SimpleListAdapter<T> adapter;

    //CONSTRUCTORS_____________________________________________
    public SimpleListModel(Context context) {
        this.context = context;
        adapter = new SimpleListAdapter<>();
    }

    public SimpleListModel(Context context, int holderLayout) {
        this.context = context;
        adapter = new SimpleListAdapter<>(holderLayout);
    }

    //ADAPTER MODEL_________________________________________________
    @Override
    public BaseListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean isHasFixedSize() {
        return true;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    //GETTERS___________________________________________________
    public Context getContext() {
        return context;
    }

    //SETTERS____________________________________________________

    public void setViewItemHandler(ViewItemHandler<T> viewItemHandler) {
        if (adapter != null) {
            adapter.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeViewItemHandler() {
        if (adapter != null) {
            adapter.removeViewItemHandler();
        }
    }

    public void setTouchableViewHandler(TouchableViewHandler<T> touchableViewHandler) {
        if (adapter != null) {
            adapter.setTouchableViewHandler(touchableViewHandler);
        }
    }

    public void removeTouchableViewHandler() {
        if (adapter != null) {
            adapter.removeTouchableViewHandler();
        }
    }

    //ADAPTER ITEMS CONTROLLER___________________________________
    @Nullable
    @Override
    public List<T> getItems() {
        return adapter != null && adapter.getAdapterItemsController() != null ? adapter.getAdapterItemsController().getItems() : null;
    }

    @Nullable
    @Override
    public T get(int index) {
        return adapter != null && adapter.getAdapterItemsController() != null ? adapter.getAdapterItemsController().get(index) : null;
    }

    @Override
    public void addAll(@Nullable List<T> items) {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().addAll(items);
        }
    }

    @Override
    public void add(@Nullable T item) {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().add(item);
        }
    }

    @Override
    public void add(int index, @Nullable T item) {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().add(index, item);
        }
    }

    @Override
    public void remove(int index) {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().remove(index);
        }
    }

    @Override
    public void clear() {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().clear();
        }
    }

    @Override
    public void set(@Nullable Integer index, @Nullable T item) {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().set(index, item);
        }
    }

    @Override
    public void replace(@Nullable List<T> items) {
        if (adapter != null && adapter.getAdapterItemsController() != null) {
            adapter.getAdapterItemsController().replace(items);
        }
    }

    @Override
    public int size() {
        return adapter != null && adapter.getAdapterItemsController() != null ? adapter.getItemCount() : 0;
    }

    @Override
    public boolean isEmpty() {
        return !(adapter != null && adapter.getAdapterItemsController() != null) || adapter.getAdapterItemsController().isEmpty();
    }

    //SELECTEDS MODEL____________________________________________________________
    @Override
    @Nullable
    public List<T> removeSelectedItems() {
        return adapter != null && adapter.getSelectedsModel() != null ? adapter.getSelectedsModel().removeSelectedItems() : null;
    }

    @Override
    @Nullable
    public List<T> getSelectds() {
        return adapter != null && adapter.getSelectedsModel() != null ? adapter.getSelectedsModel().getSelectds() : null;
    }

    @Override
    @Nullable
    public T getItemSelected() {
        return adapter != null && adapter.getSelectedsModel() != null ? adapter.getSelectedsModel().getItemSelected() : null;
    }

    //SELECTION MANAGER_______________________________________________________
    @Nullable
    @Override
    public SelectionItemsController getSelectionItemsController() {
        return adapter != null ? adapter.getSelectionItemsController() : null;
    }

    @Nullable
    @Override
    public ExpandItemsController getExpandItemsController() {
        return adapter != null ? adapter.getExpandItemsController() : null;
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
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
    //    Log.d(LOG_TAG.TAG, "RESTAURANDO  MODELO");
        if (adapter != null) {
      //      Log.d(LOG_TAG.TAG, "Guardando adaptador");
            adapter.onSaveInstanceState(savedInstanceState);
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
    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (adapter != null) {
                adapter.onRestoreInstanceState(savedInstanceState);
            }
        }
    }

    //SELECTIONABLE_________________________________________________________________

    @Nullable
    @Override
    public ListExtra getSelectionmode() {
        Selectionable selectionable = adapter != null ? adapter.getSelectionable(null) : null;
        return selectionable != null ? selectionable.getSelectionmode() : null;
    }

    @Override
    public void setSelectionmode(@NonNull ListExtra selectionmode) {
        Selectionable selectionable = adapter != null ? adapter.getSelectionable(null) : null;
        if (selectionable != null) {
            selectionable.setSelectionmode(selectionmode);
        }
    }
}
