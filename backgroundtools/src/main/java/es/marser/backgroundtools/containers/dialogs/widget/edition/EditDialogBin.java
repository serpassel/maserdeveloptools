package es.marser.backgroundtools.containers.dialogs.widget.edition;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinDecrep;
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
        extends BaseDialogBinDecrep
        implements ViewHandler<T> {

    protected OnResult<T> result;
    protected T headmodel;
    private int layout;

    public static <T extends Parcelable> EditDialogBin newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnResult<T> result) {

        EditDialogBin<T> instance = new EditDialogBin<>();
        instance.setArguments(bundle);
        instance.setContext(context);
        instance.setResult(result);
        return instance;
    }

    public static <T extends Parcelable> Bundle createBundle(int layout, DialogExtras action, @NonNull T item) {
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_EXTRA.name(), layout);
        bundle.putInt(STATE_EXTRA.name(), (action != null ? action.ordinal() : 0));
        bundle.putParcelable(ITEM_EXTRA.name(), item);
        return bundle;
    }

    public static <T extends Parcelable> Bundle createBundle(int layout, @NonNull T item) {
        return createBundle(layout, NULL_ACTION, item);
    }

    @Override
    protected void preBuild() {
        this.headmodel = getArguments().getParcelable(ITEM_EXTRA.name());
        this.layout = getArguments().getInt(LAYOUT_EXTRA.name(), -1);
        statusModel.state.set(getArguments().getInt(STATE_EXTRA.name(), 0));

        //Configurar botones [EN]  Configure buttons
        buttonsSetModel.ok_name.set(context.getResources().getString(R.string.bt_ACTION_SAVE));
        buttonsSetModel.cancel_name.set(context.getResources().getString(R.string.bt_ACTION_CANCEL));
    }

    @Override
    protected void postBuild() {
        if (headmodel instanceof Editable) {
            ((Editable) headmodel).setEditing(true);
        }
    }

    @Override
    public void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.headmodel, headmodel);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.handler, this);
        viewDataBinding.executePendingBindings();

    }

    @Override
    protected int getDialogLayout() {
        return layout;
    }

    @Override
    public void close() {
        if (headmodel instanceof Editable) {
            ((Editable) headmodel).setEditing(false);
        }
        super.close();
    }

    @Override
    public void onClick(View view, T value) {
        if (result != null) {
            result.onClick(view, value);
        }
    }

    @Override
    public boolean onLongClick(View view, T model) {
        return false;
    }

    @Override
    public void onOk(View v) {
        result.onResult(DialogExtras.OK_EXTRA, headmodel);
        close();
    }

    @Override
    public void onCancel(View v) {
        result.onResult(DialogExtras.CANCEL_EXTRA, headmodel);
        close();
    }

    @Override
    public void onOption(View v) {
        result.onResult(DialogExtras.OPTION_EXTRA, headmodel);
        close();
    }

    public OnResult<T> getResult() {
        return result;
    }

    public void setResult(OnResult<T> result) {
        this.result = result;
    }
}