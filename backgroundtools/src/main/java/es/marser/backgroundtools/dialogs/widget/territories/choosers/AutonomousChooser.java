package es.marser.backgroundtools.dialogs.widget.territories.choosers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.dialogs.widget.chooser.ChooserDialog;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.dialogs.widget.territories.model.AutonomousModel;
import es.marser.generic.GenericFactory;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 5/11/17.
 *         Selector de provincias
 *         <p>
 *         [EN]  Provincial selector
 */

@SuppressWarnings("unused")
public class AutonomousChooser extends ChooserDialog<AutonomousModel> {

    /**
     * Nueva instancia {@link ChooserDialog}
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param bundle  Argumentos de inicio [EN]  Start arguments
     * @param result  Variable de resultados [EN]  Variable of results
     * @return nueva instancia del dialogo [EN]  new instance of dialogue
     */
    @SuppressWarnings("All")
    public static AutonomousChooser newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @Nullable OnResult<List<AutonomousModel>> result
    ) {

        AutonomousChooser instace = new AutonomousChooser();
        instace.setContext(context);
        instace.setArguments(bundle);
        instace.setResult(result);
        return instace;
    }


    private static Bundle createBundle(String title,
                                       String ok,
                                       String cancel,
                                       String preselect,
                                       ListExtra listExtra,
                                       boolean placeholder
    ) {
        Bundle bundle = new Bundle();

       /*PRE-BUILD*/
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), DialogIcon.LIST_ICON);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putSerializable(ListExtra.LIST_EXTRA.name(), listExtra);
        bundle.putBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), placeholder);

        switch (listExtra) {
            case ONLY_MULTIPLE_SELECTION_MODE:
                bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
                bundle.putInt(DialogExtras.STATE_EXTRA.name(), 1);
                break;
            default:
                bundle.putInt(DialogExtras.STATE_EXTRA.name(), 0);
                break;
        }

        bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));

        /*LOAD*/
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), preselect);

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
        return createBundle(context,multipleselection,preselect,false);
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

    @Override
    protected void load() {
        if (getArguments() != null) {
            int index = getArguments().getInt(DialogExtras.INDEX_EXTRAS.name());

            String[] values = ResourcesAccess.getListAutonomousCommunities(getContext());

            String preselect = getArguments().getString(DialogExtras.FILTER_EXTRAS.name(), "");

            if (getArguments().getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                AutonomousModel item1 = GenericFactory.BuildSingleObject(AutonomousModel.class,
                        getContext().getResources().getString(R.string.all_spain_ccaa));
                addItem(item1);
                inputSelected(getItemCount() - 1, preselect.contains(item1.preSelectValue()));
            }

            for (String reg : values) {
                AutonomousModel item = GenericFactory.BuildSingleObject(AutonomousModel.class, reg);
                addItem(item);
                inputSelected(getItemCount() - 1, preselect.contains(item.preSelectValue()));
            }
        }
    }
}
