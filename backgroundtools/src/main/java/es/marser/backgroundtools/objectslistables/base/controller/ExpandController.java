package es.marser.backgroundtools.objectslistables.base.controller;

import android.util.SparseBooleanArray;

import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;

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

    //CONSTRUCTORS____________________________________________________________________________________________

    public ExpandController() {
        this.expandItems = new SparseBooleanArray();
        this.onSelectionChanged = null;
    }

    public ExpandController(OnItemChangedListener onSelectionChanged) {
        this();
        this.onSelectionChanged = onSelectionChanged;
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
}
