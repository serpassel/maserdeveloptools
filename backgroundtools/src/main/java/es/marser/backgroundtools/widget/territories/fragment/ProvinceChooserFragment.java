package es.marser.backgroundtools.widget.territories.fragment;

import android.os.Bundle;

import es.marser.backgroundtools.containers.fragments.widget.SimpleListFragment;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.widget.territories.presenter.ProvincePresenter;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Fragmento de selección de provincias
 *         <p>
 *         [EN]  Fragment of selection of provinces
 */

@SuppressWarnings("unused")
public class ProvinceChooserFragment extends SimpleListFragment<ProvincieModel> {

    public static ProvinceChooserFragment newInstance(Bundle bundle) {
        ProvinceChooserFragment instance = new ProvinceChooserFragment();
        instance.setArguments(bundle);
        return new ProvinceChooserFragment();
    }

    public ProvinceChooserFragment() {
        super();
        presenter = new ProvincePresenter(getContext(), false);
        simpleListModel = new SimpleListModel<>(getContext());
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
}
