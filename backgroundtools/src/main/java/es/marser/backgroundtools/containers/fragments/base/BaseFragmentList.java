package es.marser.backgroundtools.containers.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para la construcción de fragmentos con lista reciclable
 *         <p>
 *         [EN]  Base for the construction of fragments with recyclable list
 */

@SuppressWarnings("unused")
public abstract class BaseFragmentList extends BaseFragment {

    protected RecyclerView recyclerView;

    protected Integer lastScroll;

    public BaseFragmentList() {
        super();
    }

    /*Arranque [EN]  start*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //    Log.i(LOG_TAG.TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   Log.i(LOG_TAG.TAG, "onViewCreated");

    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG.TAG, "onActivityCreated");
        lastScroll = null;

        Log.w(LOG_TAG.TAG, "RecyclerView nula:  " + (recyclerView == null));

        if (recyclerView == null) {

            recyclerView = getActivity().findViewById(getRecyclerviewId());
            recyclerView.setHasFixedSize(hasFixedSize());
            recyclerView.setLayoutManager(getLayoutManager());
        }
        bindAdapter();
    }

    /*Vistas de componentes [EN]  Component views*/

    /**
     * Definición de la vista del recyclerview
     * <p>
     * [EN]  Definition of the view of recyclerview
     *
     * @return R.id.xxxxxx, por defecto {@link R.id#id_recyclerview} [EN]  default {@link R.id#id_recyclerview}
     */
    protected int getRecyclerviewId() {
        return R.id.id_recyclerview;
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


    /**
     * Enlazar el adapter con la lista {@link RecyclerView}, y oyentes del adapter,
     * el método es llamado tras definir la lista {@link RecyclerView} en el método {@link #onActivityCreated(Bundle)} )}
     * <p>
     * [EN] Link the adapter with the list {@link RecyclerView}, and listeners of the adapter
     * ej {@link es.marser.backgroundtools.handlers.ViewItemHandler},
     * {@link es.marser.backgroundtools.handlers.TouchableViewHandler}
     * <p>
     * ej {@link es.marser.backgroundtools.handlers.ViewItemHandler},
     * {@link es.marser.backgroundtools.handlers.TouchableViewHandler}
     */
    protected abstract void bindAdapter();

    /**
     * Número de registros en la lista
     * <p>
     * [EN]  Number of records in the list
     *
     * @return Número de registros en la lista [EN]  Number of records in the list
     */
    public abstract int getItemCount();

    /**
     * Comprueba si la lista tiene registros
     * <p>
     * [EN]  Check if the list has records
     *
     * @return verdadero si no hay registros en la lista [EN]  true if there are no records in the list
     */
    public abstract boolean isEmpty();

    //MOVEMENT_______________________________________________________________________________________

    /**
     * Posiciona la vista en el prier elemento de la lista
     * <p>
     * [EN]  Position the view on the first item in the list
     */
    public void scrollToFirst() {
        if (!isEmpty()) {
            scrollToId(0);
        }
    }

    /**
     * Posicionar la vista en la posición señalada
     * <p>
     * [EN]  Position the view in the indicated position
     *
     * @param position posición plana en el adaptador [EN]  flat position on the adapter
     */
    public void scrollToId(int position) {
        if (position > -1 && position < getItemCount()) {
            try {
                recyclerView.scrollToPosition(position);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Guarda la posición del pimer elemento visible
     * <p>
     * [EN]  Save the position of the visible element pimer
     */
    public void savedScroll() {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            lastScroll = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        } else {
            lastScroll = 0;
        }
    }

    /**
     * Posiciona la vista en a útlima posición guardada
     * <p>
     * [EN]  Position the view in the last saved position
     */
    public void restoreScroll() {
        if (lastScroll != null && lastScroll < recyclerView.getAdapter().getItemCount() && lastScroll > -1) {
            try {
                recyclerView.scrollToPosition(lastScroll);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Posiciona el foco en la última posición de la lista de elementos
     * <p>
     * [EN]  Position the focus in the last position in the list of elements
     */
    public void scrollToLast() {
        scrollToId(getItemCount() - 1);

    }

    //ITEM DECORATOR___________________________________________________________________________________

    /**
     * Añadir un separador de elementos
     * <p>
     * [EN]  Add an element separator
     *
     * @param itemDecoration separador de elementos [EN]  element separator
     */
    protected void addItemDecorator(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * Añadir separador de elementos según
     * <p>
     * [EN]  Add item separator according
     *
     * @param itemDecoration separador de elementos [EN]  element separator
     * @param index          índice del separador [EN]  index of the separator
     */
    protected void addItemDecorator(RecyclerView.ItemDecoration itemDecoration, int index) {
        recyclerView.addItemDecoration(itemDecoration, index);
    }


}
