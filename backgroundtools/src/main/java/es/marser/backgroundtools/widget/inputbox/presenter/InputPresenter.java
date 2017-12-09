package es.marser.backgroundtools.widget.inputbox.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.widget.inputbox.model.BoxModel;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Presentador para entradas de valores
 *         <p>
 *         [EN]  Presenter for securities entries
 */

@SuppressWarnings("unused")
public class InputPresenter extends DialogBasePresenter {
    /*Variable de resultado [EN]  Result variable*/
    protected OnResult<String> result;
    /*Variable de configuración [EN]  Configuration variable*/
    protected BoxModel boxModel;

    //CONSTRUCTORS_________________________________________
    public InputPresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
    }

    public InputPresenter(@NonNull Context context) {
        this(context, R.layout.mvp_dialog_inputbox);
    }

    //OVERRIDE_______________________________________________
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.boxmodel, boxModel);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            boxModel = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name());
        }

        if (boxModel == null) {
            boxModel = new BoxModel();
        }
    }

    //WINACTION______________________________________________
    @Override
    public void onOk(View v) {
        if (boxModel.validate()) {
            result.onResult(DialogExtras.OK_EXTRA, boxModel.getBody());
            close();
        }
    }

    @Override
    public void onCancel(View v) {
        result.onResult(DialogExtras.CANCEL_EXTRA, boxModel.getBody());
        close();
    }

    //PROPERTIES_____________________________________________
    @SuppressWarnings("unused")
    public OnResult<String> getResult() {
        return result;
    }

    public void setResult(OnResult<String> result) {
        this.result = result;
    }

    @SuppressWarnings("unused")
    public BoxModel getBoxModel() {
        return boxModel;
    }

    @SuppressWarnings("unused")
    public void setBoxModel(BoxModel boxModel) {
        this.boxModel = boxModel;
    }
}
