package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
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

    public BaseFragmentListBin() {
        super();
    }

    //SAVED AND RESTORE____________________________________________________________________
    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        simpleListModel.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

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

    //LOAD DATA___________________________________________________________
    /**
     * Métodos de inicio de variables posteriores a la construcción del dialogo y a la vinculación de datos {@link #bindObject()}. Opcional
     * Se ejecuta en {@link #onActivityCreated(Bundle)}
     * <p>
     * Methods and start of variables after the construction of the dialogue and the data link {@link #bindObject()}
     *
     * @param savedInstanceState argumentos guardados [EN]  saved arguments
     */
    @Override
    protected void postBuild(@Nullable Bundle savedInstanceState) {
        super.postBuild(savedInstanceState);
        if(savedInstanceState !=null){
            simpleListModel.onSaveInstanceState(savedInstanceState);
            presenter.onSaveInstanceState(savedInstanceState);
        }else{
            presenter.load(getArguments());
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
