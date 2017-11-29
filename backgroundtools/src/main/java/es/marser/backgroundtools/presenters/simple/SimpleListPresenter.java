package es.marser.backgroundtools.presenters.simple;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapterDecrep;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapter;
import es.marser.backgroundtools.presenters.base.ListModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 */

@SuppressWarnings("unused")
public class SimpleListPresenter<T extends Parcelable> implements ListModel, TouchableViewHandler<T>, ViewItemHandler<T> {

    protected Context context;
    protected SimpleListAdapter<T> adapter;

    //CONSTRUCTORS_____________________________________________
    public SimpleListPresenter(Context context) {
        this.context = context;
        adapter = new SimpleListAdapter<>();
    }

    public SimpleListPresenter(Context context, int holderLayout) {
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


    //EVENTS_______________________________________________________
    /* {@link ViewItemHandler}*/
    @Override
    public void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {

    }

    @Override
    public boolean onLongClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
        return false;
    }

    /* {@link TouchableViewHandler}*/
    @Override
    public void onClick(View view, int position, T item, View root) {

    }

    @Override
    public boolean onLongClick(View view, int position, T item, View root) {
        return false;
    }
}
