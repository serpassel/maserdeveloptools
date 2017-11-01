package es.marser.backgroundtools.objectslistables.complex.controller;

import android.os.Parcelable;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewComplexHandler;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.complex.listeners.OnItemComplexChangedListener;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableList;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableListPosition;

/**
 * @author sergio
 *         Created by Sergio on 02/05/2017.
 *         Controlador de selección de listas complejas expandibles
 *         <ul>
 *         <il>Operaciones de selección</il>
 *         <il>Recuperación de elementos</il>
 *         <il>Notificación de cambios</il>
 *         <il>Acceso a variables</il>
 *         <il>Eventos de acción</il>
 *         </ul>
 *         <p>
 *         <p>
 *         <p>
 *         [EN]  Expandable Complex List Selection Controller
 *         <ul>
 *         <il>Selection operations</il>
 *         <il>Recovery of elements</il>
 *         <il>Notification of changes</il>
 *         <il>Access to variables</il>
 *         <il>Action Events</il>
 *         </ul>
 *         <p>
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ComplexSelectionController<G extends ExpandableGroup<C>, C extends Parcelable> {

    /*Variables de marcado [EN]  Marking Variables*/
    protected ExpandableList<G, C> expandableList;
    /*Variable de modo de selección [EN]  Selection Mode Variable*/
    protected ListExtra selectionmode;

    /*Variable de captación de eventos de pulsación [EN]  Pulse Pickup Variable*/
    protected ViewComplexHandler<G, C> viewComplexHandler;

    /*Variable oyente de modificaciones de slección*/
    protected OnItemComplexChangedListener<C> onItemComplexChangedListener;

    public ComplexSelectionController(ExpandableList<G, C> expandableList, ListExtra selectionmode) {
        this.expandableList = expandableList;
        this.selectionmode = selectionmode;
    }

    //SELECTION OPERATIONS_______________________________________________________________________
    /*GROUP: SELECTION OPERATIONS*/

    /**
     * Devuelve lista de posiciones de grupos que contienen elementos seleccionados
     * <p>
     * [EN]  Returns list of group positions containing selected items
     *
     * @return lista de posiciones de grupos que contienen elementos seleccionados o -1 si no hay selección
     * [EN]  list of group positions containing selected items or -1 if no selection
     */
    public List<Integer> getGroupsWithSelected() {
        List<Integer> selected = new ArrayList<>();
        /*Recorrer todos los grupos, listar elementos y comprobar si están seleccionados
        * [EN]  Scroll through all groups, list items and check if they are selected*/
        for (int i = 0; i < expandableList.groups.size(); i++) {
            if (expandableList.groups.get(i) != null) {
                if ((expandableList.groups.get(i)).haveSelected()) {
                    selected.add(i);
                }
            }
        }
        return selected;
    }

    /**
     * Devuelve la posición del primer grupo que contiene un elemento seleccionado
     * <p>
     * [EN]  Returns the position of the first group containing a selected element
     *
     * @return la posición del primer grupo que contiene un elemento seleccionado
     * [EN]  the position of the first group containing a selected element
     */
    public int getFirstGroupWithSelected() {
        /*Recorrer todos los grupos, listar elementos y hasta localizar un elemento seleccionado
        * [EN]  Scroll through all groups, list items and even locate a selected item*/
        for (int i = 0; i < expandableList.groups.size(); i++) {
            if (expandableList.groups.get(i) != null) {
                if ((expandableList.groups.get(i)).haveSelected()) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Elimina los grupos que contienen alguna selección
     * <p>
     * [EN]  Removes groups containing any selection
     *
     * @return listado de grupos eliminados [EN]  list of deleted groups
     */
    public List<ExpandableGroup<C>> removedSelected() {

        ArrayList<ExpandableGroup<C>> toUpdate = new ArrayList<>();
        boolean update;

        for (int i = 0; i < expandableList.groups.size(); i++) {
            update = false;
            if (expandableList.groups.get(i) != null) {
                /*Recorrer grupos [EN]  Tour Groups*/
                ExpandableGroup<C> group = expandableList.groups.get(i);
                for (int j = 0; j < group.getItemCount(); j++) {
                    if (group.isChildSelected(j)) {
                        update = true;
                        group.deleteItem(j);
                    }
                }
                if (update) {
                    toUpdate.add(group);
                }
            }
        }

        notifyAllChanged();

        return toUpdate;
    }

    /**
     * Borrar todas las opciones previamente marcadas
     * <p>
     * Clear any choices previously checked
     */
    public void clearSelecteds() {
        for (int i = 0; i < expandableList.groups.size(); i++) {
            ExpandableGroup<C> group = expandableList.groups.get(i);
            group.clearSelections();
        }
        notifyAllChanged();
    }

    /**
     * Comprobación del estado global de selección de elementos
     * <p>
     * [EN]  Checking the global status of item selection
     *
     * @return verdadero si no existe ningún elemento seleccionado
     * [EN]  true if no item is selected
     */
    public boolean isEmptySelected() {
        return getSelectedPositions().isEmpty();
    }


     /*CHILD: SELECTION OPERATIONS*/

    /**
     * Devuelve la lista de posiciones planas de todos los elementos anidados seleccionados
     * <p>
     * [EN]  Returns the list of plan positions of all selected nested elements
     *
     * @return la lista de posiciones planas de todos los elementos anidados seleccionados
     * [EN]  the list of planar positions of all selected nested elements
     */
    public List<Integer> getSelectedPositions() {
        List<Integer> selected = new ArrayList<>();

        for (int i = 0; i < expandableList.groups.size(); i++) {
            if (expandableList.groups.get(i) != null) {
                ExpandableGroup<C> group = expandableList.groups.get(i);
                for (int j = 0; j < group.getItemCount(); j++) {
                    if (group.isChildSelected(j)) {
                        long packedPosition = ExpandableListView.getPackedPositionForChild(i, j);
                        selected.add(expandableList.getFlattenedChildIndex(packedPosition));
                    }
                }
            }
        }
        return selected;
    }

    /**
     * Devuelve la posición plana del primer elemento anidado seleccionado
     * <p>
     * [EN]  Returns the flat position of the first selected nested element
     *
     * @return posición plana del primer elemento anidado seleccionado
     * [EN]  flat position of the selected first nested element
     */
    public int getSelectedPosition() {
        for (int i = 0; i < expandableList.groups.size(); i++) {

            if (expandableList.groups.get(i) != null) {
                ExpandableGroup<C> group = expandableList.groups.get(i);
                for (int j = 0; j < group.getItemCount(); j++) {
                    if (group.isChildSelected(j)) {
                        long packedPosition = ExpandableListView.getPackedPositionForChild(i, j);
                        return expandableList.getFlattenedChildIndex(packedPosition);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Invierte el estado de selección de un elemento anidado
     * <p>
     * [EN]  Inverts the selection state of a nested element
     *
     * @param flatPos posición plana del elemento [EN]  flat position of the element
     * @return Valor el estado de selección del elemento tras la inversión
     * [EN]  Value the item selection status after the investment
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean toggleChildSelection(int flatPos) {
        ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
        ExpandableGroup<C> group = expandableList.groups.get(listPosition.groupPos);
        group.setSelected(listPosition.childPos, !group.isChildSelected(listPosition.childPos));
        return group.isChildSelected(listPosition.childPos);
    }

    /**
     * Define el estado de selección de un elemento anidado
     * <p>
     * [EN]  Defines the selection state of a nested element
     *
     * @param selected   valor del nuevo estado de selección [EN]  selected new selection status value
     * @param groupIndex posición del grupo [EN]  position of the group
     * @param childIndex posición del elemento anidado al grupo [EN]  position of the nested element to the group
     */
    public void selectChild(boolean selected, int groupIndex, int childIndex) {
        ExpandableGroup<C> group = expandableList.groups.get(groupIndex);
        group.setSelected(childIndex, selected);
    }

    /**
     * Comprueba el estado de selección para una posición plana
     * <p>
     * [EN]  Check selection status for flat position
     *
     * @param flatPos Posición plana [EN]  Flat position
     * @return verdadero si está seleccionada [EN]  true if selected
     */
    public boolean isSelected(int flatPos) {
        ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
        ExpandableGroup<C> group = expandableList.groups.get(listPosition.groupPos);
        return group.isChildSelected(listPosition.childPos);
    }

    //RECOVERY OF ELEMENTS_________________________________________________________________
    /*GROUP: RECOVERY OF ELEMENTS*/

    /**
     * Devuelve el grupo para una posición plana
     * <p>
     * [EN]  Returns the group to a flat position
     *
     * @param flatPos posición plana [EN]  flat position
     * @return Grupo [EN]  Group
     */
    public G getGroup(int flatPos) {
        int groupindex = expandableList.getUnflattenedPosition(flatPos).groupPos;
        return expandableList.groups.get(groupindex);
    }

    /**
     * Devuleve la posición en el lista de un grupos para una posición plana
     * <p>
     * [EN]  Returns the position in the list of a group for a flat position
     *
     * @param flatPos posición plana [EN]  flat position
     * @return posición del grupo [EN]  position of the group
     */
    public int getGroupIndex(int flatPos) {
        ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
        return listPosition.groupPos;
    }

    /**
     * Devuelve el elemento anidado de un grupo
     * <p>
     * [EN]  Returns the nested element of a group
     *
     * @param groupIndex posición del grupo [EN]  position of the group
     * @param childIndex posición del elemento anidado [EN]  position of the nested element
     * @return Elemento annidado [EN]  Element nididado
     */
    public C getChildAt(int groupIndex, int childIndex) {
        return expandableList.groups.get(groupIndex).getItems().get(childIndex);
    }

    /**
     * Devuelve el elemento anidado de un grupo
     * <p>
     * [EN]  Returns the nested element of a group
     *
     * @param flatPos posición plana en la lista [EN]  flat position in the list
     * @return Elemento anidado [EN]  Nested element
     */
    public C getChildAt(int flatPos) {
        return getChildAt(getGroupIndex(flatPos), getChildIndex(flatPos));
    }

    /**
     * Posición de un elemento anidado a partir de su posición plana
     * <p>
     * [EN]  Position of a nested element from its flat position
     *
     * @param flatPos posición plana [EN]  flat position
     * @return posición dentro del grupo del elemento [EN]  position within the group of the element
     */
    public int getChildIndex(int flatPos) {
        ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
        return listPosition.childPos;
    }

    //NOTIFICATION OF CHANGES___________________________________________________________________

    /**
     * Notificación sincronizada de cambios
     * <p>
     * [EN]  Synchronized notification of changes
     */
    private synchronized void notifyAllChanged() {
        if (onItemComplexChangedListener != null) {
            onItemComplexChangedListener.notifityAll();
        }

    }

    //ACCESS TO VARIABLES_______________________________________________________________________
    /*Variable de modo de selección [EN]  Selection Mode Variable*/
    public ListExtra getSelectionmode() {
        return selectionmode;
    }

    public void setSelectionmode(ListExtra selectionmode) {
        this.selectionmode = selectionmode;
    }

    /*Variable manejadora de eventos [EN]  Event Handler Variable*/
    public void setViewComplexHandler(ViewComplexHandler<G, C> viewComplexHandler) {
        this.viewComplexHandler = viewComplexHandler;
    }

    public void removeViewComplexHandler() {
        this.viewComplexHandler = null;
    }

    /*Oyentes de cambios en los elementos de la lista [EN]  Listeners for changes in list items*/
    public void removeOnItemComplexChangedListener() {
        this.onItemComplexChangedListener = null;
    }

    public void setOnItemComplexChangedListener(OnItemComplexChangedListener<C> onItemComplexChangedListener) {
        this.onItemComplexChangedListener = onItemComplexChangedListener;
    }

//ACTION EVENTS_______________________________________________________________________________________________
/*Los eventos se activan desde la vista holder del elemento [EN]  Events are activated from the view of the item*/
/*Traslado del elemento de la vista a la manejador de eventos [EN]  Moving the view element to the event handler*/

    /**
     * Pulsación sencilla en un elemento {@link ExpandableGroup}
     * <p>
     * [EN]  Single click on an element
     *
     * @param view    Vista pulsada [EN]  Pulsed view
     * @param flatPos Posición plana de la vista pulsada [EN]  Flat position of the pulsed view
     */
    @SuppressWarnings("UnusedParameters")
    public void onClick(View view, int flatPos, int viewType) {

        ViewHolderType viewHolderType = ViewHolderType.values()[viewType];

        switch (viewHolderType) {
            case CHILD:
                onChildClick(view, flatPos);
                break;
            case GROUP:
                onGroupClick(view, flatPos);
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
    @SuppressWarnings({"UnusedParameters", "SameReturnValue"})
    public boolean onLongClick(View view, int position, int viewType) {
        /*Lanzar la pulsación sobre el elemento [EN]  Release the key on the element*/
        if (viewComplexHandler != null) {
            ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(position);

            ViewHolderType viewHolderType = ViewHolderType.values()[viewType];
            switch (viewHolderType) {
                case CHILD:
                    viewComplexHandler.onLongClickChildItem(
                            listPosition.groupPos,
                            listPosition.childPos,
                            getChildAt(listPosition.groupPos, listPosition.childPos),
                            position,
                            selectionmode
                    );
                    break;

                case GROUP:
                    viewComplexHandler.onLongClickGroupItem(
                            listPosition.groupPos,
                            getGroup(position),
                            position,
                            selectionmode
                    );

                    break;
            }
        }
        switch (selectionmode) {
            case NOT_SELECTION_MODE:
                break;
            case SINGLE_SELECTION_MODE:
                /*Eliminar selecciones [EN]  Delete selections*/
                clearSelecteds();
                /*Inversión del elemento pulsado [EN]  Inversion of the pulsed element*/
                toggleChildSelection(position);
                /*Notificar cambios [EN]  Notify changes*/
                notifyAllChanged();
                /*Cambiamos a selección multiple [EN]  Switch to multiple selection*/
                setSelectionmode(ListExtra.MULTIPLE_SELECTION_MODE);
                break;
            case MULTIPLE_SELECTION_MODE:
                /*Deseleccionar todos los elementos y cambiar al mode de selección simple
                * [EN]  Deselect all items and switch to simple selection mode*/
                clearSelecteds();
                setSelectionmode(ListExtra.SINGLE_SELECTION_MODE);
                break;
        }
        return true;
    }


    private void onChildClick(View v, int flatPos) {
        if (viewComplexHandler != null) {
            ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);

            viewComplexHandler.onClickChildItem(
                    listPosition.groupPos,
                    listPosition.childPos,
                    getChildAt(listPosition.groupPos, listPosition.childPos),
                    flatPos,
                    selectionmode
            );
        }

        switch (selectionmode) {
            case NOT_SELECTION_MODE:
                break;
            case SINGLE_SELECTION_MODE:
                if (isSelected(flatPos)) {
                    clearSelecteds();
                } else {
                    clearSelecteds();
                    toggleChildSelection(flatPos);
                }
                break;
            case MULTIPLE_SELECTION_MODE:
                toggleChildSelection(flatPos);
                //Si no hay ninguno seleccionado pasamos a modo singular
                if (isEmptySelected()) {
                    setSelectionmode(ListExtra.SINGLE_SELECTION_MODE);
                }
                //notifyAllChanged();
                break;
        }
    }

    private void onChildLongClick(View v, int flatPos) {
        /*Lanzar la pulsación sobre el elemento [EN]  Release the key on the element*/
        if (viewComplexHandler != null) {
            ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);

            viewComplexHandler.onLongClickChildItem(
                    listPosition.groupPos,
                    listPosition.childPos,
                    getChildAt(listPosition.groupPos, listPosition.childPos),
                    flatPos,
                    selectionmode
            );
        }
        switch (selectionmode) {
            case NOT_SELECTION_MODE:
                break;
            case SINGLE_SELECTION_MODE:
                /*Eliminar selecciones [EN]  Delete selections*/
                clearSelecteds();
                /*Inversión del elemento pulsado [EN]  Inversion of the pulsed element*/
                toggleChildSelection(flatPos);
                /*Notificar cambios [EN]  Notify changes*/
                notifyAllChanged();
                /*Cambiamos a selección multiple [EN]  Switch to multiple selection*/
                setSelectionmode(ListExtra.MULTIPLE_SELECTION_MODE);
                break;
            case MULTIPLE_SELECTION_MODE:
                /*Deseleccionar todos los elementos y cambiar al mode de selección simple
                * [EN]  Deselect all items and switch to simple selection mode*/
                clearSelecteds();
                setSelectionmode(ListExtra.SINGLE_SELECTION_MODE);
                break;
        }

    }

    private void onGroupClick(View v, int flatPos) {
        if (viewComplexHandler != null) {
            ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
            viewComplexHandler.onClickGroupItem(
                    listPosition.groupPos,
                    getGroup(flatPos),
                    flatPos,
                    selectionmode
            );
        }
    }

    private void onGroupLongClick(View v, int flatPos) {
        if (viewComplexHandler != null) {
            ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
            viewComplexHandler.onLongClickGroupItem(
                    listPosition.groupPos,
                    getGroup(flatPos),
                    flatPos,
                    selectionmode
            );
        }
    }

}
