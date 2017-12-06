package es.marser.backgroundtools.widget.edition;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinDecrep;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.handlers.ViewHandler;

import static es.marser.backgroundtools.enums.DialogExtras.ITEM_EXTRA;
import static es.marser.backgroundtools.enums.DialogExtras.LAYOUT_EXTRA;
import static es.marser.backgroundtools.enums.DialogExtras.NULL_ACTION;
import static es.marser.backgroundtools.enums.DialogExtras.STATE_EXTRA;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Dialogo de edición de un elemento singular
 *         <p>
 *         [EN]  Dialog for editing a single element
 */

@SuppressWarnings("unused")
public class EditDialogBin<T extends Parcelable>
        extends BaseDialogBin<EditPresenter<T>> {

    public static <T extends Parcelable> EditDialogBin newInstance(
            @NonNull Context context,
            int layout,
            @NonNull T item,
            @NonNull OnResult<T> result) {

        EditPresenter<T> presenter = new EditPresenter<>(context, layout);
        presenter.setResult(result);
        presenter.setArguments(createBundle(context, item));

        EditDialogBin<T> instance = new EditDialogBin<>();
        instance.setContext(context);
        instance.setPresenter(presenter);
        return instance;
    }

    private static <T extends Parcelable> Bundle createBundle(@NonNull Context context, @NonNull T item) {
        Bundle bundle = new Bundle();

        bundle.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_SAVE),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                null
        ));
        bundle.putParcelable(ITEM_EXTRA.name(), item);
        return bundle;
    }

}