package es.marser.backgroundtools.containers.fragments.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragmentListBin;
import es.marser.backgroundtools.dialogs.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
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
public class BinProvinceChooserFragment extends BaseFragmentListBin<ProvincieModel> {

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
    protected int getHolderLayout() {
        return R.layout.mvp_item_object_chooser;
    }

    @Override
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
        super.bindAdapter(savedInstanceState);
        listModel.getAdapter().setAnimHolders(true);

        if (savedInstanceState == null) {
            load(createBundle(1, "", true));
        } else {
            listModel.getAdapter().onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
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

                    listModel.add(item1);
            }

            for (String reg : values) {
                ProvincieModel item = GenericFactory.BuildSingleObject(ProvincieModel.class, reg);
                //Log.i(LOG_TAG.TAG, "Provincia " + item.toString());
                    listModel.add(item);
            }
        }
    }

    /**
     * Pulsación corta sobre vista del elemento
     * <p>
     * [EN]  Short press on element view
     *
     * @param holder   vista reciclable
     * @param item     Objeto de datos
     * @param position posición de datos
     * @param mode     modo de pulsación en el adapter
     */
    @Override
    public void onClickItem(BaseViewHolder<ProvincieModel> holder, ProvincieModel item, int position, ListExtra mode) {

    }

    /**
     * Pulsación larga sobre vista del elemento
     * <p>
     * [EN]  Long press on element view
     *
     * @param holder   vista reciclable
     * @param item     Objeto de datos
     * @param position posición de datos
     * @param mode     modo de pulsación en el adapter
     * @return devolver true si está activado
     */
    @Override
    public boolean onLongClickItem(BaseViewHolder<ProvincieModel> holder, ProvincieModel item, int position, ListExtra mode) {
        return false;
    }

    /**
     * Manejador de eventos de pulsación sencilla en elementos pulsables
     * <p>
     * [EN]  Single-pulse event handler for push-button elements
     * TAG @string/INCLUDE_ITEM_ACTIONS
     *
     * @param view
     * @param position posición en el adpater [EN]  position in the adpater
     * @param item     objeto de datos [EN]  data object
     * @param root     Vista grupal [EN]  Group view
     */
    @Override
    public void onClick(View view, int position, ProvincieModel item, View root) {

    }

    /**
     * Manejador de eventos de pulsación prolongada en elementos pulsables
     * <p>
     * [EN]  Long-pulsed event handler on pushbutton elements
     * <p>
     * TAG @string/INCLUDE_ITEM_ACTIONS
     *
     * @param view
     * @param position posición en el adpater [EN]  position in the adpater
     * @param item     objeto de datos [EN]  data object
     * @param root     Vista grupal [EN]  Group view
     */
    @Override
    public boolean onLongClick(View view, int position, ProvincieModel item, View root) {
        return false;
    }
}
