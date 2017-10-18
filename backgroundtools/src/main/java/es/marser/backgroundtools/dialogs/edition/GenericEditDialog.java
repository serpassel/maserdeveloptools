package es.marser.backgroundtools.dialogs.edition;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.handlers.WindowAction;

import static es.marser.backgroundtools.dialogs.edition.GenericEditDialog.EDIT_DIALOG_ACTION.NULL_ACTION;
import static es.marser.backgroundtools.dialogs.edition.GenericEditDialog.EDIT_DIALOG_EXTRAS.ITEM_EXTRA;
import static es.marser.backgroundtools.dialogs.edition.GenericEditDialog.EDIT_DIALOG_EXTRAS.LAYOUT_EXTRA;
import static es.marser.backgroundtools.dialogs.edition.GenericEditDialog.EDIT_DIALOG_EXTRAS.STATE_EXTRA;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Dialogo de edición de un elemento singular
 *         <p>
 *         [EN]  Dialog for editing a single element
 */

@SuppressWarnings("unused")
public class GenericEditDialog<T extends Parcelable> extends BaseCustomBinDialog implements WindowAction<T> {

    protected OnResult<T> result;
    protected T model;
    private int layout;

    /*enumeracion de llaves de argumentos [EN]  enumeration of argument keys */
    public enum EDIT_DIALOG_EXTRAS {
        ITEM_EXTRA, STATE_EXTRA, LAYOUT_EXTRA, ACTION_EXTRA
    }

    /*Enumeración de acciones [EN]  Enumeration of actions*/
    public enum EDIT_DIALOG_ACTION {
        NULL_ACTION, EDIT_ACTION, ADD_ACTION, INSERT_ACTION
    }

    public static <T extends Parcelable> GenericEditDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnResult<T> result) {

        GenericEditDialog<T> instance = new GenericEditDialog<>();
        instance.setArguments(bundle);
        instance.setContext(context);
        instance.setResult(result);
        return instance;
    }

    public static <T extends Parcelable> Bundle createBundle(int layout, EDIT_DIALOG_ACTION action, @NonNull T item) {
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
        this.model = getArguments().getParcelable(ITEM_EXTRA.name());
        this.layout = getArguments().getInt(LAYOUT_EXTRA.name(), -1);
        state.set(getArguments().getInt(STATE_EXTRA.name(), 0));
    }

    @Override
    protected void postBuild() {
        if (model instanceof Editable) {
            ((Editable) model).setEditing(true);
        }
    }

    @Override
    public void bindObject() {
        viewDataBinding.setVariable(BR.model, model);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.winaction, this);
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected int getDialogLayout() {
        return layout;
    }

    @Override
    public void close() {
        if (model instanceof Editable) {
            ((Editable) model).setEditing(false);
        }
        super.close();
    }

    @Override
    public void onClick(View v, T value) {
        if (result != null) {
            result.onClick(v, value);
        }
    }

    @Override
    public boolean onLongClick(View v, T model) {
        return false;
    }

    @Override
    public void onOk(View v) {
        result.onResult(Activity.RESULT_OK, model);
        close();
    }

    @Override
    public void onCancel(View v) {
        result.onResult(Activity.RESULT_CANCELED, model);
        close();
    }

    @Override
    public void onOption1(View v) {

    }

    @Override
    public void onOption2(View v) {

    }

    @SuppressWarnings({"EmptyMethod", "unused", "UnusedParameters"})
    public interface OnResult<T> {
        void onResult(int result, T value);

        void onClick(View v, T value);
    }

    public OnResult<T> getResult() {
        return result;
    }

    public void setResult(OnResult<T> result) {
        this.result = result;
    }
}