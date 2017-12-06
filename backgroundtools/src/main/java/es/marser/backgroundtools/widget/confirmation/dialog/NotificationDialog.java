package es.marser.backgroundtools.widget.confirmation.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.widget.confirmation.presenter.NotificationPresenter;

/**
 * @author sergio
 *         Created by sergio on 19/10/17.
 *         Dialogos de notificaciones
 *         <p>
 *         [EN]  Notifications dialogs
 */

@SuppressWarnings({"unused", "SameParameterValue"})
public class NotificationDialog extends BaseDialogBin<NotificationPresenter> {

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Argumentos del dialogo [EN]  Arguments of the dialogue
     * @param result Variable de resultados [EN]  Variable of results
     * @return nueva instancia [EN]  new instance
     */
    public static NotificationDialog newInstance(@NonNull Context context, @NonNull Bundle bundle) {
        return newInstance(context, bundle, null);
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
    public static NotificationDialog newInstance(@NonNull Context context, @NonNull Bundle bundle, @Nullable OnResult<Void> result) {
        NotificationDialog instance = new NotificationDialog();
        instance.setContext(context);
        NotificationPresenter presenter = new NotificationPresenter(context, result);
        presenter.setArguments(bundle);

        instance.setPresenter(presenter);
        return instance;
    }

    /**
     * Creador de argumentos del cuadro de dialogo
     * <p>
     * [EN]  Dialog Box Argument Creator
     *
     * @param icon     Icono para la barra de título [EN]  Icon for the title bar
     * @param title    Título de la barra [EN]  Title of the bar
     * @param body     Cuerpo del mensaje [EN]  Message body
     * @param option   Texto del botón de opciones [EN]  Option Button Text
     * @param ok       Texto de botón aceptar [EN]  Accept button text
     * @param cancel   Texto de botón cancelar [EN]  Cancel button text
     * @param key_name Nombre de la clave de vinculación (Shared), si existiese [EN]  key_name Name of the link key (Shared), if it exists
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static Bundle createBundle(DialogIcon icon,
                                      String title,
                                      String body,
                                      String key_name,
                                      String ok,
                                      String cancel,
                                      String option) {
        Bundle bundle = new Bundle();
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, title, body, key_name));
        bundle.putAll(BundleBuilder.createButtonSetModelBundle(ok, cancel, option));
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.INFORMATION_ICON,
                title,
                body,
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.ERROR_ICON,
                context.getResources().getString(R.string.bt_dialog_error_title),
                body,
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.ERROR_ICON,
                context.getResources().getString(R.string.bt_dialog_error_title),
                body,
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.WARNING_ICON,
                context.getResources().getString(R.string.bt_dialog_warning_title),
                body,
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.HELP_ICON,
                context.getResources().getString(R.string.bt_dialog_help_title),
                body,
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                null,
                context.getResources().getString(R.string.bt_ACTION_CLOSE),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.QUESTION_ICON,
                context.getResources().getString(R.string.bt_dialog_confirmation_title),
                context.getResources().getString(R.string.bt_dialog_deleterecords_msg),
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(
                DialogIcon.QUESTION_ICON,
                context.getResources().getString(R.string.bt_dialog_confirmation_title),
                body,
                null
        ));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                null
        ));

        return out;
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
        Bundle out = new Bundle();

        /*Valores de la vista del dialogo [EN]  Dialog view values*/
        out.putAll(BundleBuilder.createDialogModelBundle(DialogIcon.QUESTION_ICON,
                context.getResources().getString(R.string.bt_dialog_confirmation_title),
                body, null));
        /*Variable de botones [EN]  Variable buttons*/
        out.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_YES),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                context.getResources().getString(R.string.bt_ACTION_NOT)
        ));

        return out;
    }
}
