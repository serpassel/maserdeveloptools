package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by Sergio on 30/04/2017.
 *         Base controladora de selección de elementos de una lista
 *         <ul>
 *         <il>Métodos de sobreescritura</il>
 *         <il>Operaciones de selección</il>
 *         <il>Acceso a variables</il>
 *         <il>Eventos de acción</il>
 *         </ul>
 *         <p>
 *         <p>
 *         <p>
 *         [EN]  Base controller for selecting items from a list
 *         <ul>
 *         <il>Methods of overwriting</il>
 *         <il>Selection operations</il>
 *         <il>Access to variables</il>
 *         <il>Action Events</il>
 *         </ul>
 *         Tigger by {@link ViewItemHandler}
 * @see ViewItemHandler
 * @see OnItemChangedListener
 * @see ListExtra
 */

@SuppressWarnings("unused")
public class SelectionController implements SelectionItemsController {

    /*Variables de marcado [EN]  Marking Variables*/
    protected SparseBooleanArray selectedItems;

    /*Variables de control de posición de pulsaciones [EN]  Pulse Position Control Variables*/
    protected int lastposition, position;

    /*Variable oyente de modificaciones de slección*/
    protected OnItemChangedListener onSelectionChanged;

    private static String[] extras = new String[]{"selected_ids", "last_position", "position"};

    public SelectionController() {

        this.selectedItems = new SparseBooleanArray();

        this.position = -1;
        this.lastposition = -1;

        this.onSelectionChanged = null;
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
            savedInstanceState.putIntegerArrayList(TextTools.nc(id) + extras[0], getIdSelecteds());
            savedInstanceState.putInt(extras[1], lastposition);
            savedInstanceState.putInt(extras[2], position);
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
            ArrayList<Integer> ids = savedInstanceState.getIntegerArrayList(TextTools.nc(id) + extras[0]);
            if (selectedItems == null) {
                this.selectedItems = new SparseBooleanArray();
            }
            if (ids != null) {
                for (Integer i : ids) {
                    selectedItems.put(i, true);
                }
            }

            lastposition = savedInstanceState.getInt(extras[1], -1);
            position = savedInstanceState.getInt(extras[2], -1);
        }
    }

//SELECTION MODEL______________________________________________________________________________________

    /*{@link SelectionItemsController}*/
    @Override
    public boolean get(int position) {
        return selectedItems.get(position);
    }

    @Override
    public void delete(int position) {
        if (position > -1) {
            try {
                selectedItems.delete(position);
                this.position = -1;
                this.lastposition = -1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clear() {
        selectedItems.clear();
    }

    @Override
    public boolean isEmptySelected() {
        for (int i = 0; i <= selectedItems.size(); i++) {
            if (selectedItems.get(i))
                return false;
        }
        return true;
    }

    @Override
    public ArrayList<Integer> getIdSelecteds() {
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i)) {
                selected.add(i);
            }
        }
        return selected;
    }

    @Override
    public void setSelected(int id, boolean value) {
        if (id > -1) {
            selectedItems.put(id, value);
        }
    }

    @Override
    public void inputSelected(int id, boolean value) {
        if (id > -1) {
            selectedItems.put(id, value);
            /*Indicar el último registro selecionado*/
            if (value) {
                this.lastposition = this.position;
                this.position = id;
            }
        }
    }

    @Override
    public int getIdSelected() {
        if (selectedItems.get(position)) {
            return position;
        }
        for (int i = 0; i <= selectedItems.size(); i++) {
            if (selectedItems.get(i))
                return (i);
        }
        return -1;
    }

    @Override
    public void selectedAll(int count) {
        for (int i = 0; i < count; ++i) {
            selectedItems.put(i, true);
        }

       /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
    }

    @Override
    public void deselectedAll() {
        selectedItems.clear();

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
    }

    //ACCESS TO VARIABLES_________________________________________________________________________________________

    /*Variables de control de selección [EN]  Selection control variables*/
    @Override
    public int getLastposition() {
        return lastposition;
    }

    public void setLastposition(int lastposition) {
        this.lastposition = lastposition;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /*Oyentes [EN]  Listeners*/
    public void setOnSelectionChanged(OnItemChangedListener onSelectionChanged) {
        this.onSelectionChanged = onSelectionChanged;
    }

    public void removedOnSelectionChanged() {
        this.onSelectionChanged = null;
    }
}
