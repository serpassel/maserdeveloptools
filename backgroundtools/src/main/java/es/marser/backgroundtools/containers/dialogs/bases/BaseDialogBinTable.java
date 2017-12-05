package es.marser.backgroundtools.containers.dialogs.bases;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.base.model.Selectionable;
import es.marser.backgroundtools.listables.table.model.TableAdapterModel;
import es.marser.backgroundtools.listables.table.presenter.TableListPresenter;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Base de construccion de dialogos de lista
 *         <p>
 *         [EN]  Basis of constructing list dialogs
 */

@SuppressWarnings("unused")
public abstract class BaseDialogBinTable<
        H extends Parcelable, B extends Parcelable,
        TAM extends TableAdapterModel<H, B>,
        TLP extends TableListPresenter<H, B, TAM>
        >
        extends BaseDialogBin
        implements Selectionable {

    protected TAM tableListModel;
    protected TLP presenter;

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
        viewDataBinding.setVariable(BR.listmodel, tableListModel);
        viewDataBinding.executePendingBindings();

        if (presenter.getListmodel() == null) {
            presenter.setListModel(tableListModel);
        }
    }

    //SELECTIONABLE________________________________________________________
    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewType) {
        return tableListModel != null ? tableListModel.getSelectionmode(viewType) : null;
    }

    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {
        if (tableListModel != null) {
            tableListModel.setSelectionmode(viewType, selectionmode);
        }
    }

    //MVP PATTERN

    public TAM getSimpleListModel() {
        return tableListModel;
    }

    public void setSimpleListModel(TAM tableListModel) {
        this.tableListModel = tableListModel;
        if (presenter != null) {
            presenter.setListModel(this.tableListModel);
        }
    }

    public TLP getPresenter() {
        return presenter;
    }

    public void setPresenter(TLP presenter) {
        this.presenter = presenter;
    }
}
