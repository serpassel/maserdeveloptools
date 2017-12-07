package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.enums.Territories;
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

/*PRE_BUILD*/
    /**
     * Selector de comunidades autónomas
     * [EN]  Autonomous community selector
     *
     * @param context   Contexto de la aplicación
     * @param listExtra Tipo de selección
     * @param preselect provincias preseleccionadas
     * @return Argumentos de creación
     */
    public static Bundle createBundle(@NonNull Context context, boolean multipleselection, @Nullable String preselect, @NonNull Territories type) {
        return createBundle(context, multipleselection, preselect, false, type);
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
    public static Bundle createBundle(@NonNull Context context, boolean multipleselection, @Nullable String preselect, boolean placeholder, @NonNull Territories type) {
        return createBundle(context, -1, multipleselection, preselect, placeholder, type);
    }

    /**
     * Selector de provincias
     * [EN]  Provincial selector
     *
     * @param context     Contexto de la aplicación
     * @param index       índice de la comunidad autónoma o -1 si son todas
     * @param listExtra   Tipo de selección
     * @param preselect   provincias preseleccionadas
     * @param placeholder bandera para añadir registro extra de territorio completo
     * @return Argumentos de creación
     */
    public static Bundle createBundle(@NonNull Context context, int index, boolean multipleselection, @Nullable String preselect, boolean placeholder, @NonNull Territories type) {
        LOG_TAG.assertNotNull(context);
        String title;
        switch (type) {
            case CCAA:
                title = context.getResources().getString(R.string.autonomous_selector_title);
                break;
            case PRO:
                title = context.getResources().getString(R.string.province_selector_title);
                break;
            case MUN:
                title = context.getResources().getString(R.string.village_selector_title);
                break;
            default:
                title = "";
                break;
        }

        return createBundle(title,
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                preselect,
                index,
                multipleselection ? ListExtra.ONLY_MULTIPLE_SELECTION_MODE : ListExtra.ONLY_SINGLE_SELECTION_MODE,
                placeholder);
    }

    /*LOAD*/
    /**
     * Argumentos de carga de datos
     * <p>
     * [EN]  Arguments of data loading
     *
     * @param preselect   Texto de preselección [EN]  Pre-selection text
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
     * @param index       índice de su objeto dependiente, PRO -> CCAA, MUN -> PRO
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

    /*GLOBAL*/
    private static Bundle createBundle(String title,
                                       String ok,
                                       String cancel,
                                       String preselect,
                                       int index,
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
        bundle.putAll(createLoadBundle(preselect, index, placeholder));

        return bundle;
    }

}
