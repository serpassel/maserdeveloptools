package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 30/04/2017.
 *         Controlador de expansión de vistas de elementos
 *         <p>
 *         <ul>
 *         <il>Operaciones de expansión y contracción de vistas</il>
 *         <il>Acceso a variables</il>
 *         </ul>
 *         <p>
 *         [EN]  Elements view expansion controller
 *         <ul>
 *         <il>View Expansion and Contraction Operations</il>
 *         <il>Access to variables</il>
 *         </ul>
 * @see es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener
 * @see es.marser.backgroundtools.recyclerviews.simple.adapter.BaseBindAdapterList
 */

@SuppressWarnings("unused")
public class ExpandController {

    /*Variable de control [EN]  Control variable*/
    protected SparseBooleanArray expandItems;
    /*Variables de selección [EN]  Selection Variables*/
    protected OnItemChangedListener onSelectionChanged;

    public static String expanedIdkey = "expaned_ids";

    //CONSTRUCTORS____________________________________________________________________________________________

    public ExpandController() {
        this.expandItems = new SparseBooleanArray();
        this.onSelectionChanged = null;
    }

    public ExpandController(OnItemChangedListener onSelectionChanged) {
        this();
        this.onSelectionChanged = onSelectionChanged;
    }

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
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState, String id) {
        if (savedInstanceState != null) {
            savedInstanceState.putIntegerArrayList(TextTools.nc(id) + expanedIdkey, getIdExpaned());
        }
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
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, String id) {
        if (savedInstanceState != null) {
            ArrayList<Integer> ids = savedInstanceState.getIntegerArrayList(TextTools.nc(id) + expanedIdkey);
            if (expandItems == null) {
                this.expandItems = new SparseBooleanArray();
            }
            if (ids != null) {
                for (Integer i : ids) {
                    expandItems.put(i, true);
                }
            }

        }
    }

    //VIEW EXPANSION AND CONTRACTION OPERATIONS________________________________________________________________________

    /**
     * Establecer valor de estado de expansión en un posición
     * <p>
     * [EN]  Set Expansion State Value in a Position
     *
     * @param id    posición [EN]  position
     * @param value verdadero expandida [EN]  true expanded
     */
    @SuppressWarnings("All")
    public void setExpand(int id, boolean value) {
        expandItems.put(id, value);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onItemChaged(id);
        }
    }

    /**
     * Comprueba el estado de una posición
     * <p>
     * [EN]  Check the status of a position
     *
     * @param position posición
     * @return verdadero si está expandido [EN]  true if expanded
     */
    public boolean isExpaned(int position) {
        return position > -1 && position < expandItems.size() && get(position);
    }


    /**
     * Colapsa todas las vistas
     * <p>
     * [EN]  Collapse all views
     */
    public void collapseAll() {

        for (int i = 0; i < expandItems.size(); ++i) {
            if (get(i)) {
                /*settear falso si la posición está expandida [EN]  settear false if position is expanded*/
                setExpand(i, false);

                 /*Notificar cambios de selección [EN]  Notify selection changes*/
                if (onSelectionChanged != null) {
                    onSelectionChanged.onItemChaged(i);
                }
            }
        }
    }

    /**
     * Eliminar la varible de expansión de una posición
     * <p>
     * [EN]  Remove the expansion variable from a position
     *
     * @param position posición a eliminar [EN]  position to be deleted
     */
    public void delete(int position) {
        try {
            expandItems.delete(position);
        } catch (Exception ignored) {
        }
    }

    /**
     * Invertir el estad de expansión de una posición
     * <p>
     * [EN]  Invert the expansion state of a position
     *
     * @param id posición a invertir [EN]  position to invest
     * @return valor del nuevo estado de la posición [EN]  value of the new status of the position
     */
    public boolean toggleExpand(int id) {
       /*Invertir estado de expansión [EN]  Invert Expanding State*/
        expandItems.put(id, !isExpaned(id));

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onItemChaged(id);
        }
        return expandItems.get(id);
    }

    /**
     * Limpiar selección. Notificada
     * <p>
     * [EN]  Clear selection.  Notified
     */
    public void deselectedAll() {
        /*Limpiar [EN]  Clean*/
        expandItems.clear();

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
    }

//ACCESS TO VARIABLES_________________________________________________________________________________________

    /*Métodos de acceso de los datos de la variable [EN]  Methods of accessing the variable data */

    /**
     * Estado de expansión de una posición
     * <p>
     * [EN]  Expansion status of a position
     *
     * @param position posición consultada [EN]  consulted position
     * @return verdadero si la vista está expandida [EN]  true if the view is expanded
     */
    public boolean get(int position) {
        return expandItems.get(position);
    }

    /**
     * Limpiar la lista de estados de expansión
     * <p>
     * [EN]  Clear list of expansion states
     */
    public void clear() {
        expandItems.clear();
    }


    /*Variables de oyentes [EN]  Listener Variables*/
    @SuppressWarnings("UnusedReturnValue")
    public ExpandController setOnSelectionChanged(OnItemChangedListener onSelectionChanged) {
        this.onSelectionChanged = onSelectionChanged;
        return this;
    }

    public ExpandController removedOnSelectionChanged() {
        this.onSelectionChanged = null;
        return this;
    }

    /**
     * Devuelve la lista de las posiciones expandidas
     * <p>
     * [EN]  Returns the list of expanded items
     *
     * @return Listas con las posiciones expandidas [EN]  Lists with expanded positions
     */
    public ArrayList<Integer> getIdExpaned() {
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < expandItems.size(); i++) {
            if (expandItems.get(i)) {
                selected.add(i);
            }
        }
        return selected;
    }
}
