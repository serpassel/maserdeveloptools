package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.model.Selectionable;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.objectslistables.simple.presenter.SimpleListPresenter;

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
        implements Selectionable {

    protected ViewDataBinding viewDataBinding;

    //protected SimpleListModel<T> listModel;
    //protected SimpleListPresenter<T> presenter;

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
        initPresenterModel();
        return viewDataBinding.getRoot();
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

    //VIEWS________________________________________________________________
    @Override
    protected int getFragmentLayout() {
        return R.layout.mvp_frag_simple_list;
    }

    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        viewDataBinding.setVariable(BR.listmodel, getSimpleListModel());
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.handler, getSimpleListPresenter());
        viewDataBinding.executePendingBindings();
    }

    /**
     * Iniciar variables de Presenter y Model y repercutir en los métodos get
     * <p>
     * [EN]  Start Presenter and Model variables and affect the get methods
     *
     * {@link #getSimpleListModel}
     * {@link #getSimpleListPresenter}
     */
    protected abstract void initPresenterModel();

    @NonNull
    protected abstract SimpleListModel<T> getSimpleListModel();

    @NonNull
    protected abstract SimpleListPresenter<T> getSimpleListPresenter();

    //SELECTIONABLE________________________________________________________
    @Nullable
    @Override
    public ListExtra getSelectionmode() {
        return getSimpleListModel().getSelectionmode();
    }

    @Override
    public void setSelectionmode(@NonNull ListExtra selectionmode) {
        getSimpleListModel().setSelectionmode(selectionmode);
    }
}
