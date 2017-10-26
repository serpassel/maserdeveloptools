package es.marser.backgroundtools.objectslistables.complex.controller;

import android.os.Parcelable;

import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableList;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableListPosition;

@SuppressWarnings("WeakerAccess")
public class ComplexExpandController<X extends ExpandableGroup<T>, T extends Parcelable> {

    private ComplexViewHolderController<X,T> complexViewHolderController;
    private ExpandableList<X, T> expandableList;

    public ComplexExpandController(
            ExpandableList<X, T> expandableList,
            ComplexViewHolderController<X,T> complexViewHolderController) {
        this.expandableList = expandableList;
        this.complexViewHolderController = complexViewHolderController;
    }

    /**
     * Contraer un grupo
     * <p>
     * Collapse a group
     *
     * @param listPosition posición del grupo al colapso
     *                     position of the group to collapse
     */
    private void collapseGroup(ExpandableListPosition listPosition) {
        expandableList.expandedGroupIndexes.put(listPosition.groupPos, false);
        if (complexViewHolderController != null) {
            complexViewHolderController.onGroupCollapsed(expandableList.getFlattenedGroupIndex(listPosition) + 1,
                    expandableList.groups.get(listPosition.groupPos).getItemCount());
        }
    }

    /**
     * Expandir un grupo
     * <p>
     * Expand a group
     *
     * @param listPosition el grupo a ser expandido
     *                     the group to be expanded
     */
    private void expandGroup(ExpandableListPosition listPosition) {
        expandableList.expandedGroupIndexes.put(listPosition.groupPos, true);
        if (complexViewHolderController != null) {
            complexViewHolderController.onGroupExpanded(expandableList.getFlattenedGroupIndex(listPosition) + 1,
                    expandableList.groups.get(listPosition.groupPos).getItemCount());
        }
    }

    /**
     * @param group the {@link ExpandableGroup} being checked for its collapsed state
     * @return true if {@code group} is expanded, false if it is collapsed
     */
    public boolean isGroupExpanded(X group) {
        int groupIndex = expandableList.groups.indexOf(group);
        return expandableList.expandedGroupIndexes.get(groupIndex);
    }

    /**
     * @param flatPos the flattened position of an item in the list
     * @return true if {@code group} is expanded, false if it is collapsed
     */
    public boolean isGroupExpanded(int flatPos) {
        ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPos);
        return expandableList.expandedGroupIndexes.get(listPosition.groupPos);
    }

    /**
     * @param flatPos The flat list position of the group
     * @return false if the group is expanded, *after* the toggle, true if the group is now collapsed
     */
    public boolean toggleGroup(int flatPos) {
        ExpandableListPosition listPos = expandableList.getUnflattenedPosition(flatPos);
        boolean expanded = expandableList.expandedGroupIndexes.get(listPos.groupPos);
        if (expanded) {
            collapseGroup(listPos);
        } else {
            expandGroup(listPos);
        }
        return expanded;
    }

    public boolean toggleGroup(X group) {
        ExpandableListPosition listPos =
                expandableList.getUnflattenedPosition(expandableList.getFlattenedGroupIndex(group));
        boolean expanded = expandableList.expandedGroupIndexes.get(listPos.groupPos);
        if (expanded) {
            collapseGroup(listPos);
        } else {
            expandGroup(listPos);
        }
        return expanded;
    }

}