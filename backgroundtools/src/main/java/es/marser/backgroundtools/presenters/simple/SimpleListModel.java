package es.marser.backgroundtools.presenters.simple;

import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.presenters.base.ListModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Modelo para listas simples. Las variables adapter y layoutManager, no pueden ser nulas
 *         <p>
 *         [EN]  Model for simple lists.  The adapter and layoutManager variables can not be null
 */

@SuppressWarnings("unused")
public class SimpleListModel implements ListModel {

    private BaseListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean hasFixedSize;

    public SimpleListModel() {
    }

    public SimpleListModel(BaseListAdapter adapter, RecyclerView.LayoutManager layoutManager) {
        this(adapter, layoutManager, true);
    }

    public SimpleListModel(BaseListAdapter adapter, RecyclerView.LayoutManager layoutManager, boolean hasFixedSize) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        this.hasFixedSize = hasFixedSize;
    }

    public void setAdapter(BaseListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        this.hasFixedSize = hasFixedSize;
    }

    @Override
    public BaseListAdapter getAdapter() {
        return adapter;
    }

    /**
     * Verdadero si la aplicación ha especificado que los cambios
     * en el contenido del adaptador no pueden cambiar el tamaño de RecyclerView.
     * <p>
     * [EN] true if the app has specified that changes
     * in adapter content cannot change the size of the RecyclerView itself.
     *
     * @return Verdadero si la aplicación ha especificado que los cambios
     * en el contenido del adaptador no pueden cambiar el tamaño de RecyclerView.
     * [EN] true if the app has specified that changes
     * in adapter content cannot change the size of the RecyclerView itself.
     */
    @Override
    public boolean isHasFixedSize() {
        return hasFixedSize;
    }

    /**
     * Definición del gestor del layout de la lista. Por defecto se utilizará el lineal
     * <p>
     * [EN]  Definition of the layout manager of the list.  By default the linear
     *
     * @return gestor del layout {@link LinearLayoutManager#VERTICAL}
     * Opcional puede ser {@link GridLayoutManager}
     * [EN]  gestor del layout {@link LinearLayoutManager#VERTICAL}
     * Optional can be {@link GridLayoutManager}
     */
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }
}
