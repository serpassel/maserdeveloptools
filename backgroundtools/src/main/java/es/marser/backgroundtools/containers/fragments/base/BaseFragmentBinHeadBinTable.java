package es.marser.backgroundtools.containers.fragments.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
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

public abstract class BaseFragmentBinHeadBinTable<T extends Parcelable,H extends Parcelable, B extends Parcelable>
        extends BaseFragmentBinTable<H, B> {

    protected ViewDataBinding viewDataBinding;
    protected T model;

    public static String bundle_model_table_key =  "head_model_table_key";

    //SAVED AND RESTORE_________________________________________________________________________________

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(bundle_model_table_key, model);
        adapter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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
        adapter.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            model = savedInstanceState.getParcelable(bundle_model_table_key);
        }
    }

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
