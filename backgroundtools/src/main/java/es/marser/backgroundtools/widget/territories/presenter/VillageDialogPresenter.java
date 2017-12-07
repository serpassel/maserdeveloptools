package es.marser.backgroundtools.widget.territories.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
import es.marser.backgroundtools.widget.territories.model.VillageModel;
import es.marser.generic.GenericFactory;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 */

@SuppressWarnings("unused")
public class VillageDialogPresenter extends ChooserPresenter<VillageModel> {

    //CONTRUCTORS_____________________________________________________________________
    public VillageDialogPresenter(@NonNull Context context) {
        this(context, false);
    }

    public VillageDialogPresenter(@NonNull Context context, int viewlayout) {
        this(context, viewlayout, false);
    }

    public VillageDialogPresenter(@NonNull Context context, int viewlayout, boolean multiselect_flag) {
        super(context,viewlayout);
        setMultiselect_flag(multiselect_flag);
    }

    public VillageDialogPresenter(@NonNull Context context, boolean multiselect_flag) {
        super(context);
        setMultiselect_flag(multiselect_flag);
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

            SelectionItemsController selectionItemsController = getListmodel().getSelectionItemsController();

            if (bundle.getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                VillageModel item1 = GenericFactory.BuildSingleObject(VillageModel.class,
                        getContext().getResources().getString(R.string.all_spain_mun));
                getListmodel().add(item1);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(item1.preSelectValue()));
                }
            }


            for (String reg : values) {
                VillageModel item = GenericFactory.BuildSingleObject(VillageModel.class, reg);
                getListmodel().add(item);

                if (selectionItemsController != null) {
                    selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(item.preSelectValue()));
                }
            }
        }
    }
}
