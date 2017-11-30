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
public abstract class BaseFragmentListBin<
        T extends Parcelable,
        SLM extends SimpleListModel<T>,
        SLP extends SimpleListPresenter<T, SLM>
        >
        extends BaseFragmentBin
        implements Selectionable {

    protected ViewDataBinding viewDataBinding;

    protected SLM simpleListModel;
    protected SLP presenter;

    protected Integer lastScroll;

    public BaseFragmentListBin() {
        super();
    }

    //OVERRRIDE___________________________________________________________________________


    //BIN METHODS OF CONFIGURATION________________________________________________________
    /**
     * Enlace de objetos en la vista principal. Obligatorio que la variable de modelo en la vista se denomine model
     * Se ejecuta durante al crearse la vista {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * <p>
     * [EN]  Link objects in the main view.  Obligatory that the model variable in the view is called model
     * Runs during view creation {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     *
     * @param savedInstanceState argumentos de recuperación
     */
    @Override
    protected void binObjects(@Nullable Bundle savedInstanceState) {
        bindAdapter(savedInstanceState);
    }

    /**
     * Enlace de objetos con la vista de lista
     * <p>
     * [EN]  Link objects with list view
     */
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        viewDataBinding.setVariable(BR.listmodel, simpleListModel);
        viewDataBinding.executePendingBindings();

        if (presenter.getListModel() == null) {
            presenter.setListModel(simpleListModel);
        }
    }

    //VIEWS________________________________________________________________
    @Override
    protected int getFragmentLayout() {
        return R.layout.mvp_frag_simple_list;
    }

    //SELECTIONABLE________________________________________________________
    @Nullable
    @Override
    public ListExtra getSelectionmode() {
        return simpleListModel.getSelectionmode();
    }

    @Override
    public void setSelectionmode(@NonNull ListExtra selectionmode) {
        if (simpleListModel != null) {
            simpleListModel.setSelectionmode(selectionmode);
        }
    }

    //MVP PATTERN
    @NonNull
    public SLM getSimpleListModel() {
        return simpleListModel;
    }

    public void setSimpleListModel(@NonNull SLM simpleListModel) {
        this.simpleListModel = simpleListModel;

        if (presenter != null) {
            presenter.setListModel(simpleListModel);
        }
    }

    @NonNull
    public SLP getPresenter() {
        return presenter;
    }

    public void setPresenter(@NonNull SLP presenter) {
        this.presenter = presenter;
    }
}
