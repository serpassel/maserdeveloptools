package es.marser.backgroundtools.objectslistables.simple.controller;

import android.util.SparseBooleanArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.simple.listeners.OnItemChangedListener;


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
 *
 * @see es.marser.backgroundtools.handlers.ViewItemHandler
 * @see es.marser.backgroundtools.objectslistables.simple.listeners.OnItemChangedListener
 * @see es.marser.backgroundtools.enums.ListExtra
 */

@SuppressWarnings("unused")
public abstract class BaseSelectionController<T> {

    /*Variables de marcado [EN]  Marking Variables*/
    protected SparseBooleanArray selectedItems;
    /*Variable de modo de selección [EN]  Selection Mode Variable*/
    protected ListExtra selectionmode;
    /*Variables de control de posición de pulsaciones [EN]  Pulse Position Control Variables*/
    protected int lastposition, position;

    /*Variable de captación de eventos de pulsación [EN]  Pulse Pickup Variable*/
    protected ViewItemHandler<T> itemHandler;
    /*Variable oyente de modificaciones de slección*/
    protected OnItemChangedListener onSelectionChanged;

    public BaseSelectionController(ListExtra selectionmode) {

        this.selectedItems = new SparseBooleanArray();
        this.selectionmode = selectionmode;

        this.position = -1;
        this.lastposition = -1;

        this.onSelectionChanged = null;
        this.itemHandler = null;
    }

    //METHODS OF OVERWRITING__________________________________________________________________________________

    /**
     * @return Número total de elementos [EN]  Total number of items
     */
    protected abstract int getItemsCount();


    /**
     * Elemento para la posición definida
     * <p>
     * [EN]  Item for the defined position
     *
     * @param position posición de búsqueda [EN]  search position
     * @return elemento para la posición indicada [EN]  element for the indicated position
     */
    protected abstract T getItemAt(int position);

    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    protected abstract void itemsClear();

    /**
     * Añadir la lista completa de elementos
     * <p>
     * [EN]  Add full list of items
     *
     * @param list lista de elementos genéricos [EN]  list of generic elements
     */
    protected abstract void itemsAddAll(List<T> list);

//SELECTION OPERATIONS________________________________________________________________________________________

    /**
     * Indica el estado de selección para la posición  indicada
     * <p>
     * [EN]  Indicates the selection status for the indicated position
     *
     * @param position posición a comprobar
     * @return verdadero si la posición se encuentra selecionada [EN]  true if the position is selected
     */
    public boolean get(int position) {
        return selectedItems.get(position);
    }

    /**
     * Elimima la selección para la posición indicada
     * <p>
     * [EN]  Delete the selection for the indicated position
     *
     * @param position posición de selección
     *                 [EN]  selection position
     */
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

    /**
     * Limpia la lista de selección
     * <p>
     * [EN]  Clear selection list
     */
    public void clear() {
        selectedItems.clear();
    }

    /**
     * Comprueba si hay registros de selección seleccionados
     * <p>
     * [EN]  Check if selected selection records
     *
     * @return verdadero si la lista está vacía [EN]  true if the list is empty
     */
    public boolean isEmptySelected() {
        for (int i = 0; i <= getItemsCount(); i++) {
            if (selectedItems.get(i))
                return false;
        }
        return true;
    }

    /**
     * Devuelve los registros seleccionados
     * <p>
     * [EN]  Returns the selected records
     *
     * @return lista con los registros seleccionados [EN]  list with selected records
     */
    public List<T> getSelectds() {
        List<T> selected = new ArrayList<>();
        for (int i = 0; i < getItemsCount(); i++) {
            if (selectedItems.get(i)) {
                selected.add(getItemAt(i));
            }
        }
        return selected;
    }

    /**
     * Devuelve la lista de las posiciones seleccionadas
     * <p>
     * [EN]  Returns the list of selected positions
     *
     * @return Listas con las posiciones seleccionadas [EN]  Lists with selected positions
     */
    public List<Integer> getIdSelecteds() {
        List<Integer> selected = new ArrayList<>();
        for (int i = 0; i < getItemsCount(); i++) {
            if (selectedItems.get(i)) {
                selected.add(i);
            }
        }
        return selected;
    }

    /**
     * Marca el estado de selección de una posición
     * <p>
     * [EN]  Mark the selection state of a position
     *
     * @param id    posición a modificar [EN]  position to modify
     * @param value valor de estado para la posición [EN]  status value for position
     */
    public void setSelected(int id, boolean value) {
        selectedItems.put(id, value);
    }

    /**
     * Predefinción del estado de selección para la posición indicada
     * <p>
     * [EN]  Default selection state for the indicated position
     *
     * @param id    posición a predefinir [EN]  position to preset
     * @param value valor de preselección para la posición indicada [EN]  preset value for the indicated position
     */
    public void inputSelected(int id, boolean value) {
        selectedItems.put(id, value);
        this.lastposition = this.position;
        this.position = id;
    }

    /**
     * Devuelve el último registro pulsado si está seleccionado o el primer registro seleccionado
     * <p>
     * [EN]  Returns the last recorded record if selected or the first selected record
     *
     * @return Objeto seleccionado [EN]  Selected Object
     */
    public T getItemSelected() {
        if (selectedItems.get(position)) {
            return getItemAt(position);
        }
        for (int i = 0; i <= getItemsCount(); i++) {
            if (selectedItems.get(i))
                return getItemAt(i);
        }
        return null;
    }

    /**
     * Devuelve la posición del último elemento pulsado si está seleccionado o el primer registro seleccionado
     * <p>
     * [EN]  Returns the position of the last element pressed if selected or the first selected record
     *
     * @return posición de la última posición pulsada o seleccionada -1 en caso de no existir
     * [EN]  position of the last position pressed or selected -1 if there is no
     */
    public int getIdSelected() {
        if (selectedItems.get(position)) {
            return position;
        }
        for (int i = 0; i <= getItemsCount(); i++) {
            if (selectedItems.get(i))
                return (i);
        }
        return -1;
    }

    /**
     * Seleccionar todos los elementos
     * <p>
     * [EN]  Select all items
     */
    public void selectedAll() {
        for (int i = 0; i < getItemsCount(); ++i) {
            selectedItems.put(i, true);
        }

       /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
    }

    /**
     * Deseleccionar todos los registros
     * <p>
     * [EN]  Deselect all records
     */
    public void deselectedAll() {
        selectedItems.clear();

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
    }

    /**
     * Elimina los elementos seleccionados
     * <p>
     * [EN]  Delete selected items
     *
     * @return lista de elementos seleccionados [EN]  list of selected items
     */
    public List<T> removeSelectedItems() {
        List<T> toRemove = new ArrayList<>();
        List<T> toSave = new ArrayList<>();
        for (int i = 0; i < getItemsCount(); i++) {

            if (selectedItems.get(i)) {
                toRemove.add(getItemAt(i));
                selectedItems.delete(i);
            } else {
                toSave.add(getItemAt(i));
            }
        }

        itemsClear();//Limpiar lista de elementos [EN]  Clean item list
        itemsAddAll(toSave);//Agregar los elementos no borrados [EN]  Add items not deleted

        position = -1;
        lastposition = -1;

        /*Notificar cambios de selección [EN]  Notify selection changes*/
        if (onSelectionChanged != null) {
            onSelectionChanged.onSelectionChanged();
        }
        return toRemove;
    }

    //ACCESS TO VARIABLES_________________________________________________________________________________________

    /*Variable mode de selección [EN]  Variable selection mode*/
    public ListExtra getSelectionmode() {
        return selectionmode;
    }

    public void setSelectionMode(ListExtra selectionmode) {
        this.selectionmode = selectionmode;
    }


    /*Oyentes [EN]  Listeners*/
    public void setOnSelectionChanged(OnItemChangedListener onSelectionChanged) {
        this.onSelectionChanged = onSelectionChanged;
    }

    public void removedOnSelectionChanged() {
        this.onSelectionChanged = null;
    }

    public void setItemHandler(ViewItemHandler<T> itemHandler) {
        this.itemHandler = itemHandler;
    }

    public void removeItemHandler() {
        this.itemHandler = null;
    }

    //ACTION EVENTS_______________________________________________________________________________________________
/*Los eventos se activan desde la vista holder del elemento [EN]  Events are activated from the view of the item*/
/*Traslado del elemento de la vista a la manejador de eventos [EN]  Moving the view element to the event handler*/

    /**
     * Pulsación sencilla en un elemento
     * <p>
     * [EN]  Single click on an element
     *
     * @param view     Vista pulsada [EN]  Pulsed view
     * @param position Posición de la vista pulsada [EN]  Position of the pulsed view
     */
    public void onClick(View view, int position) {
        /*Actualizar las variables de posición [EN]  Update position variables*/
        this.lastposition = this.position;
        this.position = position;

        /*Lanzar la pulsación sobre el elemento [EN]  Release the key on the element*/
        if (itemHandler != null) {
            itemHandler.onClickItem(view, getItemAt(position), position, selectionmode);
        }

        switch (selectionmode) {
            case NOT_SELECTION_MODE:
                return;

            case ONLY_SINGLE_SELECTION_MODE:
            case SINGLE_SELECTION_MODE:
                if (lastposition != position) {
                    //Comprobamos que no estamos en el mismo registro
                    selectedItems.put(lastposition, false);

                    /*Notificar cambios de selección [EN]  Notify selection changes*/
                    if (onSelectionChanged != null) {
                        onSelectionChanged.onItemChaged(lastposition);
                        onSelectionChanged.onItemChaged(position);
                    }
                }

                selectedItems.put(position, !view.isSelected());

                /*Notificar cambios de selección [EN]  Notify selection changes*/
                if (onSelectionChanged != null) {
                    onSelectionChanged.onItemChaged(position);
                }

                break;
            case ONLY_MULTIPLE_SELECTION_MODE:
            case MULTIPLE_SELECTION_MODE:

                selectedItems.put(position, !view.isSelected());

                /*Notificar cambios de selección [EN]  Notify selection changes*/
                if (onSelectionChanged != null) {
                    onSelectionChanged.onItemChaged(position);
                }

                //Si es el ultimo registro pasar a selección sencilla
                //[EN]  If the last record goes to simple selection
                if (isEmptySelected() && selectionmode == ListExtra.MULTIPLE_SELECTION_MODE) {
                    //Si no hay elementos seleccionados pasar a modo singular
                    //[EN]   If there are no selected elements go to singular mode
                    selectionmode = ListExtra.SINGLE_SELECTION_MODE;
                }
                break;
        }
    }

    /**
     * Pulsación prolongada en un elemento
     * <p>
     * [EN]  Long press on an element
     *
     * @param view     Vista pulsada [EN]  Pulsed view
     * @param position Posición de la vista pulsada [EN]  Position of the pulsed view
     */
    @SuppressWarnings("SameReturnValue")
    public boolean onLongClick(View view, int position) {
        //Actualizamos el indice de posicion
        this.lastposition = this.position;
        this.position = position;

                /*Lanzar la pulsación sobre el elemento [EN]  Release the key on the element*/
        if (itemHandler != null) {
            itemHandler.onLongClickItem(view, getItemAt(position), position, selectionmode);
        }

        switch (selectionmode) {
            case NOT_SELECTION_MODE:
                return true;
            case ONLY_SINGLE_SELECTION_MODE:
            case SINGLE_SELECTION_MODE:
                /*Deseleccionar la posición anterior [EN]  Deselect previous position*/
                if (lastposition != position) {

                    /*Comprobar si se ha cambiado el registro [EN]  To compute if the record has been changed*/
                    selectedItems.put(lastposition, false);

                    /*Notificar cambios de selección [EN]  Notify selection changes*/
                    if (onSelectionChanged != null) {
                        onSelectionChanged.onItemChaged(lastposition);
                    }
                }

                selectedItems.put(position, true);

                /*Notificar cambios de selección [EN]  Notify selection changes*/
                if (onSelectionChanged != null) {
                    onSelectionChanged.onItemChaged(position);
                }

                if (selectionmode == ListExtra.SINGLE_SELECTION_MODE) {
                    /*Activar selección multiple [EN]  Enable multiple selection*/
                    selectionmode = ListExtra.MULTIPLE_SELECTION_MODE;
                }
                break;

            case MULTIPLE_SELECTION_MODE:
                /*Limpiar selección [EN]  Clear Selection*/
                deselectedAll();
                /*Activar selección simple [EN]  Enable simple selection*/
                selectionmode = ListExtra.SINGLE_SELECTION_MODE;
                break;
        }
        return true;
    }
}
