package es.marser.backgroundtools.containers.dialogs.bases;

import android.os.Parcelable;

import es.marser.backgroundtools.containers.dialogs.presenter.DialogListPresenter;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;

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
        DLP extends DialogListPresenter<SimpleAdapterModel<T>>
        >
        extends BaseDialogBin<DLP> {
}
