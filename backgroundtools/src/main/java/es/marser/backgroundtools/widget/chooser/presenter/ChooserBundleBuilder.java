package es.marser.backgroundtools.widget.chooser.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Constructor de argumentos para dialog de selección de listas
 *         <p>
 *         [EN]  Argument constructor for list selection dialog
 *
 */

public class ChooserBundleBuilder {
    //BUNDLE BUILDER________________________________
    public static <T extends Selectable> Bundle createLoadBundle(@Nullable String preselect, @Nullable ArrayList<T> values) {
        Bundle bundle = new Bundle();
          /*LOAD BUNDLE*/
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), TextTools.nc(preselect));
        if (values == null) {
            values = new ArrayList<>();
        }
        bundle.putParcelableArrayList(ListExtra.VALUES_EXTRA.name(), values);
        return bundle;
    }

    /**
     * Creador de argumentos del cuadro de dialogo
     * <p>
     * [EN]  Dialog Box Argument Creator
     *
     * @param icon   Icono para la barra de título [EN]  Icon for the title bar
     * @param title  Título de la barra [EN]  Title of the bar
     * @param path   Directorio de búsqueda [EN]  Search directory
     * @param ok     Texto de botón aceptar [EN]  Accept button text
     * @param cancel Texto de botón cancelar [EN]  Cancel button text
     * @param filter Listado de extensiones válidas [EN]  List of valid extensions
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static <T extends Selectable> Bundle createBundle(DialogIcon icon,
                                                             String title,
                                                             String ok,
                                                             String cancel,
                                                             String preselect,
                                                             ListExtra listExtra,
                                                             ArrayList<T> values) {
        Bundle bundle = new Bundle();

        /*DIALOG MODEL*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, TextTools.nc(title), null, null));

        /*BUTTON SET MODEL*/
        switch (listExtra) {
            case MULTIPLE_SELECTION_MODE:
            case ONLY_MULTIPLE_SELECTION_MODE:
                bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
            default:
                bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
                break;
        }

        /*LIST MODEL*/
        bundle.putAll(BundleBuilder.createListModeBundle(listExtra));

        /*LOAD BUNDLE*/
        bundle.putAll(createLoadBundle(preselect, values));
        return bundle;
    }

    /**
     * @param context contexto de la aplicación [EN]  context of the application
     * @param path    Directorio de búsqueda [EN]  Search directory
     * @param filter  Listado de extensiones válidas [EN]  List of valid extensions
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static <T extends Selectable> Bundle createBundle(
            Context context,
            ListExtra listExtra,
            String premarc,
            ArrayList<T> values) {
        return createBundle(
                DialogIcon.SEARCH_ICON,
                context.getResources().getString(R.string.bt_dialog_select_title),
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                premarc,
                listExtra,
                values
        );
    }

    /**
     * Valores de prueba por defecto
     * <p>
     * [EN]  Default test values
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @return Bundle argumentado [EN]  Bundle argued
     */
    public static <T extends Selectable> Bundle createBundle(
            Context context,
            ArrayList<T> values) {
        return createBundle(context, ListExtra.ONLY_SINGLE_SELECTION_MODE, null, values);
    }
}
