package es.marser.backgroundtools.containers.fragments.widget.province;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragmentListBin;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.objectslistables.simple.presenter.SimpleListPresenter;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Fragmento de selección de provincias
 *         <p>
 *         [EN]  Fragment of selection of provinces
 */

@SuppressWarnings("unused")
public class BinProvinceChooserFragment extends BaseFragmentListBin<ProvincieModel> {

    private ProvincePresenter presenter;
    private SimpleListModel<ProvincieModel> simpleListModel;

    public static BinProvinceChooserFragment newInstance() {
        return new BinProvinceChooserFragment();
    }

    /**
     * Selector de provincias
     * [EN]  Provincial selector
     *
     * @param index       índice de la comunidad autónoma o -1 si son todas
     * @param preselect   provincias preseleccionadas
     * @param placeholder bandera para añadir registro extra de territorio completo
     * @return Argumentos de creación
     */
    public static Bundle createBundle(int index, String preselect, boolean placeholder) {
        Bundle bundle = new Bundle();
           /*LOAD*/
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), preselect);
        bundle.putInt(DialogExtras.INDEX_EXTRAS.name(), index);
        bundle.putBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), placeholder);
        return bundle;
    }

    @Override
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        super.bindAdapter(savedInstanceState);

        simpleListModel.getAdapter().setAnimHolders(true);
        if (savedInstanceState == null) {
            presenter.load(createBundle(1, "", true));
        } else {
            simpleListModel.onRestoreInstanceState(savedInstanceState);
        }
    }


    @Override
    protected void initPresenterModel() {
        simpleListModel = new SimpleListModel<>(getContext(), R.layout.mvp_item_object_chooser);
        presenter = new ProvincePresenter(getContext(), simpleListModel);
    }

    @NonNull
    @Override
    protected SimpleListModel<ProvincieModel> getSimpleListModel() {
        return simpleListModel;
    }

    @NonNull
    @Override
    protected SimpleListPresenter<ProvincieModel> getSimpleListPresenter() {
        return presenter;
    }
}
