package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
import es.marser.backgroundtools.widget.territories.model.AutonomousModel;
import es.marser.generic.GenericFactory;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Presentador para Comunidades autónomas
 *         <p>
 *         [EN]  Presenter for Autonomous Communities
 */

@SuppressWarnings("unused")
public class AutonomousPresenter extends ChooserPresenter<AutonomousModel> {

    //CONTRUCTORS______________________________________________________________________________
    public AutonomousPresenter(@NonNull Context context) {
        super(context);
    }

    public AutonomousPresenter(@NonNull Context context, int viewlayout) {
        super(context, viewlayout);
    }

    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    @Override
    public void load(@Nullable Bundle bundle) {

        if ( bundle!= null) {

            String[] values = ResourcesAccess.getListAutonomousCommunities(getContext());

            String preselect = bundle.getString(DialogExtras.FILTER_EXTRAS.name(), "");

            SelectionItemsController selectionItemsController = getListmodel().getSelectionItemsController();

            if (bundle.getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                AutonomousModel item1 = GenericFactory.BuildSingleObject(AutonomousModel.class,
                        getContext().getResources().getString(R.string.all_spain_ccaa));

                getListmodel().add(item1);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(item1.preSelectValue()));
                }
            }

            for (String reg : values) {
                AutonomousModel item = GenericFactory.BuildSingleObject(AutonomousModel.class, reg);
                getListmodel().add(item);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(item.preSelectValue()));
                }
            }
        }
    }

//BUNDLE BUILDER_______________________________________________________________________

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
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), preselect);
        bundle.putBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), placeholder);

        return bundle;
    }
}
