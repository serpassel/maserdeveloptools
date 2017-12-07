package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
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

    //CONSTRUCTORS________________________________
    public ProvincePresenter(@NonNull Context context, int viewlayout, boolean multiselect_flag) {
        super(context,viewlayout);
        setMultiselect_flag(multiselect_flag);
    }

    //LOAD__________________________________________
    @Override
    public void load(@Nullable Bundle bundle) {
        if (bundle != null) {
            //Log.w(LOG_TAG.TAG, "Entrada con argumentos ");
            int index = bundle.getInt(DialogExtras.INDEX_EXTRAS.name());

            String[] values = index < 1 || index > 19
                    ? ResourcesAccess.getListProvinces(getContext())
                    : ResourcesAccess.getListProvinces(getContext(), index);

            String preselect = bundle.getString(DialogExtras.FILTER_EXTRAS.name(), "");

            SelectionItemsController selectionItemsController = getListmodel().getSelectionItemsController();


            if (bundle.getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                ProvincieModel item1 = GenericFactory.BuildSingleObject(ProvincieModel.class,
                        getContext().getResources().getString(R.string.all_spain_pro));

                getListmodel().add(item1);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(item1.preSelectValue()));
                }
            }


            for (String reg : values) {
                ProvincieModel item = GenericFactory.BuildSingleObject(ProvincieModel.class, reg);
                getListmodel().add(item);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(item.preSelectValue()));
                }
            }
        } else {
            Log.e(LOG_TAG.TAG, "Entrada sin argumentos ");
        }
    }
}
