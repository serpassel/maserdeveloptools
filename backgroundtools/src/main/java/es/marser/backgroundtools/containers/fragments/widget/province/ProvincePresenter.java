package es.marser.backgroundtools.containers.fragments.widget.province;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.objectslistables.simple.presenter.SimpleListPresenter;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.generic.GenericFactory;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador de provincias
 */

public class ProvincePresenter extends ChooserPresenter<ProvincieModel> {


    public ProvincePresenter(@NonNull Context context, @NonNull SimpleListModel<ProvincieModel> listModel) {
        super(context, listModel);
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
                    selectionItemsController.setSelected(simpleListModel.size() - 1, preselect.contains(item1.preSelectValue()));
                }
            }

            for (String reg : values) {
                ProvincieModel item = GenericFactory.BuildSingleObject(ProvincieModel.class, reg);
                //Log.i(LOG_TAG.TAG, "Provincia " + item.toString());
                getListModel().add(item);

                if (selectionItemsController != null) {
                    selectionItemsController.setSelected(simpleListModel.size() - 1, preselect.contains(item.preSelectValue()));
                }
            }
        }
    }
}
