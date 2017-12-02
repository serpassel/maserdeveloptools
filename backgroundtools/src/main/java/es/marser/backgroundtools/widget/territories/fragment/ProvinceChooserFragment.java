package es.marser.backgroundtools.widget.territories.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.containers.fragments.widget.SimpleListFragment;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.listables.simple.model.SimpleListModel;
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

    public static ProvinceChooserFragment newInstance(@Nullable Bundle bundle) {
        ProvinceChooserFragment instance = new ProvinceChooserFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected void preBuild(Context context, @Nullable Bundle args) {
        super.preBuild(context, args);
        setPresenter(new ProvincePresenter(context, false));
        setSimpleListModel(new SimpleListModel<ProvincieModel>(context));
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
