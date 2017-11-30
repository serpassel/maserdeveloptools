package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author sergio
 *         Created by Sergio on 05/04/2017.
 *         Base de fragment para un único modelo con MVP
 *         <p>
 *         [EN]  Fragment basis for a single model with MVP
 *         <ul>
 *         <il>Link Variables</il>
 *         </ul>
 */
@SuppressWarnings("unused")
public abstract class BaseFragmentBin extends BaseFragment{

    protected ViewDataBinding viewDataBinding;

    /**
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false);
        binObjects(savedInstanceState);
        return viewDataBinding.getRoot();
    }

    /**
     * Enlace de objetos en la vista principal. Obligatorio que la variable de modelo en la vista se denomine model
     * Se ejecuta durante al crearse la vista {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * <p>
     * [EN]  Link objects in the main view.  Obligatory that the model variable in the view is called model
     * Runs during view creation {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    protected abstract void binObjects(@Nullable Bundle savedInstanceState);
}
