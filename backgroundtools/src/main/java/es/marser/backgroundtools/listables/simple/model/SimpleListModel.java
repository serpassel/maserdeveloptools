package es.marser.backgroundtools.listables.simple.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.listables.base.model.AdapterItemsController;
import es.marser.backgroundtools.listables.base.model.AdapterModel;
import es.marser.backgroundtools.listables.base.model.ExpandItemsController;
import es.marser.backgroundtools.listables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.listables.base.model.SelectedsModel;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.listables.base.model.Selectionable;
import es.marser.backgroundtools.listables.simple.adapter.SimpleListAdapter;
import es.marser.backgroundtools.widget.files.model.SimpleFileListModel;

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
    protected RecyclerView.LayoutManager layoutManager;

    private static int defaultHolderLayout = R.layout.mvp_item_object_chooser;

    //CONSTRUCTORS_____________________________________________
    public SimpleListModel(@NonNull Context context) {
        this(context, defaultHolderLayout);
    }

    public SimpleListModel(@NonNull Context context, int holderLayout) {
        this(context, new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false), holderLayout);
    }

    public SimpleListModel(@NonNull Context context, int rows, int holderLayout) {
        this(context,
                rows < 2
                        ? new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        : new GridLayoutManager(context, rows),
                holderLayout);
    }

    public SimpleListModel(@NonNull Context context, @NonNull RecyclerView.LayoutManager layoutManager) {
        this(context, layoutManager, defaultHolderLayout);
    }

    public SimpleListModel(@NonNull Context context, @NonNull RecyclerView.LayoutManager layoutManager, int holderLayout) {
        this.context = context;
        if (holderLayout < -1) {
            holderLayout = defaultHolderLayout;
        }
        adapter = new SimpleListAdapter<>(holderLayout);
        this.layoutManager = layoutManager;
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
        return layoutManager;
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

    public void setLayoutManager(@NonNull RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
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
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        //    Log.d(LOG_TAG.TAG, "RESTAURANDO  MODELO");
        if (adapter != null) {
            //      Log.d(LOG_TAG.TAG, "Guardando adaptador");
            adapter.onSaveInstanceState(savedInstanceState);
        }
    }

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
