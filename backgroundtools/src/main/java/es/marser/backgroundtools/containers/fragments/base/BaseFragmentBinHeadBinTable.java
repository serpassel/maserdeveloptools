package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.BR;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Base para construcción de fragmentos tipo tabla con objeto de cabecera
 *         <p>
 *         [EN]  Base for construction of fragments type table with header object
 */

public abstract class BaseFragmentBinHeadBinTable<T,H, B>
        extends BaseFragmentBinTable<H, B> {

    protected ViewDataBinding viewDataBinding;
    protected T model;

    /**
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false);
        binObjects();
        return viewDataBinding.getRoot();
    }

    //INSTANTIATE VARIABLES______________________________________________________________________________

    /**
     * Instanciar variables en el método {@link BaseFragment#OnCreate()}
     * <p>
     * [EN]  Instanciar variables
     */
    @Override
    protected void instanceVariables() {
        model = getNewModelInstance();
    }

    /**
     * Crear una instancia para el objeto modelo {@link #instanceVariables()}
     * <p>
     * [EN]  Create an instance for the model object
     *
     * @return Nuevo objeto genérico [EN]  New generic object
     */
    protected @NonNull
    abstract T getNewModelInstance();


    //LINK VARIABLES______________________________________________________________________________________

    /**
     * Enlaza el objeto de cabecera con el presentador,
     * {@link  #OnCreateView} en el método OnCreateView
     * <p>
     * [EN]  Binds the header object with the presenter
     */
    protected void binObjects() {
        setModel(model);
    }

    /**
     * Asignación del objeto genérico de cabecera
     * {@link #binObjectsl()}
     * <p>
     * [EN]  Assigning the generic header object
     *
     * @param model Objeto modelo [EN]  Model object
     */
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
    public T getModel() {
        return model;
    }
}
