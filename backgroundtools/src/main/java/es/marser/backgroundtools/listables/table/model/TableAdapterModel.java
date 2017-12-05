package es.marser.backgroundtools.listables.table.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.controller.AdapterController;
import es.marser.backgroundtools.listables.simple.model.AdapterItems;
import es.marser.backgroundtools.listables.base.model.BaseAdapterModel;
import es.marser.backgroundtools.listables.base.model.ExpandItemsController;
import es.marser.backgroundtools.listables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.listables.base.model.SelectedsModel;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.listables.base.model.Selectionable;
import es.marser.backgroundtools.listables.table.adapter.TableAdapterItems;
import es.marser.backgroundtools.listables.table.adapter.TableListAdapter;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Modelo de configuración de listas simples para patrón de diseño MVP
 *         <p>
 *         [EN]  Simple list configuration model for MVP design pattern
 */

@SuppressWarnings("unused")
public class TableAdapterModel<H extends Parcelable, B extends Parcelable>
        extends BaseAdapterModel<TableListAdapter<H, B>>
        implements
        TableAdapterItems<H, B>,
        SelectedsModel<B>,
        SelectionItemsManager,
        ExpandItemsManager,
        Selectionable {

    //CONSTRUCTORS_____________________________________________
    public TableAdapterModel(@NonNull Context context) {
        this(context, 1);
    }

    public TableAdapterModel(@NonNull Context context, int rows) {
        this(context,
                rows < 2
                        ? new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        : new GridLayoutManager(context, rows)
        );
    }

    public TableAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager) {
        super(context, layoutManager);
        this.adapter = new TableListAdapter<>();
    }

    //SETTERS____________________________________________________

    public void setTitleViewItemHandler(ViewItemHandler<Selectable> viewItemHandler) {
        AdapterController<Selectable> ac = getAdapter() != null ? getAdapter().getTitleAdapterController() : null;
        if (ac != null) {
            ac.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeTitleViewItemHandler() {
        if (getAdapter() != null && getAdapter().getTitleAdapterController() != null) {
            getAdapter().getTitleAdapterController().removeViewItemHandler();
        }
    }

    public void setHeadViewItemHandler(ViewItemHandler<H> viewItemHandler) {
        AdapterController<H> ac = getAdapter() != null ? getAdapter().getHeadAdapterController() : null;
        if (ac != null) {
            ac.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeHeadViewItemHandler() {
        if (getAdapter() != null && getAdapter().getHeadAdapterController() != null) {
            getAdapter().getHeadAdapterController().removeViewItemHandler();
        }
    }

    public void setBodyViewItemHandler(ViewItemHandler<B> viewItemHandler) {
        AdapterController<B> ac = getAdapter() != null ? getAdapter().getBodyAdapterController() : null;
        if (ac != null) {
            ac.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeBodyViewItemHandler() {
        if (getAdapter() != null && getAdapter().getBodyAdapterController() != null) {
            getAdapter().getBodyAdapterController().removeViewItemHandler();
        }
    }

    public void setTitleTouchableViewHandler(TouchableViewHandler<Selectable> tTouchableViewHandler) {
        if (getAdapter() != null) {
            getAdapter().setTitleTouchableViewHandler(tTouchableViewHandler);
        }
    }

    public void removedTitleTouchableViewHandler() {
        setTitleTouchableViewHandler(null);
    }

    public void setHeadTouchableViewHandler(TouchableViewHandler<H> hTouchableViewHandler) {
        if (getAdapter() != null) {
            getAdapter().setHeadTouchableViewHandler(hTouchableViewHandler);
        }
    }

    public void removedHeadTouchableViewHandler() {
        setHeadTouchableViewHandler(null);
    }

    public void setBodyTouchableViewHandler(TouchableViewHandler<B> bTouchableViewHandler) {
        if (getAdapter() != null) {
            getAdapter().setBodyTouchableViewHandler(bTouchableViewHandler);
        }
    }

    public void removedBodyTouchableViewHandler() {
        setBodyTouchableViewHandler(null);
    }

    //ADAPTER ITEMS CONTROLLER___________________________________

    /**
     * Devuelve la lista de elementos del título
     * <p>
     * [EN]  Returns the list of title elements
     *
     * @return Lista de elementos [EN]  List of items
     */
    @Nullable
    @Override
    public List<Selectable> getTitleItems() {
        return getAdapter() != null && getAdapter().getTitleAdapterController() != null
                ? getAdapter().getTitleAdapterController().getItems()
                : null;
    }

    @Nullable
    @Override
    public List<H> getHeadItems() {
        return getAdapter() != null && getAdapter().getHeadAdapterController() != null
                ? getAdapter().getHeadAdapterController().getItems()
                : null;
    }

    @Nullable
    @Override
    public List<B> getBodyItems() {
        return getAdapter() != null && getAdapter().getBodyAdapterController() != null
                ? getAdapter().getBodyAdapterController().getItems()
                : null;
    }


    @Nullable
    @Override
    public B get(int index) {
        return getAdapter() != null && getAdapter().getBodyAdapterController() != null
                ? getAdapter().getBodyAdapterController().get(index)
                : null;
    }

    @Override
    public void addAll(@Nullable List<H> items) {
        if (getAdapter() != null && getAdapter().getBodyAdapterController() != null) {
            getAdapter().getBodyAdapterController().addAll(items);
        }
    }

    @Override
    public void add(@Nullable B item) {
        if (item != null) {
            Log.w(LOG_TAG.TAG, "Insertado " + item.toString());
        }

        if (getAdapter() != null && getAdapter().getBodyAdapterController() != null) {
            getAdapter().getBodyAdapterController().add(item);
        }
    }

    @Override
    public void add(int index, @Nullable B item) {
        if (getAdapter() != null && getAdapter().getBodyAdapterController() != null) {
            getAdapter().getBodyAdapterController().add(index, item);
        }
    }

    @Override
    public void remove(int flatpos) {
        if (getAdapter() != null) {
            int viewtype = getAdapter().getItemViewType(flatpos);
            int index = getAdapter().indexPos(flatpos, viewtype);

            if()
            if (getAdapter() != null && getAdapter().getBodyAdapterController() != null) {
                getAdapter().getBodyAdapterController().remove(index);
            }
        }
    }

    @Override
    public void clear() {
        if (getAdapter() != null && getAdapter().getBodyAdapterController() != null) {
            getAdapter().getBodyAdapterController().clear();
        }
    }

    @Override
    public int size() {
        return adapter != null ? adapter.getItemCount() : 0;
    }

    @Override
    public boolean isEmpty() {
        return !(getAdapter() != null && getAdapter().getBodyAdapterController() != null) || getAdapter().getBodyAdapterController().isEmpty();
    }

    //SELECTEDS MODEL____________________________________________________________
    @Override
    @Nullable
    public List<B> removeSelectedItems() {
        return adapter != null && adapter.getSelectedsModel() != null ? adapter.getSelectedsModel().removeSelectedItems() : null;
    }

    @Override
    @Nullable
    public List<B> getSelectds() {
        return adapter != null && adapter.getSelectedsModel() != null ? adapter.getSelectedsModel().getSelectds() : null;
    }

    @Override
    @Nullable
    public B getItemSelected() {
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

    //SELECTIONABLE___________________________________________________________________________________

    /**
     * @param viewtype viewtype
     * @return Modo de selección de la lista [EN]  Selection mode of the list
     */
    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewtype) {
        return getAdapter() != null ? getAdapter().getSelectionmode(viewtype) : null;
    }

    /**
     * Filjar el modo de selección de la lista
     * <p>
     * [EN]  Filtering the mode selection mode of the list
     *
     * @param viewType      viewtype
     * @param selectionmode Modo de slección de la lista
     */
    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {
        if (getAdapter() != null) {
            getAdapter().setSelectionmode(viewType, selectionmode);
        }
    }
}
