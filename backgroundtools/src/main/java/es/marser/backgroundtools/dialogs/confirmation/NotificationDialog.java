package es.marser.backgroundtools.dialogs.confirmation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;

import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.handlers.WindowAction;

import es.marser.backgroundtools.systemtools.SharedPreferenceTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 19/10/17.
 *         Dialogos de notificaciones
 *         <p>
 *         [EN]  Notifications dialogs
 */

@SuppressWarnings("unused")
public class NotificationDialog extends BaseCustomBinDialog implements WindowAction {

    /**
     * Nombre del buzón de preferencias para llaves de acceso a variables de preferencias de dialogos
     * <p>
     * [EN]  Name of the preference box for access keys to dialog preference variables
     *
     * @param context contexto de la aplicación [EN]  application context
     * @return Nombre de la base shared preference asociada [EN]  Name of the base shared preference associated
     */
    public static String sharedBox(Context context) {
        return context.getResources().getString(R.string.bt_dialog_functionality_preferences);
    }

    /*Variable de resultados [EN]  Variable of results*/
    protected OnResult<Void> result;

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Argumentos del dialogo [EN]  Arguments of the dialogue
     * @param result Variable de resultados [EN]  Variable of results
     * @return nueva instancia [EN]  new instance
     */
    public static NotificationDialog newInstace(Context context, Bundle bundle) {
        return newInstace(context, bundle, null);
    }

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Argumentos del dialogo [EN]  Arguments of the dialogue
     * @param result Variable de resultados [EN]  Variable of results
     * @return nueva instancia [EN]  new instance
     */
    public static NotificationDialog newInstace(Context context, Bundle bundle, OnResult<Void> result) {
        NotificationDialog instance = new NotificationDialog();
        instance.setContext(context);
        if (bundle == null) {
            bundle = createBundle(DialogIcon.DEFAULT_ICON,
                    null,
                    null,
                    null,
                    null,
                    context.getResources().getString(R.string.bt_ACTION_CLOSE),
                    null
            );
        }
        instance.setArguments(bundle);
        instance.setResult(result);
        return instance;
    }

    /**
     * Creador de argumentos del cuadro de dialogo
     * <p>
     * [EN]  Dialog Box Argument Creator
     *
     * @param icon Icono para la barra de título [EN]  Icon for the title bar
     * @param title Título de la barra [EN]  Title of the bar
     * @param body Cuerpo del mensaje [EN]  Message body
     * @param option Texto del botón de opciones [EN]  Option Button Text
     * @param ok Texto de botón aceptar [EN]  Accept button text
     * @param cancel Texto de botón cancelar [EN]  Cancel button text
     * @param key_name Nombre de la clave de vinculación (Shared), si existiese [EN]  key_name Name of the link key (Shared), if it exists
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static Bundle createBundle(DialogIcon icon, String title, String body, String option, String ok, String cancel, String key_name) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putString(DialogExtras.BODY_EXTRA.name(), TextTools.nc(body));
        bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
        bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
        bundle.putString(DialogExtras.OPTION_EXTRA.name(), TextTools.nc(option));
        bundle.putString(DialogExtras.KEY_EXTRA.name(), key_name);

        return bundle;
    }

    /**
     * Argumentos para dialogo de información
     * <p>
     * [EN]  Arguments for information dialogue
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createInformationBundle(Context context, String body) {
        return createInformationBundle(context, context.getResources().getString(R.string.bt_dialog_information_title), body);
    }

    /**
     * Argumentos para dialogo de información
     * <p>
     * [EN]  Arguments for information dialogue
     *
     * @param title título del dialogo [EN]  title of the dialogue
     * @param body  cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createInformationBundle(Context context, String title, String body) {
        return createBundle(
                DialogIcon.INFORMATION_ICON,
                title,
                body,
                null,
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE), null);
    }

    /**
     * Argumentos para dialogo de error
     * <p>
     * [EN]  Arguments for error dialogue
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createErrorBundle(Context context, String body) {
        return createBundle(
                DialogIcon.ERROR_ICON,
                context.getResources().getString(R.string.bt_dialog_error_title),
                body,
                null,
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE), null);
    }

    /**
     * Argumentos para dialogo de error
     * <p>
     * [EN]  Arguments for error dialogue
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createOkCancelErrorBundle(Context context, String body) {
        return createBundle(
                DialogIcon.ERROR_ICON,
                context.getResources().getString(R.string.bt_dialog_error_title),
                body,
                null,
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL), null);
    }

    /**
     * Argumentos para dialogo de advertencia
     * <p>
     * [EN]  Arguments for warning dialog
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createWarningBundle(Context context, String body) {
        return createBundle(
                DialogIcon.WARNING_ICON,
                context.getResources().getString(R.string.bt_dialog_warning_title),
                body,
                null,
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE), null);
    }

    /**
     * Argumentos para dialogo de ayuda
     * <p>
     * [EN]  Arguments for help dialog
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createHelpBundle(Context context, String body) {
        return createBundle(
                DialogIcon.HELP_ICON,
                context.getResources().getString(R.string.bt_dialog_help_title),
                body,
                null,
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE), null);
    }

    /**
     * Argumentos para dialogo de confirmación de eliminación de registros
     * <p>
     * [EN]  Arguments for record deletion confirmation dialog
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createDeleteRecordsBundle(Context context) {
        return createBundle(
                DialogIcon.QUESTION_ICON,
                context.getResources().getString(R.string.bt_dialog_confirmation_title),
                context.getResources().getString(R.string.bt_dialog_deleterecords_msg),
                null,
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL), null);
    }

    /**
     * Argumentos para dialogo de confrimación
     * <p>
     * [EN]  Arguments for confrimination dialog
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createConfirmationBundle(Context context, String body) {
        return createBundle(
                DialogIcon.QUESTION_ICON,
                context.getResources().getString(R.string.bt_dialog_confirmation_title),
                body,
                null,
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL), null);
    }

    /**
     * Argumentos para dialogo de confirmación SI NO CANCELAR
     * <p>
     * [EN]  Arguments for confirmation dialog YES NO CANCEL
     *
     * @param body cuerpo del mensaje [EN]  message body
     * @return Objeto de configuración bundle [EN]  Bundle configuration object
     */
    public static Bundle createYesNoCancelConfirmationBundle(Context context, String body) {
        return createBundle(
                DialogIcon.QUESTION_ICON,
                context.getResources().getString(R.string.bt_dialog_confirmation_title),
                body,
                context.getResources().getString(R.string.bt_ACTION_YES),
                context.getResources().getString(R.string.bt_ACTION_NOT),
                context.getResources().getString(R.string.bt_ACTION_CANCEL), null);
    }

    @Override
    protected void postBuild() {
        super.postBuild();
        model.body.set(getArguments().getString(DialogExtras.BODY_EXTRA.name(), ""));
        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));

        buttonsSetModel.option_name.set(getArguments().getString(DialogExtras.OPTION_EXTRA.name()));
        buttonsSetModel.ok_name.set(getArguments().getString(DialogExtras.OK_EXTRA.name()));
        buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));

        String key = getArguments().getString(DialogExtras.KEY_EXTRA.name());

        if (!TextTools.isEmpty(key)) {
            model.keyname.set(key);
            model.key.set(SharedPreferenceTools.getBoolean(getContext(), false, sharedBox(getContext()), key));
        }
    }

    /**
     * Establecer vínculo del dialogo para la llave establecida
     * <p>
     * [EN]  Set dialog link for key set
     *
     * @param key llave de vinculación con shared preferences [EN]  link key with shared preferences
     */
    public void setKeyName(String key) {
        model.keyname.set(key);
    }

    /**
     * Devuelve el valor de la llave de vinculación
     * <p>
     * [EN]  Returns the value of the link key
     *
     * @return Cadena de texto con el nombre de la llave de vincuación asociada
     */
    public String getKeyName() {
        return model.keyname.get();
    }


    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_notification;
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.winaction, this);
        viewDataBinding.executePendingBindings();
    }

    private void savePreferences() {
        SharedPreferenceTools.setBoolean(context,
                model.key.get(),
                context.getResources().getString(R.string.bt_dialog_functionality_preferences),
                model.keyname.get());
    }

    @Override
    public void onOk(View v) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, null);
        }
        savePreferences();
        close();
    }

    @Override
    public void onCancel(View v) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, null);
        }
        savePreferences();
        close();
    }

    @Override
    public void onOption(View v) {
        if (result != null) {
            result.onResult(DialogExtras.OPTION_EXTRA, null);
        }
        savePreferences();
        close();
    }

    /**
     * Recuperar variables de resultado
     * <p>
     * [EN]  Recovering Result Variables
     *
     * @return variable de resultado [EN]  outcome variable
     */
    public OnResult<Void> getResult() {
        return result;
    }

    /**
     * Seteo de variable de resultado
     * <p>
     * [EN]  Result variable setting
     *
     * @param result Variable de resultados [EN]  Variable of results
     */
    public void setResult(OnResult<Void> result) {
        this.result = result;
    }
}
