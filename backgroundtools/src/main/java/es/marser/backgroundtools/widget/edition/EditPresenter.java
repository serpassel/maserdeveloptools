package es.marser.backgroundtools.widget.edition;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Presentador para edición de objetos
 *         <p>
 *         [EN]  Presenter for object editing
 */

public class EditPresenter<T extends Parcelable> extends DialogBasePresenter {
    protected OnResult<T> result;
    protected T headmodel;

    //CONSTRUCTORS____________________________________
    public EditPresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
    }

    //OVERRIDE________________________________________

    /**
     * Indicador del conmienzo de la vinculación de vistas {@link ViewDataBinding}
     * <p>
     * [EN]  Join linking view indicator
     *
     * @param binderContainer Objeto de enlace de vistas [EN]  View link object
     */
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.headmodel, headmodel);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

    }

    //VARIABLES________________________________________________

    public OnResult<T> getResult() {
        return result;
    }

    public void setResult(OnResult<T> result) {
        this.result = result;
    }

    public T getHeadmodel() {
        return headmodel;
    }

    public void setHeadmodel(T headmodel) {
        this.headmodel = headmodel;
        if (this.headmodel instanceof Editable) {
            ((Editable) this.headmodel).setEditing(true);
        }
    }

    //WINACTION________________________________________________
    @Override
    public void onOk(View v) {
        if (this.headmodel != null && this.headmodel instanceof Editable) {
            ((Editable) this.headmodel).setEditing(false);
        }
        result.onResult(DialogExtras.OK_EXTRA, headmodel);
        close();
    }

    @Override
    public void onCancel(View v) {
        if (this.headmodel != null && this.headmodel instanceof Editable) {
            ((Editable) this.headmodel).setEditing(false);
        }
        result.onResult(DialogExtras.CANCEL_EXTRA, headmodel);
        close();
    }

    @Override
    public void onOption(View v) {
        if (this.headmodel != null && this.headmodel instanceof Editable) {
            ((Editable) this.headmodel).setEditing(false);
        }
        result.onResult(DialogExtras.OPTION_EXTRA, headmodel);
        close();
    }
}
