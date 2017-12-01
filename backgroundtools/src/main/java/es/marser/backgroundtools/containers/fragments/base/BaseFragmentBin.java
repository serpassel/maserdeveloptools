package es.marser.backgroundtools.containers.fragments.base;

import android.content.Context;
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
public abstract class BaseFragmentBin extends BaseFragment {

    protected ViewDataBinding viewDataBinding;

    /**
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false);
        preBuild(getContext(), getArguments());
        binObjects(getArguments());
        return viewDataBinding.getRoot();
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postBuild(getArguments());
    }

    /**
     * Enlace de objetos en la vista principal. Obligatorio que la variable de modelo en la vista se denomine model
     * Se ejecuta durante al crearse la vista {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * <p>
     * [EN]  Link objects in the main view.  Obligatory that the model variable in the view is called model
     * Runs during view creation {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     *
     * @param args argumentos guardados [EN]  saved arguments
     */
    protected abstract void binObjects(@Nullable Bundle args);

    /**
     * Métodos e inicio de variables previas a la construcción del dialogo. Opcional
     * <p>
     * [EN]  Methods and start of variables prior to the construction of the dialogue.  Optional
     *
     * @param args    argumentos guardados [EN]  saved arguments
     * @param context contexto al que se agjuta el fragment
     */
    protected void preBuild(Context context, @Nullable Bundle args) {
    }

    /**
     * Métodos de inicio de variables posteriores a la construcción del dialogo y a la vinculación de datos {@link #bindObject()}. Opcional
     * Se ejecuta en {@link #onActivityCreated(Bundle)}
     * <p>
     * Methods and start of variables after the construction of the dialogue and the data link {@link #bindObject()}
     *
     * @param args argumentos guardados [EN]  saved arguments
     */
    protected void postBuild(@Nullable Bundle args) {
    }
}
