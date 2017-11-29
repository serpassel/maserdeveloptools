package es.marser.backgroundtools.objectslistables.base.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapterDecrep;
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
        implements AdapterItemsModel, AdapterItemsManager<T> {

    protected Context context;
    protected SimpleListAdapter<T> adapter;

    protected AdapterItemsController<T> listcrud;

    //CONSTRUCTORS_____________________________________________
    public SimpleListModel(Context context) {
        this.context = context;
        adapter = new SimpleListAdapter<>();
    }

    public SimpleListModel(Context context, int holderLayout) {
        this.context = context;
        adapter = new SimpleListAdapter<>(holderLayout);
    }

    //LIST MODEL_________________________________________________

    /**
     * @return Adaptador de listas {@link BaseListAdapterDecrep}
     */
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
        return true;
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
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
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

    //CRUD_______________________________________________________
    /**
     * @return devuelve el gestor de lectura y escritura asignado al manejador
     * <p>
     * [EN]  returns the read and write manager assigned to the handler
     */
    @Nullable
    @Override
    public AdapterItemsController<T> getAdapterItemsController() {
        return adapter != null ? adapter.getAdapterItemsController() : null;
    }
}
