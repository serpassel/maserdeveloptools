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
import es.marser.backgroundtools.widget.territories.model.AutonomousModel;
import es.marser.generic.GenericFactory;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Presentador para Comunidades autónomas
 *         <p>
 *         [EN]  Presenter for Autonomous Communities
 */

@SuppressWarnings("unused")
public class AutonomousDialogPresenter extends ChooserPresenter<AutonomousModel> {

    //CONTRUCTORS______________________________________________________________________________
    public AutonomousDialogPresenter(@NonNull Context context) {
        super(context);
    }

    public AutonomousDialogPresenter(@NonNull Context context, int viewlayout) {
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
        bundle = replaceNullBundleWithArguments(bundle);

        if (bundle != null) {

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
}
