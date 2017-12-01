package es.marser.backgroundtools.definition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author sergio
 *         Created by sergio on 1/12/17.
 *         Definición para objetos que pueden guardar y restituir su estado. Con identificador para distitntas instancias de la misma clase, el mismo archivo de guardado.
 *         <p>
 *         [EN]  Definition for objects that can save and restore their status.  With identifier for different instances of the same class, the same file saved.
 */

@SuppressWarnings("unused")
public interface RestorableInstanciable {

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
     * @param savedInstanceState Objeto de guardado de datos [EN]  Data saving object
     * @param id identificador de la instancia [EN]  instance identifier
     */
    void onSaveInstanceState(@Nullable Bundle savedInstanceState, String id);

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
     * @param id identificador de la instancia [EN]  instance identifier
     */
    void onRestoreInstanceState(@Nullable Bundle savedInstanceState, String id);
}
