package es.marser.backgroundtools.objectslistables.complex.adapter;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.handlers.ViewComplexHandler;
import es.marser.backgroundtools.objectslistables.base.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexExpandController;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexSelectionController;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexViewHolderController;
import es.marser.backgroundtools.objectslistables.complex.holder.ChildViewHolderBinding;
import es.marser.backgroundtools.objectslistables.complex.holder.GroupViewHolderBinding;
import es.marser.backgroundtools.objectslistables.complex.listeners.ExpandCollapseListener;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableList;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableListPosition;


/**
 * Created by Sergio on 28/04/2017.
 */

@SuppressWarnings("ALL")
public abstract class BaseComplexAdapter<
        G extends GroupViewHolderBinding<X, T>,
        C extends ChildViewHolderBinding<X, T>,
        X extends ExpandableGroup<T>,
        T extends Parcelable>
        extends RecyclerView.Adapter<BaseViewHolder>
        implements ExpandCollapseListener, ComplexViewHolderController<X, T> {


    /*Variables de Elementos [EN]  Element Variables*/
    protected List<X> groups;
    protected ExpandableList<X, T> expandableList;

    /*Variables de control [EN]  Control variables*/
    protected ComplexSelectionController<X, T> complexSelectionController;
    protected ComplexExpandController<X, T> expandCollapseController;


    public BaseComplexAdapter() {
        /*Inicio de variables de listas [EN]  Start List Variables*/
        this.groups = new ArrayList<>();
        this.expandableList = new ExpandableList<>(groups);

        /*Inicio de controladores [EN]  Controllers startup*/
        this.complexSelectionController = new ComplexSelectionController<>(expandableList, ListExtra.SINGLE_SELECTION_MODE);
        this.complexSelectionController.setViewComplexHandler(getViewComplexHandler());


        this.expandCollapseController = new ComplexExpandController<>(expandableList, this);
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

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ExpandableListPosition.GROUP:
                return onCreateGroupViewHolder(parent, viewType);
            case ExpandableListPosition.CHILD:
                return onCreateChildViewHolder(parent, viewType);
            default:
                throw new IllegalArgumentException("viewType is not valid");
        }
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ExpandableListPosition listPos = expandableList.getUnflattenedPosition(position);
        X group = expandableList.getExpandableGroup(listPos);
        switch (listPos.type) {
            case ExpandableListPosition.GROUP:
                onBindGroupViewHolder((G) holder, position, group);

                if (isGroupExpanded(group)) {
                    holder.expand();
                } else {
                    holder.collapse();
                }
                break;

            case ExpandableListPosition.CHILD:
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
     * @param index
     * @return
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
    public void addItem(X group) {
        groups.add(group);
        notifyDataSetChanged();
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
    public void updateItem(int index, X group) {
        if (index > -1 && index < groups.size()) {
            groups.set(index, group);
            notifyDataSetChanged();
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
    public void removeItem(int index) {
        if (index > -1 && index < groups.size()) {
            groups.remove(index);
            notifyDataSetChanged();
        }
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
}
