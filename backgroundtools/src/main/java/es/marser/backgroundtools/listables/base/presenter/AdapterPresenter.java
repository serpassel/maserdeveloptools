package es.marser.backgroundtools.listables.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.definition.Restorable;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Definción del presentador de listas
 *         <p>
 *         [EN]  List presenter definition
 */

@SuppressWarnings("EmptyMethod")
public interface AdapterPresenter extends Restorable{

    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    void load(@Nullable Bundle bundle);

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
     */
    void onSaveInstanceState(@Nullable Bundle savedInstanceState);

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
    void onRestoreInstanceState(@Nullable Bundle savedInstanceState);
}
