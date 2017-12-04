package es.marser.backgroundtools.listables.simple.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.model.AdapterItemsController;
import es.marser.backgroundtools.listables.base.model.BaseAdapterModel;
import es.marser.backgroundtools.listables.base.model.ExpandItemsController;
import es.marser.backgroundtools.listables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.listables.base.model.SelectedsModel;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.listables.base.model.Selectionable;
import es.marser.backgroundtools.listables.simple.adapter.SimpleListAdapter;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Modelo de configuración de listas simples para patrón de diseño MVP
 *         <p>
 *         [EN]  Simple list configuration model for MVP design pattern
 */

@SuppressWarnings("unused")
public class SimpleAdapterModel<T extends Parcelable> extends BaseAdapterModel<T, SimpleListAdapter<T>>
        implements
        AdapterItemsController<T>,
        SelectedsModel<T>,
        SelectionItemsManager,
        ExpandItemsManager, Selectionable {

    //CONSTRUCTORS_____________________________________________
    public SimpleAdapterModel(@NonNull Context context) {
        this(context, defaultHolderLayout);
    }

    public SimpleAdapterModel(@NonNull Context context, int holderLayout) {
        this(context, new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false), holderLayout);
    }

    public SimpleAdapterModel(@NonNull Context context, int rows, int holderLayout) {
        this(context,
                rows < 2
                        ? new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        : new GridLayoutManager(context, rows),
                holderLayout);
    }

    public SimpleAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager) {
        this(context, layoutManager, defaultHolderLayout);
    }

    public SimpleAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager, int holderLayout) {
        super(context, layoutManager, holderLayout);
        this.adapter = new SimpleListAdapter<>(this.holderLayout);
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
        if (item != null) {
            Log.w(LOG_TAG.TAG, "Insertado " + item.toString());
        }

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

}
