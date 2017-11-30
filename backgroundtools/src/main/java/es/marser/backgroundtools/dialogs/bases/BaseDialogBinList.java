package es.marser.backgroundtools.dialogs.bases;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.model.Selectionable;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.objectslistables.simple.presenter.SimpleListPresenter;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Base de construccion de dialogos de lista
 *         <p>
 *         [EN]  Basis of constructing list dialogs
 */

@SuppressWarnings("unused")
public abstract class BaseDialogBinList<T extends Parcelable>
        extends BaseDialogBinModel implements Selectionable {

    /**
     * Métodos e inicio de variables previas a la construcción del dialogo. Opcional
     * <p>
     * [EN]  Methods and start of variables prior to the construction of the dialogue.  Optional
     */
    @Override
    protected void preBuild() {
        super.preBuild();
        initPresenterModel();
    }

    /**
     * Métodos e inicio de variables posteriores a la construcción del dialogo. Opcional
     * <p>
     * [EN]  Methods and start of variables after the construction of the dialogue.  Optional
     */
    @Override
    protected void postBuild() {
        super.postBuild();
        bindAdapter();
    }


    //ABSTRACT METHODS OF CONFIGURATION_______________________________________________________________
    protected void bindAdapter() {
        viewDataBinding.setVariable(BR.listmodel, getSimpleListModel());
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
}
