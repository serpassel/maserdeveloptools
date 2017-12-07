package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Constructor de argumentos para listas seleccionables con las comunidades autónomas
 *         <p>
 *         [EN]  Insert values ​​in the adapter
 */

@SuppressWarnings("unused")
public class TerritoriesBundleBuilder {

    /**
     * Argumentos de carga de datos
     * <p>
     * [EN]  Arguments of data loading
     *
     * @param preselect Texto de preselección [EN]  Pre-selection text
     * @param placeholder verdadero si hay que introducir el dato para todo_ el territorio
     *                    [EN]  true if you have to enter the data for all_ the territory
     * @return Argumentos de carga de datos [EN]  Pre-selection text
     */
    public static Bundle createLoadBundle(@Nullable String preselect, boolean placeholder) {
       return createLoadBundle(preselect, -1, placeholder);
    }

    /**
     * Argumentos de carga de datos
     * <p>
     * [EN]  Arguments of data loading
     *
     * @param index índice de su objeto dependiente, PRO -> CCAA, MUN -> PRO
     * @param preselect   Texto de preselección [EN]  Pre-selection text
     * @param placeholder verdadero si hay que introducir el dato para todo_ el territorio
     *                    [EN]  true if you have to enter the data for all_ the territory
     * @return Argumentos de carga de datos [EN]  Pre-selection text
     */
    public static Bundle createLoadBundle(@Nullable String preselect, int index, boolean placeholder) {
        Bundle bundle = new Bundle();
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), TextTools.nc(preselect));
        bundle.putBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), placeholder);
        bundle.putInt(DialogExtras.INDEX_EXTRAS.name(), index);
        return bundle;
    }

    /**
     * Selector de comunidades autónomas
     * [EN]  Autonomous community selector
     *
     * @param context   Contexto de la aplicación
     * @param listExtra Tipo de selección
     * @param preselect provincias preseleccionadas
     * @return Argumentos de creación
     */
    public static Bundle createBundle(Context context, boolean multipleselection, String preselect) {
        return createBundle(context, multipleselection, preselect, false);
    }

    /**
     * Selector de comunidades autónomas
     * [EN]  Autonomous community selector
     *
     * @param context     Contexto de la aplicación
     * @param listExtra   Tipo de selección
     * @param preselect   provincias preseleccionadas
     * @param placeholder bandera para añadir registro extra de territorio completo
     * @return Argumentos de creación
     */
    public static Bundle createBundle(Context context, boolean multipleselection, String preselect, boolean placeholder) {
        return createBundle(context.getResources().getString(R.string.autonomous_selector_title),
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                preselect,
                multipleselection ? ListExtra.ONLY_MULTIPLE_SELECTION_MODE : ListExtra.ONLY_SINGLE_SELECTION_MODE,
                placeholder);
    }

    private static Bundle createBundle(
            String title,
            String ok,
            String cancel,
            String preselect,
            ListExtra listExtra,
            boolean placeholder
    ) {
        Bundle bundle = new Bundle();

        /*DIALOG*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(DialogIcon.LOGIN_ICON, TextTools.nc(title), null, null));

        /*BUTTON SET MODEL*/
        switch (listExtra) {
            case MULTIPLE_SELECTION_MODE:
            case ONLY_MULTIPLE_SELECTION_MODE:
                bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
            default:
                bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
                break;
        }

        /*LOAD*/
        bundle.putAll(BundleBuilder.createListModeBundle(listExtra));
        bundle.putAll(createLoadBundle(preselect, placeholder));
        return bundle;
    }
}
