package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapter;
import es.marser.backgroundtools.presenters.base.ListModel;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para la construcción de fragmentos con lista reciclable
 *         <p>
 *         [EN]  Base for the construction of fragments with recyclable list
 */

@SuppressWarnings("unused")
public abstract class BaseFragmentListBin<T extends Parcelable>
        extends BaseFragment
        implements ListModel, TouchableViewHandler<T>, ViewItemHandler<T> {

    protected ViewDataBinding viewDataBinding;
    protected RecyclerView recyclerView;
    protected SimpleListAdapter<T> adapter;

    protected Integer lastScroll;

    public BaseFragmentListBin() {
        super();
    }

    /**
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false);
        init();
        return viewDataBinding.getRoot();
    }

    private void init(){
        adapter = new SimpleListAdapter<T>() {

            /**
             * Vista del item
             * <p>
             * [EN]  Item view
             *
             * @return variable de vista de los elementos [EN]  variable view of the elements
             */
            @Override
            protected int getHolderLayout() {
                return BaseFragmentListBin.this.getHolderLayout();
            }

            @Override
            public TouchableViewHandler<T> getTouchableViewHandler() {
                return BaseFragmentListBin.this;
            }

            @Override
            public ViewItemHandler<T> getItemHandler() {
                return BaseFragmentListBin.this;
            }
        };
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
        bindAdapter(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvp_frag_simple_list;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public boolean isHasFixedSize() {
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

    protected void binObjects(@Nullable Bundle savedInstanceState) {
    }

    protected abstract int getHolderLayout();

    protected void bindAdapter(@Nullable Bundle savedInstanceState){
        viewDataBinding.setVariable(BR.listmodel, this);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public BaseListAdapter getAdapter() {
        adapter.globalController.selectionController.setSelectionMode(getInitialSelectionMode());
        return adapter;
    }

    public boolean isEmpty() {
        return adapter.globalController.isEmpty();
    }

    public int getItemCount() {
        return adapter.getItemCount();
    }

    //VIEW EVENT HANDLERS_____________________________________________________________________________

    /*{@link TouchableViewHandler}*/
    /*Eventos de pulsación sobre la vista raiz
    [EN]  Pulsation Events on the Root View*/
    @Override
    public void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
    }

    @Override
    public boolean onLongClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
        return true;
    }

    /*{@link ViewItemHandler}*/
    /*Eventos de pulsación en las vistas anidadas sobre la vista principal
    [EN]  Pulsation events in nested views over the main view*/
    @Override
    public void onClick(View view, int position, T item, View root) {

    }

    @Override
    public boolean onLongClick(View view, int position, T item, View root) {
        return true;
    }


}
