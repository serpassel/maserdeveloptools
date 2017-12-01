package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;

import java.util.ArrayList;

import es.marser.backgroundtools.definition.RestorableInstanciable;
import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsController;
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
public class ExpandController implements ExpandItemsController, RestorableInstanciable {

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

    //SAVED AND RESTORED_______________________________________________________________________________________

    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState, String id) {
        if (savedInstanceState != null) {
            savedInstanceState.putIntegerArrayList(TextTools.nc(id) + expanedIdkey, getIdExpaned());
        }
    }

    @Override
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

    @Override
    public void setExpand(int id, boolean value) {
        expandItems.put(id, value);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onItemChaged(id);
        }
    }

    @Override
    public boolean isExpaned(int position) {
        return position > -1 && position < expandItems.size() && get(position);
    }

    @Override
    public void collapseAll() {

        for (int i = 0; i < expandItems.size(); ++i) {
            if (expandItems.valueAt(i)) {
                /*settear falso si la posición está expandida [EN]  settear false if position is expanded*/
                setExpand(expandItems.keyAt(i), false);

                 /*Notificar cambios de selección [EN]  Notify selection changes*/
                if (onSelectionChanged != null) {
                    onSelectionChanged.onItemChaged(i);
                }
            }
        }
    }

    @Override
    public void delete(int position) {
        try {
            expandItems.delete(position);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean toggleExpand(int id) {
       /*Invertir estado de expansión [EN]  Invert Expanding State*/
        expandItems.put(id, !isExpaned(id));

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onItemChaged(id);
        }
        return expandItems.get(id);
    }

    @Override
    public void deselectedAll() {
        /*Limpiar [EN]  Clean*/
        expandItems.clear();

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
    }

    @Override
    public boolean get(int position) {
        return expandItems.get(position);
    }

    @Override
    public void clear() {
        expandItems.clear();
    }

    @Override
    @NonNull
    public ArrayList<Integer> getIdExpaned() {
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < expandItems.size(); i++) {
            if (expandItems.valueAt(i)) {
                selected.add(expandItems.keyAt(i));
            }
        }
        return selected;
    }

//ACCESS TO VARIABLES_________________________________________________________________________________________

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

}
