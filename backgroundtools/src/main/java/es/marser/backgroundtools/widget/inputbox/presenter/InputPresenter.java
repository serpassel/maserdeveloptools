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
import es.marser.backgroundtools.widget.inputbox.model.BoxSettings;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Presentador para entradas de valores
 *         <p>
 *         [EN]  Presenter for securities entries
 */

public class InputPresenter extends DialogBasePresenter {
    /*Variable de resultado [EN]  Result variable*/
    protected OnResult<String> result;
    /*Variable de configuración [EN]  Configuration variable*/
    protected BoxSettings boxSettings;

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
        binderContainer.bindObject(BR.settings, boxSettings);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            boxSettings = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name());
        }

        if (boxSettings == null) {
            boxSettings = new BoxSettings();
        }
    }

    //WINACTION______________________________________________
    @Override
    public void onOk(View v) {
        if (boxSettings.validate()) {
            result.onResult(DialogExtras.OK_EXTRA, boxSettings.getBody());
            close();
        }
    }

    @Override
    public void onCancel(View v) {
        result.onResult(DialogExtras.CANCEL_EXTRA, boxSettings.getBody());
        close();
    }

    //PROPERTIES_____________________________________________
    public OnResult<String> getResult() {
        return result;
    }

    public void setResult(OnResult<String> result) {
        this.result = result;
    }

    public BoxSettings getBoxSettings() {
        return boxSettings;
    }

    public void setBoxSettings(BoxSettings boxSettings) {
        this.boxSettings = boxSettings;
    }
}
