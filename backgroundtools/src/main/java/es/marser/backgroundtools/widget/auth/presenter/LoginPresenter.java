package es.marser.backgroundtools.widget.auth.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnDResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.widget.inputbox.model.BoxModel;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Presentador para login de usuario
 *         <p>
 *         [EN]  Presenter for user login
 */

public class LoginPresenter extends DialogBasePresenter {

    //RESULT________________________________________
    private OnDResult<String, String> result;

    //MODELS________________________________________
    private BoxModel user;
    private BoxModel password;

    //CONTRUCTORS__________________________________
    public LoginPresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
    }

    public LoginPresenter(@NonNull Context context) {
        this(context, R.layout.mvp_dialog_login);
    }

    //OVERRIDE______________________________________
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.user, user);
        binderContainer.bindObject(BR.password, password);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            user = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name());
            password = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX2_EXTRA.name());

            if (user == null) {
                user = new BoxModel();
            }

            if (password == null) {
                password = new BoxModel();
            }
        }
    }

    //WINACTION______________________________________
    @Override
    public void onOk(View v) {
        if (user.validate() && password.validate()) {
            if (user.getInputType() == BoxModel.textPassword) {
                if (TextTools.validateAndConfirmPassword(user.getBody(), password.getBody())) {
                    password.setErrorText("Las contraseñas no coinciden");
                    return;
                }
            }
            result.onResult(DialogExtras.OK_EXTRA, user.getBody(), password.getBody());
            close();
        }
    }

    @Override
    public void onCancel(View v) {
        result.onResult(DialogExtras.CANCEL_EXTRA, user.getBody(), password.getBody());
        close();
    }

    //PROPERTIES____________________________________
    public OnDResult<String, String> getResult() {
        return result;
    }

    public void setResult(OnDResult<String, String> result) {
        this.result = result;
    }

    public BoxModel getUser() {
        return user;
    }

    public void setUser(BoxModel user) {
        this.user = user;
    }

    public BoxModel getPassword() {
        return password;
    }

    public void setPassword(BoxModel password) {
        this.password = password;
    }
}
