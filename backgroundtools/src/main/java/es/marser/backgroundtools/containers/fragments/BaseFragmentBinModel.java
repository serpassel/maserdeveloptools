package es.marser.backgroundtools.containers.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.containers.fragments.base.BaseFragment;
import es.marser.backgroundtools.handlers.ViewHandler;

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
public abstract class BaseFragmentBinModel<T> extends BaseFragment implements ViewHandler<T> {

    protected ViewDataBinding viewDataBinding;
    private T model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instaceVariables();
    }

    /**
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false);
        binObjectsl();
        return viewDataBinding.getRoot();
    }

    //INSTANTIATE VARIABLES______________________________________________________________________________

    /**
     * Instanciar variables
     * <p>
     * [EN]  Instanciar variables
     */
    protected void instaceVariables() {
        model = getNewModelInstance();
    }

    /**
     * Crear una instancia para el objeto modelo
     * <p>
     * [EN]  Create an instance for the model object
     *
     * @return Nuevo objeto genérico [EN]  New generic object
     */
    protected @NonNull
    abstract T getNewModelInstance();


    //LINK VARIABLES______________________________________________________________________________________

    /**
     * Enlaza el objeto de cabecera con el presentador
     * <p>
     * [EN]  Binds the header object with the presenter
     */
    protected void binObjectsl() {
        setModel(model);
        setHandler(this);
    }

    /**
     * Enlace de manejador de eventos de vista de cabecera
     * <p>
     * [EN]  Header View Event Handler Link
     *
     * @param handler manejador de eventos {@link ViewHandler} [EN]  event handler {@link ViewHandler}
     */
    public void setHandler(ViewHandler<T> handler) {
        viewDataBinding.setVariable(BR.handler, handler);
        viewDataBinding.executePendingBindings();
    }

    /**
     * Asignación del objeto genérico de cabecera
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

    //DEFINITION OF INTERFACES_____________________________________________________________________________
    /*{@link ViewHandler}*/
    @Override
    public void onClick(View v, T item) {

    }

    @Override
    public boolean onLongClick(View v, T item) {
        return true;
    }

}
