package es.marser.backgroundtools.listables.base.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;

/**
 * @author sergio
 *         Created by sergio on 28/11/17.
 *         Modelo para listas
 *         <p>
 *         [EN]  Model for lists
 */

@SuppressWarnings("unused")
public interface AdapterModel<ADP extends RecyclerView.Adapter, RLM extends RecyclerView.LayoutManager>
        extends Scrollable {

    /**
     * @return Adaptador de listas {@link BaseListAdapter}
     */
    ADP getAdapter();

    /**
     * Establecer adaptador
     * <p>
     * [EN]  Set adapter
     *
     * @param adapter Adaptador de listas [EN]  List adapter
     */
    void setAdapter(ADP adapter);

    /**
     * Verdadero si la aplicación ha especificado que los cambios
     * en el contenido del adaptador no pueden cambiar el tamaño de RecyclerView.
     * <p>
     * [EN] true if the app has specified that changes
     * in adapter content cannot change the size of the RecyclerView itself.
     *
     * @return Verdadero si la aplicación ha especificado que los cambios
     * en el contenido del adaptador no pueden cambiar el tamaño de RecyclerView.
     * [EN] true if the app has specified that changes
     * in adapter content cannot change the size of the RecyclerView itself.
     */
    @SuppressWarnings("SameReturnValue")
    boolean isHasFixedSize();

    /**
     * Definición del gestor del layout de la lista. Por defecto se utilizará el lineal
     * <p>
     * [EN]  Definition of the layout manager of the list.  By default the linear
     *
     * @return gestor del layout {@link LinearLayoutManager#VERTICAL}
     * Opcional puede ser {@link GridLayoutManager}
     * [EN]  gestor del layout {@link LinearLayoutManager#VERTICAL}
     * Optional can be {@link GridLayoutManager}
     */
  RLM getLayoutManager();

    /**
     * Establece el gestor del diseño de la lista
     * <p>
     * [EN]  Sets the layout manager of the list
     *
     * @param layoutManager gestor del diseño de la lista [EN]  design manager of the list
     */
    void setLayoutManager(@NonNull RLM layoutManager);

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
