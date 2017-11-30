package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.widget.chooser.ChooserPresenter;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.widget.territories.model.VillageModel;
import es.marser.generic.GenericFactory;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 */

@SuppressWarnings("unused")
public class VillagePresenter extends ChooserPresenter<VillageModel> {

    //CONTRUCTORS_____________________________________________________________________
    public VillagePresenter(@NonNull Context context) {
        super(context);
    }

    public VillagePresenter(@NonNull Context context, boolean multiselect_flag) {
        super(context, multiselect_flag);
    }

    public VillagePresenter(@NonNull Context context, @NonNull SimpleListModel<VillageModel> listModel) {
        super(context, listModel);
    }

    //LOAD______________________________________________________________________________


    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    @Override
    public void load(@Nullable Bundle bundle) {
        if (bundle != null) {
            int index = bundle.getInt(DialogExtras.INDEX_EXTRAS.name());

            String[] values = ResourcesAccess.getListVillages(getContext(), index);

            String preselect = bundle.getString(DialogExtras.FILTER_EXTRAS.name(), "");

            SelectionItemsController selectionItemsController = simpleListModel.getSelectionItemsController();

            if (bundle.getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                VillageModel item1 = GenericFactory.BuildSingleObject(VillageModel.class,
                        getContext().getResources().getString(R.string.all_spain_mun));
                simpleListModel.add(item1);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(simpleListModel.size() - 1, preselect.contains(item1.preSelectValue()));
                }
            }


            for (String reg : values) {
                VillageModel item = GenericFactory.BuildSingleObject(VillageModel.class, reg);
                simpleListModel.add(item);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(simpleListModel.size() - 1, preselect.contains(item.preSelectValue()));
                }
            }
        }
    }

    //BUNDLE CREATORS__________________________________________________________________
    private static Bundle createBundle(String title,
                                       String ok,
                                       String cancel,
                                       String preselect,
                                       int index,
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
        bundle.putInt(DialogExtras.INDEX_EXTRAS.name(), index);

        return bundle;
    }

    /**
     * Selector de provincias
     * [EN]  Provincial selector
     *
     * @param context   Contexto de la aplicación
     * @param index     índice de la comunidad autónoma o -1 si son todas
     * @param listExtra Tipo de selección
     * @param preselect provincias preseleccionadas
     * @return Argumentos de creación
     */
    public static Bundle createBundle(Context context, int index, boolean multipleselection, String preselect) {
        return createBundle(context, index, multipleselection, preselect, false);
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
    public static Bundle createBundle(Context context, int index, boolean multipleselection, String preselect, boolean placeholder) {
        return createBundle(context.getResources().getString(R.string.village_selector_title),
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                preselect,
                index,
                multipleselection ? ListExtra.ONLY_MULTIPLE_SELECTION_MODE : ListExtra.ONLY_SINGLE_SELECTION_MODE,
                placeholder);
    }
}
