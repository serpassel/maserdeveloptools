package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.generic.GenericFactory;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Presentador para provincias [EN]  Presenter for provinces
 */

@SuppressWarnings("unused")
public class ProvincePresenter extends ChooserPresenter<ProvincieModel> {

    public ProvincePresenter(@NonNull Context context, boolean multiselect_flag) {
        super(context, multiselect_flag);
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
        if (bundle != null) {
            //Log.w(LOG_TAG.TAG, "Entrada con argumentos ");
            int index = bundle.getInt(DialogExtras.INDEX_EXTRAS.name());

            String[] values = index < 1 || index > 19
                    ? ResourcesAccess.getListProvinces(getContext())
                    : ResourcesAccess.getListProvinces(getContext(), index);

            String preselect = bundle.getString(DialogExtras.FILTER_EXTRAS.name(), "");

            SelectionItemsController selectionItemsController = simpleListModel.getSelectionItemsController();


            if (bundle.getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                ProvincieModel item1 = GenericFactory.BuildSingleObject(ProvincieModel.class,
                        getContext().getResources().getString(R.string.all_spain_pro));

                simpleListModel.add(item1);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(simpleListModel.size() - 1, preselect.contains(item1.preSelectValue()));
                }
            }


            for (String reg : values) {
                ProvincieModel item = GenericFactory.BuildSingleObject(ProvincieModel.class, reg);
                simpleListModel.add(item);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(simpleListModel.size() - 1, preselect.contains(item.preSelectValue()));
                }
            }
        } else {
            Log.e(LOG_TAG.TAG, "Entrada sin argumentos ");
        }
    }

    //BUNDLES
    public static class BundleBuilder {
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
            LOG_TAG.assertNotNull(context);

            return createBundle(context.getResources().getString(R.string.province_selector_title),
                    context.getResources().getString(R.string.bt_ACTION_OK),
                    context.getResources().getString(R.string.bt_ACTION_CANCEL),
                    preselect,
                    index,
                    multipleselection ? ListExtra.ONLY_MULTIPLE_SELECTION_MODE : ListExtra.ONLY_SINGLE_SELECTION_MODE,
                    placeholder);
        }
    }
}
