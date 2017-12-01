package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;

import java.util.ArrayList;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.definition.RestorableInstanciable;
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
 * @see es.marser.backgroundtools.handlers.ViewItemHandler
 * @see es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener
 * @see es.marser.backgroundtools.enums.ListExtra
 */

@SuppressWarnings("unused")
@Deprecated
public class BaseSelectionControllerD<T> implements SelectionItemsController, RestorableInstanciable {

    /*Variables de marcado [EN]  Marking Variables*/
    protected SparseBooleanArray selectedItems;
    /*Variable de modo de selección [EN]  Selection Mode Variable*/
    protected ListExtra selectionmode;
    /*Variables de control de posición de pulsaciones [EN]  Pulse Position Control Variables*/
    protected int lastposition, position;

    /*Variable oyente de modificaciones de slección*/
    protected OnItemChangedListener onSelectionChanged;

    public static String selectedIdkey = "selected_ids";
    public static String lastpositionkey = "last_position";
    public static String positionkey = "position";

    public BaseSelectionControllerD(ListExtra selectionmode) {

        this.selectedItems = new SparseBooleanArray();
        this.selectionmode = selectionmode;

        this.position = -1;
        this.lastposition = -1;

        this.onSelectionChanged = null;
        //    this.itemHandler = null;
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState, String id) {
        if (savedInstanceState != null) {
            savedInstanceState.putIntegerArrayList(TextTools.nc(id) + selectedIdkey, getIdSelecteds());
            savedInstanceState.putInt(lastpositionkey, lastposition);
            savedInstanceState.putInt(positionkey, position);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, String id) {

        if (savedInstanceState != null) {
            ArrayList<Integer> ids = savedInstanceState.getIntegerArrayList(TextTools.nc(id) + selectedIdkey);
            if (selectedItems == null) {
                this.selectedItems = new SparseBooleanArray();
            }
            if (ids != null) {
                for (Integer i : ids) {
                    Log.w(LOG_TAG.TAG, "Restore selector " + i);
                    selectedItems.put(i, true);
                }
            }

            lastposition = savedInstanceState.getInt(lastpositionkey, -1);
            position = savedInstanceState.getInt(positionkey, -1);
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

    /*Variable mode de selección [EN]  Variable selection mode*/
    public ListExtra getSelectionmode() {
        return selectionmode;
    }

    public void setSelectionMode(ListExtra selectionmode) {
        this.selectionmode = selectionmode;
    }

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
