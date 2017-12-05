package es.marser.backgroundtools.containers.dialogs.bases;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.base.model.Selectionable;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;
import es.marser.backgroundtools.listables.simple.presenter.SimpleListPresenter;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Base de construccion de dialogos de lista
 *         <p>
 *         [EN]  Basis of constructing list dialogs
 */

@SuppressWarnings("unused")
public abstract class BaseDialogBinList<
        T extends Parcelable,
        SLM extends SimpleAdapterModel<T>,
        SLP extends SimpleListPresenter<T, SLM>
        >
        extends BaseDialogBin
        implements Selectionable {

    protected SLM simpleListModel;
    protected SLP presenter;

    //BIN METHODS OF CONFIGURATION________________________________________________________

    /**
     * Enlace de objetos en la vista principal. Obligatorio que la variable de modelo en la vista se denomine model
     * <p>
     * [EN]  Link objects in the main view.  Obligatory that the model variable in the view is called model
     */
    @Override
    protected void bindObject() {
        super.bindObject();
        bindAdapter();
    }

    /**
     * Enlace de objetos con la vista de lista
     * <p>
     * [EN]  Link objects with list view
     */
    protected void bindAdapter() {
        viewDataBinding.setVariable(BR.listmodel, simpleListModel);
        viewDataBinding.executePendingBindings();

        if (presenter.getListmodel() == null) {
            presenter.setListmodel(simpleListModel);
        }
    }

    //SELECTIONABLE________________________________________________________
    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewType) {
        return simpleListModel != null ? simpleListModel.getSelectionmode(viewType) : null;
    }

    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {
        if (simpleListModel != null) {
            simpleListModel.setSelectionmode(viewType, selectionmode);
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
            presenter.setListmodel(simpleListModel);
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
