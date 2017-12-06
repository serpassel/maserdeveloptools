package es.marser.backgroundtools.widget.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinDecrep;
import es.marser.backgroundtools.containers.dialogs.task.OnDResult;
import es.marser.backgroundtools.widget.inputbox.model.BoxModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/11/17.
 *         Cuadro de dialogo para autentificación de usuario
 *         <p>
 *         [EN]  Dialog box for user authentication
 */

@SuppressWarnings("unused")
public class DialogLogin extends BaseDialogBinDecrep {

    protected OnDResult<String, String> result;

    protected BoxModel user;
    protected BoxModel password;

    //INSTANCE____________________________________________________________________
    public static DialogLogin newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnDResult<String, String> result) {

        DialogLogin instance = new DialogLogin();
        instance.setArguments(bundle);
        instance.setContext(context);
        instance.setResult(result);
        return instance;
    }

    //BUNDLE_____________________________________________________________________

    /**
     * Creación de argumentos
     * <p>
     * [EN]  Creating arguments
     *
     * @param icon        Icono de cabecera [EN]  Header icon
     * @param title       Título de cabecera [EN]  Header title
     * @param boxSettings Configuración de la caja de texto [EN]  Text box settings
     * @return Argumentos de configuración [EN]  Configuration arguments
     */
    public static Bundle createBundle(
            @NonNull DialogIcon icon,
            String title,
            @NonNull BoxModel user,
            @NonNull BoxModel password) {
        Bundle bundle = new Bundle();
        /*Fixed*/
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);

        /*Variables*/
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));

        /*settings*/
        bundle.putParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name(), user);
        bundle.putParcelable(DialogExtras.SETTING_INPUTBOX2_EXTRA.name(), password);

        return bundle;
    }

    /**
     * Formación de argumentos para cuadro de texto de correo electrónico
     * <p>
     * [EN]  Argument formation for email text box
     *
     * @param title      Título del cuadro [EN]  Title of the picture
     * @param passlenght número mínimo de caracteres del password [EN]  minimum number of password characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createMailPasswordBundle(String title, int passlenght) {
        /*settings*/
        BoxModel user = new BoxModel("Correo electrónico");
        user.setInputType(BoxModel.textEmailAddress);

        BoxModel password = new BoxModel(passlenght);
        password.setHint("Contraseña");
        return createBundle(DialogIcon.LOGIN_ICON, TextTools.nc(title, "Autentificación"), user, password);
    }

    /**
     * Formación de argumentos para cuadro de texto de correo electrónico
     * <p>
     * [EN]  Argument formation for email text box
     *
     * @param title      Título del cuadro [EN]  Title of the picture
     * @param passlenght número mínimo de caracteres del password [EN]  minimum number of password characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createUserPasswordBundle(String title, int passlenght) {
        /*settings*/
        BoxModel user = new BoxModel("Usuario");

        BoxModel password = new BoxModel(passlenght);
        password.setHint("Contraseña");
        return createBundle(DialogIcon.LOGIN_ICON, TextTools.nc(title, "Autentificación"), user, password);
    }

    /**
     * Modificación de contraseña
     * <p>
     * [EN]  Password modification
     *
     * @param title      Título del cuadro [EN]  Title of the picture
     * @param passlenght número mínimo de caracteres del password [EN]  minimum number of password characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createModificationPasswordBundle(String title, int passlenght) {
        /*settings*/
        BoxModel user = new BoxModel(passlenght);
        user.setHint("Nueva Contraseña");
        BoxModel password = new BoxModel(passlenght);
        password.setHint("Confirmar Contraseña");
        return createBundle(DialogIcon.PASSWORD_ICON, TextTools.nc(title, "Modificar Contraseña"), user, password);
    }

    //CREACIÓN____________________________________________________________________
    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_login;
    }

    @Override
    protected void preBuild() {
        if (getArguments() != null) {
            model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(),
                    getContext().getResources().getString(R.string.bt_dialog_login_box_title)));
            user = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name());
            password = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX2_EXTRA.name());
        }

        if (user == null) {
            user = new BoxModel();
        }

        if (password == null) {
            password = new BoxModel();
        }

        //Configurar botones [EN]  Configure buttons
        buttonsSetModel.ok_name.set(context.getResources().getString(R.string.bt_ACTION_OK));
        buttonsSetModel.cancel_name.set(context.getResources().getString(R.string.bt_ACTION_CANCEL));
    }

    //BINDING______________________________________________________________________
    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.user, user);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.password, password);
        viewDataBinding.executePendingBindings();
    }

    //PRESENTERS________________________________________________________________
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

    //PROPERTIES_________________________________________________________________

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
