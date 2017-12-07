package es.marser.backgroundtools.containers.dialogs.bases;

import es.marser.backgroundtools.containers.dialogs.presenter.DialogListPresenter;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Base de construccion de dialogos de lista
 *         <p>
 *         [EN]  Basis of constructing list dialogs
 */

@SuppressWarnings("unused")
public abstract class BaseDialogBinList<DLP extends DialogListPresenter>
        extends BaseDialogBin<DLP> {

    @Override
    protected void postBuild() {
        super.postBuild();
        if (presenter.isEmpty()) {
            presenter.load(getArguments());
        }
    }
}
