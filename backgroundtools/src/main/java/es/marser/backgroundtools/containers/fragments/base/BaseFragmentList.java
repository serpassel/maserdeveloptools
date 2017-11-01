package es.marser.backgroundtools.containers.fragments.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para la construcción de fragmentos con lista reciclable
 *         <p>
 *         [EN]  Base for the construction of fragments with recyclable list
 */

public abstract class BaseFragmentList extends BaseFragment {

    protected RecyclerView recyclerView;

    protected Integer lastScroll;


     /*Vistas de componentes [EN]  Component views*/

    /**
     * Definición de la vista del recyclerview
     * <p>
     * [EN]  Definition of the view of recyclerview
     *
     * @return R.id.xxxxxx, por defecto {@link R.id#com_recyclerview} [EN]  default {@link R.id#com_recyclerview}
     */
    protected int getRecyclerviewId() {
        return R.id.com_recyclerview;
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
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
    @SuppressWarnings("SameReturnValue")
    protected boolean hasFixedSize() {
        return true;
    }

    /*Inicio de métodos [EN]  Start of methods*/

    /**
     * Modo de selección inicial de la lista. Por defecto Mode de selección sencilla
     * <p>
     * [EN]  Initial selection mode of the list.  Default Simple selection mode.
     *
     * @return Modo de selección sencilla {@link ListExtra#SINGLE_SELECTION_MODE}
     */
    @SuppressWarnings("SameReturnValue")
    protected ListExtra getInitialSelectionMode() {
        return ListExtra.SINGLE_SELECTION_MODE;
    }

    @Override
    protected void initActivityCreated() {
        lastScroll = null;
        recyclerView = getActivity().findViewById(getRecyclerviewId());
        recyclerView.setHasFixedSize(hasFixedSize());
        recyclerView.setLayoutManager(getLayoutManager());
        bindAdapter();
    }

    /**
     * Enlazar el adapter con la lista {@link RecyclerView}, y oyentes del adapter,
     * el método es llamado tras definir la lista {@link RecyclerView} en el método {@link BaseFragment#initActivityCreated()}
     * <p>
     *[EN] Link the adapter with the list {@link RecyclerView}, and listeners of the adapter
     *  ej {@link es.marser.backgroundtools.handlers.ViewItemHandler},
     * {@link es.marser.backgroundtools.handlers.TouchableViewHandler}
     * <p>
     *  ej {@link es.marser.backgroundtools.handlers.ViewItemHandler},
     * {@link es.marser.backgroundtools.handlers.TouchableViewHandler}
     */
    protected abstract void bindAdapter();

}
