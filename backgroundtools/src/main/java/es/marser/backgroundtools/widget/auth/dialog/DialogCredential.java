package es.marser.backgroundtools.widget.auth.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinDecrep;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/11/17.
 *         Cuadro de dialogo para autentificación de usuario
 *         <p>
 *         [EN]  Dialog box for user authentication
 */

@SuppressWarnings("unused")
public class DialogCredential extends BaseDialogBinDecrep implements ViewHandler<DialogIcon> {

    protected OnResult<DialogIcon> result;

    //INSTANCE____________________________________________________________________
    public static DialogCredential newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnResult<DialogIcon> result) {

        DialogCredential instance = new DialogCredential();
        instance.setContext(context);
        instance.setArguments(bundle);
        instance.setResult(result);
        return instance;
    }

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
            String title, String cancel) {
        Bundle bundle = new Bundle();
        /*Fixed*/
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        /*Variables*/
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));

        bundle.putString(DialogExtras.CANCEL_EXTRA.name(), cancel);

        return bundle;
    }

    public static Bundle createBundle() {
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
    public static Bundle createBundle(Context context) {
        return createBundle(DialogIcon.LOGIN_ICON, "Reautentificación", context.getResources().getString(R.string.bt_ACTION_CANCEL));
    }

    //CREACIÓN____________________________________________________________________
    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_credential;
    }

    @Override
    protected void preBuild() {
        if (getArguments() != null) {
            model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(),
                    getContext().getResources().getString(R.string.bt_dialog_login_box_title)));
            buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));
        }
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.handler, this);
        viewDataBinding.executePendingBindings();
    }

    //PRESENTERS________________________________________________________________
    @Override
    public void onClick(View view, DialogIcon item) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, item);
            close();
        }
    }

    @Override
    public boolean onLongClick(View view, DialogIcon item) {
        return false;
    }


    @Override
    public void onCancel(View v) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, DialogIcon.DEFAULT_ICON);
            close();
        }

    }

    //PROPERTIES_________________________________________________________________
    public OnResult<DialogIcon> getResult() {
        return result;
    }

    public void setResult(OnResult<DialogIcon> result) {
        this.result = result;
    }
}
