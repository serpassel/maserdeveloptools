package es.marser.backgroundtools.widget.territories.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.containers.fragments.simple.SimpleListFragment;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.widget.territories.presenter.ProvinceFragmentPresenter;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Fragmento de selección de provincias
 *         <p>
 *         [EN]  Fragment of selection of provinces
 */

@SuppressWarnings("unused")
public class ProvinceChooserFragment extends SimpleListFragment<ProvincieModel> {

    public static ProvinceChooserFragment newInstance(@NonNull Context context, @Nullable Bundle bundle) {

        /*FRAGMENT*/
        ProvinceChooserFragment instance = new ProvinceChooserFragment();
        instance.setArguments(bundle);
        return instance;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context al que se adjunta el fragmento
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         /*PRESENTER*/
        ProvinceFragmentPresenter presenter = new ProvinceFragmentPresenter(context);
        setPresenter(presenter);
    }
}
