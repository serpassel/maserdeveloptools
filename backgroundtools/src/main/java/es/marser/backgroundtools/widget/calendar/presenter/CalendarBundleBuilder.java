package es.marser.backgroundtools.widget.calendar.presenter;

import android.content.Context;
import android.os.Bundle;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Constructor de argumentos para Calendar Chooser
 *         <p>
 *         [EN]  Argument Builder for Calendar Chooser
 */

@SuppressWarnings("unused")
public class CalendarBundleBuilder {
    /**
     * Creador de argumentos del cuadro de dialogo
     * <p>
     * [EN]  Dialog Box Argument Creator
     *
     * @param icon   Icono para la barra de título [EN]  Icon for the title bar
     * @param title  Título de la barra [EN]  Title of the bar
     * @param ok     Texto de botón aceptar [EN]  Accept button text
     * @param cancel Texto de botón cancelar [EN]  Cancel button text
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static Bundle createBundle(DialogIcon icon, String title, String ok, String cancel) {
        Bundle bundle = new Bundle();
        /*DIALOG MODEL*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, TextTools.nc(title), null, null));

        /*BUTTON SET MODEL*/
        bundle.putAll(BundleBuilder.createButtonSetModelBundle(
                TextTools.nc(ok),
                TextTools.nc(cancel),
                null));
        return bundle;
    }

    /**
     * Valores de prueba por defecto
     * <p>
     * [EN]  Default test values
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static Bundle createBundle(Context context) {
        return createBundle(
                DialogIcon.CALENDAR_2_ICON,
                context.getResources().getString(R.string.bt_dialog_calendar_title),
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL)
        );
    }

}
