package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.BR;

/**
 * @author sergio
 *         Created by Sergio on 05/04/2017.
 *         Base de fragment para un único modelo con MVP
 *         <p>
 *         [EN]  Fragment basis for a single model with MVP
 *         <ul>
 *         <il>Instantiate variables</il>
 *         <il>Link Variables</il>
 *         <il>Definition of interfaces</il>
 *         </ul>
 */
@SuppressWarnings("unused")
public abstract class BaseFragmentBinModel<T extends Parcelable> extends BaseFragment implements BinHeadFragment<T> {

    protected ViewDataBinding viewDataBinding;
    protected T model;

    public static String bundle_model_key = "head_model_key";

    //ARRANQUE [EN]  START

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
     * Called when all saved state has been restored into the view hierarchy
     * of the fragment.  This can be used to do initialization based on saved
     * state that you are letting the view hierarchy track itself, such as
     * whether check box widgets are currently checked.  This is called
     * after {@link #onActivityCreated(Bundle)} and before
     * {@link #onStart()}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && getModel() == null) {
            model = savedInstanceState.getParcelable(bundle_model_key);
            setModel(model);
        }
    }

    //LINK VARIABLES______________________________________________________________________________________

    /**
     * Enlaza el objeto de cabecera con el presentador,
     * {@link  #OnCreateView} en el método OnCreateView
     * <p>
     * [EN]  Binds the header object with the presenter
     *
     * @param savedInstanceState argumentos de recuperación de datos [EN]  data recovery arguments
     */
    @Override
    public void binObjects(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && getModel() == null) {
            model = savedInstanceState.getParcelable(bundle_model_key);
        }

        if (model == null) {
            model = getNewModelInstance();
        }
        if (getModel() == null) {
            setModel(model);
        }
    }

    /**
     * Asignación del objeto genérico de cabecera
     * <p>
     * [EN]  Assigning the generic header object
     *
     * @param model Objeto modelo [EN]  Model object
     */
    @Override
    public void setModel(T model) {
        if (model != null) {
            this.model = model;
            viewDataBinding.setVariable(BR.model, model);
            viewDataBinding.executePendingBindings();
        }
    }

    /**
     * Devuelve el Objeto genérico de cabecera
     * <p>
     * [EN]  Returns the generic Header Object
     *
     * @return Objeto genérico de cabecera [EN]  Generic Header Object
     */
    @Override
    public T getModel() {
        return model;
    }

}
