package es.marser.backgroundtools.listables.base.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.definition.Restorable;
import es.marser.backgroundtools.enums.EventsExtras;

/**
 * @author sergio
 *         Created by sergio on 5/12/17.
 *         Definición de presentador con vinculación de variables a vistas
 *         <p>
 *         [EN]  Definition of presenter with linking variables to views
 */

@SuppressWarnings({"unused", "EmptyMethod"})
public interface LinkedPresenter extends Restorable {
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

    /**
     * Indicador del conmienzo de la vinculación de vistas {@link android.databinding.ViewDataBinding}
     * <p>
     * [EN]  Join linking view indicator
     *
     * @param binderContainer Objeto de enlace de vistas [EN]  View link object
     */
    void onBindObjects(@NonNull BinderContainer binderContainer);

    /**
     * Layout de la vista principal
     * <p>
     * [EN]  Layout of the main view
     *
     * @return R.layout.XXXXX
     */
    int getViewLayout();

    /**
     * Eventos de deslizamienos de pantalla
     * <p>
     * [EN]  Screen slide events
     *
     * @param eventsExtras Eventos causante [EN]  Causing events
     */
    @SuppressWarnings("unused")
    void onSwipe(EventsExtras eventsExtras);

    /**
     * Doble pulsación de la pantalla
     * <p>
     * [EN]  Double click on the screen
     */
    @SuppressWarnings("unused")
    void onDoubleTap();
}
