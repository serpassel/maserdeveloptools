package es.marser.backgroundtools.objectslistables.complex.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.handlers.ViewComplexHandler;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexExpandController;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexSelectionController;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexViewHolderController;
import es.marser.backgroundtools.objectslistables.complex.holder.ChildViewHolderBinding;
import es.marser.backgroundtools.objectslistables.complex.holder.GroupViewHolderBinding;
import es.marser.backgroundtools.objectslistables.complex.listeners.ExpandCollapseListener;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableList;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableListPosition;
import es.marser.backgroundtools.objectslistables.base.controller.ArrayListController;


/**
 * @author sergio
 *         Created by Sergio on 28/04/2017.
 *         Clase generica para recycler view Adapter Complex
 *         <ul>
 *         <il>Eventos de acción</il>
 *         <il>Sobreescritura de métodos de superclase</il>
 *         <il>Sobre escritura de métodos de interface</il>
 *         <il>Oyentes de modificación de elementos</il>
 *         <il>Acceso a variables</il>
 *         <p>
 *         [EN]  Generic class for recycler view Adapter Complex
 *         <ul>
 *         <il>Action Events</il>
 *         <il>Superclass methods overwriting</il>
 *         <il>About Writing Interface Methods</il>
 *         <il>Element modification listeners</il>
 *         <il>Access to variables</il>
 *         <p>
 *         </ul>
 */

@SuppressWarnings({"unused", "EmptyMethod"})
public abstract class BaseComplexAdapter<
        G extends GroupViewHolderBinding<X, T>,
        C extends ChildViewHolderBinding<X, T>,
        X extends ExpandableGroup<T>,
        T extends Parcelable>
        extends RecyclerView.Adapter<BaseViewHolder>
        implements ExpandCollapseListener, ComplexViewHolderController<X, T> {

    private static final String EXPAND_STATE_MAP = "expandable_recyclerview_adapter_expand_state_map";

    /*Variables de Elementos [EN]  Element Variables*/
    protected List<X> groups;
    protected ExpandableList<X, T> expandableList;

    /*Variables de control [EN]  Control variables*/
    protected ComplexSelectionController<X, T> complexSelectionController;
    protected ComplexExpandController<X, T> expandCollapseController;


    public BaseComplexAdapter() {
        /*Inicio de variables de listas [EN]  Start List Variables*/
        this.groups = new ArrayListController<>();
        this.expandableList = new ExpandableList<>(groups);

        /*Inicio de controladores [EN]  Controllers startup*/
        this.complexSelectionController = new ComplexSelectionController<>(expandableList, ListExtra.SINGLE_SELECTION_MODE);
        this.complexSelectionController.setViewComplexHandler(getViewComplexHandler());

        this.expandCollapseController = new ComplexExpandController<>(expandableList, this);
    }

    //SAVED AND RESTORE_____________________________________________________________
    /**
     * Stores the expanded state map across state loss.
     * <p>
     * Should be called from whatever {@link Activity} that hosts the RecyclerView that {@link
     * ExpandableRecyclerViewAdapter} is attached to.
     * <p>
     * This will make sure to add the expanded state map as an extra to the
     * instance state bundle to be used in {@link #onRestoreInstanceState(Bundle)}.
     *
     * @param savedInstanceState The {@code Bundle} into which to store the
     * expanded state map
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putBooleanArray(EXPAND_STATE_MAP, expandableList.expandedGroupIndexes);
    }

    /**
     * Fetches the expandable state map from the saved instance state {@link Bundle}
     * and restores the expanded states of all of the list items.
     * <p>
     * Should be called from {@link Activity#onRestoreInstanceState(Bundle)}  in
     * the {@link Activity} that hosts the RecyclerView that this
     * {@link ExpandableRecyclerViewAdapter} is attached to.
     * <p>
     *
     * @param savedInstanceState The {@code Bundle} from which the expanded
     * state map is loaded
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(EXPAND_STATE_MAP)) {
            return;
        }
       // expandableList.expandedGroupIndexes = savedInstanceState.getBooleanArray(EXPAND_STATE_MAP);
        notifyDataSetChanged();
    }

    //ACTION EVENTS_______________________________________________________________________________________________
    /*Sobreescritura para introducir de manejador de eventos [EN]  Overwrite to enter event handler*/

    /**
     * Oyente de eventos sobre subvistas
     * <p>
     * [EN]  Listener event listener
     *
     * @return {@link ComplexTouchabeViewHandler}
     */
    public ComplexTouchabeViewHandler<X, T> getComplexTouchabeViewHandler() {
        return null;
    }

    /**
     * Oyente de eventos sobre la vista raíz
     * <p>
     * [EN]  Root view event listener
     *
     * @return {@link ViewComplexHandler}
     */
    public ViewComplexHandler<X, T> getViewComplexHandler() {
        return null;
    }


    //SUPERCLASS METHODS OVERWRITING______________________________________________________________

    /**
     * @return the number of group and child objects currently expanded
     * @see ExpandableList#getVisibleItemCount()
     */
    @Override
    public int getItemCount() {
        return expandableList.getVisibleItemCount();
    }

    /**
     * Gets the view type of the item at the given position.
     *
     * @param flatPos The flat position in the list to get the view type of
     * @return {@value ExpandableListPosition#CHILD} or {@value ExpandableListPosition#GROUP}
     * @throws RuntimeException if the item at the given position in the list is not found
     */
    @Override
    public int getItemViewType(int flatPos) {
        return expandableList.getUnflattenedPosition(flatPos).type;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolderType viewHolderType = ViewHolderType.values()[viewType];

        switch (viewHolderType) {
            case GROUP:
                return onCreateGroupViewHolder(parent, viewType);
            case CHILD:
                return onCreateChildViewHolder(parent, viewType);
            default:
                throw new IllegalArgumentException("viewType is not valid");
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ExpandableListPosition listPos = expandableList.getUnflattenedPosition(position);
        X group = expandableList.getExpandableGroup(listPos);

        ViewHolderType viewHolderType = ViewHolderType.values()[listPos.type];

        switch (viewHolderType) {
            case GROUP:
                onBindGroupViewHolder((G) holder, position, group);

                if (isGroupExpanded(group)) {
                    holder.expand();
                } else {
                    holder.collapse();
                }
                break;

            case CHILD:
                onBindChildViewHolder((C) holder, position, group, listPos.childPos);
                holder.setSelected();
                break;
        }

    }


    public abstract G onCreateGroupViewHolder(ViewGroup parent, int viewType);

    public abstract C onCreateChildViewHolder(ViewGroup parent, int viewType);


    public abstract void onBindGroupViewHolder(G holder, int flatPosition, X group);

    public abstract void onBindChildViewHolder(C holder, int flatPosition, X group, int childIndex);

    //INTERFACE METHODS OVERWRITING_______________________________________________________________________________________

    /**
     * {@link ComplexViewHolderController}
     */
    @Override
    public void onGroupClick(BaseViewHolder<X> holder, int flatPos) {
        complexSelectionController.onClick(holder.getItemView(), flatPos, holder.getItemViewType());
    }

    @Override
    public boolean onGroupLongClick(BaseViewHolder<X> holder, int flatPos) {
        return complexSelectionController.onLongClick(holder.getItemView(), flatPos, holder.getItemViewType());
    }

    @Override
    public X getGroupItemAt(int flatPos) {
        return complexSelectionController.getGroup(flatPos);
    }

    @Override
    public int getGroupIndexAt(int flaPos) {
        return complexSelectionController.getGroupIndex(flaPos);
    }

    @Override
    public void onChildClick(BaseViewHolder<T> holder, int flatPos) {
        complexSelectionController.onClick(holder.getItemView(), flatPos, holder.getItemViewType());
    }

    @Override
    public boolean onChildLongClick(BaseViewHolder<T> holder, int flatPos) {
        return complexSelectionController.onLongClick(holder.getItemView(), flatPos, holder.getItemViewType());
    }

    @Override
    public T getChildItemAt(int flatPos) {
        return complexSelectionController.getChildAt(flatPos);
    }

    @Override
    public int getChildIndexAt(int flaPos) {
        return complexSelectionController.getChildIndex(flaPos);
    }

    @Override
    public boolean isGroupExpanded(int flatPos) {
        return expandCollapseController.isGroupExpanded(flatPos);
    }

    @Override
    public boolean isGroupExpanded(X group) {
        return expandCollapseController.isGroupExpanded(group);
    }

    @Override
    public boolean isChildSelected(int flatPos) {
        return complexSelectionController.isSelected(flatPos);
    }

    //ACCESS TO ELEMENTS_________________________________________________________________________

    /**
     * The full list of {@link ExpandableGroup} backing this RecyclerView
     *
     * @return the list of {@link ExpandableGroup} that this object was instantiated with
     */
    public List<X> getGroups() {
        return expandableList.groups;
    }

    /**
     * Devuelve un grupo partiendo de su índice
     * <p>
     * [EN]  Returns a group from its index
     *
     * @param index índice del grupo [EN]  group index
     * @return Objeto {@link ExpandableGroup} asociado al índice o nulo si no existe la asciación
     * [EN]  Object {@link ExpandableGroup} associated with the index or null if the asciation does not exist
     */
    public ExpandableGroup<T> getGroup(int index) {
        if (index > -1 && index < groups.size()) {
            return groups.get(index);
        }
        return null;
    }

    /**
     * Eliminar todos los grupos
     * <p>
     * [EN]  Remove all groups
     */
    public void removeAllGroups() {
        groups.clear();
        expandableList.clear();
        notifyDataSetChanged();
    }

    /**
     * Agregar un nuevo grupo al final de la lista
     * <p>
     * [EN]  Add a new group to the bottom of the list
     *
     * @param group nuevo grupo [EN]  new group
     */
    public void addGroup(X group) {
        groups.add(group);
        notifyItemInserted(groups.size() - 1);
    }

    public void insertGroup(int index, X group) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((index > -1 && index < groups.size()) || group != null) {
        /*Agregar elemento [EN]  Add Item*/
            groups.add(index, group);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            notifyItemInserted(index);
        }
    }

    /**
     * Actualización de un grupo para una posición establecida
     * <p>
     * [EN]  Updating a group for an established position
     *
     * @param index posición del grupo a actualizar
     *              [EN]  position of the group to update
     * @param group grupo actualizado [EN]  updated group
     */
    public void updateGroup(int index, X group) {
        if (index > -1 && index < groups.size()) {
            groups.set(index, group);
            notifyItemChanged(index);
        }
    }

    /**
     * Eliminación de un grupo según su posición
     * <p>
     * [EN]  Deleting a group according to its position
     *
     * @param index posición del grupo a remover
     *              [EN]  group position to remove
     */
    public void removeGroup(int index) {
        if (index > -1 && index < groups.size()) {
            groups.remove(index);
            complexSelectionController.clearSelecteds();
            notifyItemRemoved(index);
        }
    }

    /**
     * Reemplaza todos los grupos
     * <p>
     * [EN]  Replace all groups
     *
     * @param groups listado de grupos [EN]  group list
     */
    public void replaceAllGroups(List<X> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        complexSelectionController.clearSelecteds();
        notifyDataSetChanged();
    }

    /**
     * Called when a group is expanded
     *
     * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
     * @param itemCount     the total number of children in the {@link ExpandableGroup}
     */
    @Override
    public void onGroupExpanded(int positionStart, int itemCount) {
        //update header
        int headerPosition = positionStart - 1;
        notifyItemChanged(headerPosition);

        // only insert if there items to insert
        if (itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
            if (getViewComplexHandler() != null) {
                int groupIndex = expandableList.getUnflattenedPosition(positionStart).groupPos;
                getViewComplexHandler().onGroupExpanded(getGroups().get(groupIndex));
            }
        }
    }

    /**
     * Called when a group is collapsed
     *
     * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
     * @param itemCount     the total number of children in the {@link ExpandableGroup}
     */
    @Override
    public void onGroupCollapsed(int positionStart, int itemCount) {
        if (complexSelectionController != null) {
            complexSelectionController.clearSelecteds();
        }
        //update header
        int headerPosition = positionStart - 1;
        notifyItemChanged(headerPosition);

        // only remote if there items to remove
        if (itemCount > 0) {
            notifyItemRangeRemoved(positionStart, itemCount);
            if (getViewComplexHandler() != null) {
                //minus one to return the position of the header, not first child
                int groupIndex = expandableList.getUnflattenedPosition(positionStart - 1).groupPos;
                getViewComplexHandler().onGroupCollapsed(getGroups().get(groupIndex));
            }
        }
    }

    /**
     * @param flatPos The flat list position of the group
     * @return true if the group is expanded, *after* the toggle, false if the group is now collapsed
     */
    public boolean toggleGroup(int flatPos) {
        return expandCollapseController.toggleGroup(flatPos);
    }

    /**
     * @param group the {@link ExpandableGroup} being toggled
     * @return true if the group is expanded, *after* the toggle, false if the group is now collapsed
     */
    public boolean toggleGroup(X group) {
        return expandCollapseController.toggleGroup(group);
    }

    //ACCESS TO VARIABLES_______________________________________________________________________

    /**
     * Recuperar la lista de objetos
     * <p>
     * [EN]  Retrieve the object list
     *
     * @return Recupera la lista de objetos expandibles {@link ExpandableList}
     * [EN]  Retrieve the list of expandable objects {@link ExpandableList}
     */
    public ExpandableList<X, T> getExpandableList() {
        return expandableList;
    }

    /**
     * Controlador de selección
     * <p>
     * [EN]  Selection controller
     *
     * @return Controlador de selección {@link ComplexSelectionController}
     * [EN]  Selection controller {@link ComplexSelectionController}
     */
    public ComplexSelectionController<X, T> getComplexSelectionController() {
        return complexSelectionController;
    }

    /**
     * Controlador de expansión
     * <p>
     * [EN]  Expansion controller
     *
     * @return Controlador de expansión {@link ComplexExpandController}
     * [EN]  Expansion controller {@link ComplexExpandController}
     */
    public ComplexExpandController<X, T> getExpandCollapseController() {
        return expandCollapseController;
    }
}
