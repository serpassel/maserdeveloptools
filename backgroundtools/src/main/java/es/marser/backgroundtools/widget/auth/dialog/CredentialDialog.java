package es.marser.backgroundtools.widget.auth.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.widget.auth.presenter.CredentialPresenter;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/11/17.
 *         Cuadro de dialogo para autentificación de usuario
 *         <p>
 *         [EN]  Dialog box for user authentication
 */

@SuppressWarnings("unused")
public class CredentialDialog extends BaseDialogBin<CredentialPresenter> {

    //INSTANCE____________________________________________________________________
    public static CredentialDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnResult<DialogIcon> result) {

        /*PRESENTER*/
        CredentialPresenter presenter = new CredentialPresenter(context);
        presenter.setResult(result);
        presenter.setArguments(bundle);

        /*DIALOG*/
        CredentialDialog instance = new CredentialDialog();
        instance.setContext(context);
        instance.setPresenter(presenter);

        return instance;
    }

    /**
     * Creación de argumentos
     * <p>
     * [EN]  Creating arguments
     *
     * @param icon        Icono de cabecera [EN]  Header icon
     * @param title       Título de cabecera [EN]  Header title
     * @return Argumentos de configuración [EN]  Configuration arguments
     */
    public static Bundle createBundle(
            @NonNull DialogIcon icon,
            String title,
            String cancel) {
        Bundle bundle = new Bundle();
        /*DIALOG*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, TextTools.nc(title), null, null));

        /*BUTTON SET MODEL*/
        bundle.putAll(BundleBuilder.createButtonSetModelBundle(
                null,
                TextTools.nc(cancel),
                null));
        return bundle;
    }

    public static Bundle createCredentialLoginBundle() {
        return createBundle(DialogIcon.LOGIN_ICON, "Iniciar sesión con","");
    }

    /**
     * Reautentificación
     * <p>
     * [EN]  Reauthentication
     *
     * @param context Contexto de la aplicación [EN]  Context of the application
     * @return Argumentos predefinidos [EN]  Predefined arguments
     */
    public static Bundle createCredentialReAuthBundle(Context context) {
        return createBundle(DialogIcon.LOGIN_ICON, "Reautentificación", context.getResources().getString(R.string.bt_ACTION_CANCEL));
    }
}
