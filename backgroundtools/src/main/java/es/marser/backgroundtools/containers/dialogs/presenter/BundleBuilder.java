package es.marser.backgroundtools.containers.dialogs.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Constructor de argumentos para cuadros de dialogo
 *         <p>
 *         [EN]  Argument constructor for dialog boxes
 */

public class BundleBuilder {

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

    /**
     *
     * @param icon Icono de barra de título [EN]  Title bar icon
     * @param title Texto de la barra de título [EN]  Title bar text
     * @param body Texto del cuerpo del cuadro [EN]  Body text of the box
     * @param key Texto de clave de reiteración, nulo si no tuviera [EN]  Repeat key text, null if it does not have
     * @return Argumentos para [EN]  Arguments for {@link es.marser.backgroundtools.containers.dialogs.model.DialogModel}
     */
    public static Bundle createDialogModelBundle(
            @Nullable DialogIcon icon,
            @Nullable String title,
            @Nullable String body,
            @Nullable String key) {

        Bundle bundle = new Bundle();

        bundle.putSerializable(DialogExtras.ICON_EXTRA.name(), icon);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putString(DialogExtras.BODY_EXTRA.name(), TextTools.nc(body));
        bundle.putString(DialogExtras.KEY_EXTRA.name(), key);

        return bundle;
    }

    /**
     *
     * @param ok_button texto del bóton ok, nulo para ocultar [EN]  text of the ok button, null to hide
     * @param cancel_button texto del bóton cancelar, nulo para ocultar [EN]  cancel, null text to hide
     * @param option_button texto del botón de opción [EN]  option button text
     * @return Argumentos para [EN]  Arguments for {@link es.marser.backgroundtools.containers.dialogs.model.ButtonsSetModel}
     */
    public static Bundle createButtonSetModelBundle(
            @Nullable String ok_button,
            @Nullable String cancel_button,
            @Nullable String option_button) {

        Bundle bundle = new Bundle();

        if (ok_button != null) {
            bundle.putString(DialogExtras.OK_EXTRA.name(), ok_button);
        }
        if (cancel_button != null) {
            bundle.putString(DialogExtras.CANCEL_EXTRA.name(), cancel_button);
        }
        if (option_button != null) {
            bundle.putString(DialogExtras.OPTION_EXTRA.name(), option_button);
        }

        return bundle;
    }

    /**
     * Utilizar sólo para listas simples con un solo {@link es.marser.backgroundtools.listables.simple.holder.ViewHolderBinding}
     * @param extra Modo de selección de la lista
     * @return Argumentos para [EN]  Arguments for {@link es.marser.backgroundtools.listables.base.model.Selectionable}
     */
    public static Bundle createListSelectionModeBundle(@NonNull ListExtra extra){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ListExtra.LIST_EXTRA.name(), extra);
        return bundle;
    }
}
