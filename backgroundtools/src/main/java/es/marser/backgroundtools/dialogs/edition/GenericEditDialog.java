package es.marser.backgroundtools.dialogs.edition;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.BR;

import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.handlers.WindowAction;


/**
 * Created by Sergio on 09/03/2017.
 * Dialogo de  edicción de un elemento singular
 */

@SuppressWarnings("unused")
public class GenericEditDialog<T extends Parcelable> extends BaseCustomBinDialog implements WindowAction<T> {

    public static final int EDIT_ACTION = 100;
    public static final int ADD_ACTION = 101;
    public static final int INSERT_ACTION = 102;


    protected OnResult<T> result;
    protected T model;
    private int layout;

    public static final String ITEM_EXTRA = "item_extra";
    public static final String STATE_EXTRA = "state_extra";
    public static final String LAYOUT_EXTRA = "layout_extra";


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

    public static <T extends Parcelable> Bundle createBundle(int layout, Integer state, @NonNull T item) {
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_EXTRA, layout);
        bundle.putInt(STATE_EXTRA, (state != null ? state : -1));
        bundle.putParcelable(ITEM_EXTRA, item);
        return bundle;
    }

    public static <T extends Parcelable> Bundle createBundle(int layout, @NonNull T item) {
        return createBundle(layout, -1, item);
    }

    @Override
    protected void preBuild() {
        this.model = getArguments().getParcelable(ITEM_EXTRA);
        this.layout = getArguments().getInt(LAYOUT_EXTRA, -1);
        state.set(getArguments().getInt(STATE_EXTRA, -1));
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
        Log.i(LOG_TAG.TAG, "Cerrado");
        result.onResult(Activity.RESULT_CANCELED, model);
        close();
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