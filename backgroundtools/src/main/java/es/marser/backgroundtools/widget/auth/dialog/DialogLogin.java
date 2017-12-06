package es.marser.backgroundtools.widget.auth.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinDecrep;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnDResult;
import es.marser.backgroundtools.widget.auth.presenter.LoginPresenter;
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
public class DialogLogin extends BaseDialogBin<LoginPresenter> {
    //INSTANCE____________________________________________________________________
    public static DialogLogin newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnDResult<String, String> result) {

        /*PRESENTER*/
        LoginPresenter presenter = new LoginPresenter(context);
        presenter.setResult(result);
        presenter.setArguments(bundle);

        /*DIALOG*/
        DialogLogin instance = new DialogLogin();
        instance.setContext(context);
        instance.setPresenter(presenter);
        return instance;
    }

    //BUNDLE_____________________________________________________________________

    /**
     * Creación de argumentos
     * <p>
     * [EN]  Creating arguments
     *
     * @param context     contexto de la aplicación [EN]  context of the application
     * @param icon        Icono de cabecera [EN]  Header icon
     * @param title       Título de cabecera [EN]  Header title
     * @param boxSettings Configuración de la caja de texto [EN]  Text box settings
     * @return Argumentos de configuración [EN]  Configuration arguments
     */
    public static Bundle createBundle(
            @NonNull Context context,
            @NonNull DialogIcon icon,
            String title,
            @NonNull BoxModel user,
            @NonNull BoxModel password) {
        Bundle bundle = new Bundle();

        /*DIALOG MODEL*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, TextTools.nc(title), null, null));

        /*BUTTON SET MODEL*/
        bundle.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                null
        ));

        /*BOX MODELS*/
        bundle.putParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name(), user);
        bundle.putParcelable(DialogExtras.SETTING_INPUTBOX2_EXTRA.name(), password);

        return bundle;
    }

    /**
     * Formación de argumentos para cuadro de texto de correo electrónico
     * <p>
     * [EN]  Argument formation for email text box
     *
     * @param context    contexto de la aplicación [EN]  context of the application
     * @param title      Título del cuadro [EN]  Title of the picture
     * @param passlenght número mínimo de caracteres del password [EN]  minimum number of password characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createMailPasswordBundle(@NonNull Context context, String title, int passlenght) {
        /*settings*/
        BoxModel user = new BoxModel("Correo electrónico");
        user.setInputType(BoxModel.textEmailAddress);

        BoxModel password = new BoxModel(passlenght);
        password.setHint("Contraseña");

        return createBundle(context, DialogIcon.LOGIN_ICON, TextTools.nc(title, "Autentificación"), user, password);
    }

    /**
     * Formación de argumentos para cuadro de texto de correo electrónico
     * <p>
     * [EN]  Argument formation for email text box
     *
     * @param context    contexto de la aplicación [EN]  context of the application
     * @param title      Título del cuadro [EN]  Title of the picture
     * @param passlenght número mínimo de caracteres del password [EN]  minimum number of password characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createUserPasswordBundle(@NonNull Context context, String title, int passlenght) {
        /*settings*/
        BoxModel user = new BoxModel("Usuario");

        BoxModel password = new BoxModel(passlenght);
        password.setHint("Contraseña");
        return createBundle(context, DialogIcon.LOGIN_ICON, TextTools.nc(title, "Autentificación"), user, password);
    }

    /**
     * Modificación de contraseña
     * <p>
     * [EN]  Password modification
     *
     * @param context    contexto de la aplicación [EN]  context of the application
     * @param title      Título del cuadro [EN]  Title of the picture
     * @param passlenght número mínimo de caracteres del password [EN]  minimum number of password characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createModificationPasswordBundle(@NonNull Context context, String title, int passlenght) {
        /*settings*/
        BoxModel user = new BoxModel(passlenght);
        user.setHint("Nueva Contraseña");
        BoxModel password = new BoxModel(passlenght);
        password.setHint("Confirmar Contraseña");
        return createBundle(context, DialogIcon.PASSWORD_ICON, TextTools.nc(title, "Modificar Contraseña"), user, password);
    }

}
