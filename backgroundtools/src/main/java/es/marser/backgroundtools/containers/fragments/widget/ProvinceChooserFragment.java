package es.marser.backgroundtools.containers.fragments.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragmentBinList;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.generic.GenericFactory;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Fragmento de selección de provincias
 *         <p>
 *         [EN]  Fragment of selection of provinces
 */

@SuppressWarnings("unused")
public class ProvinceChooserFragment extends BaseFragmentBinList<ProvincieModel> {

    public static ProvinceChooserFragment newInstance() {
        return new ProvinceChooserFragment();
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
    protected int getHolderLayout() {
        return R.layout.mvp_item_object_chooser;
    }

    @Override
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        super.bindAdapter(savedInstanceState);
        adapter.setAnimHolders(true);

        if (savedInstanceState == null) {
            load(createBundle(1, "", true));
        } else {
            adapter.onRestoreInstanceState(savedInstanceState);
        }
    }

    protected void load(Bundle bundle) {
        if (bundle != null) {
            int index = bundle.getInt(DialogExtras.INDEX_EXTRAS.name());

            String[] values = index < 1 || index > 19
                    ? ResourcesAccess.getListProvinces(getContext())
                    : ResourcesAccess.getListProvinces(getContext(), index);

            String preselect = bundle.getString(DialogExtras.FILTER_EXTRAS.name(), "");

            if (bundle.getBoolean(DialogExtras.PLACEHOLDER_EXTRA.name(), false)) {

                ProvincieModel item1 = GenericFactory.BuildSingleObject(ProvincieModel.class,
                        getContext().getResources().getString(R.string.all_spain_pro));

                addItem(item1);

                setSelected(getItemCount() - 1, preselect.contains(item1.preSelectValue()));
            }


            for (String reg : values) {
                ProvincieModel item = GenericFactory.BuildSingleObject(ProvincieModel.class, reg);
                //Log.i(LOG_TAG.TAG, "Provincia " + item.toString());
                addItem(item);
                setSelected(getItemCount() - 1, preselect.contains(item.preSelectValue()));
            }
        }
    }
}
