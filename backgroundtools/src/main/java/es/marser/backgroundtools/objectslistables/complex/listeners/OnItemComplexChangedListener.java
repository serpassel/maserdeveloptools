package es.marser.backgroundtools.objectslistables.complex.listeners;

import android.os.Parcelable;

import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by Sergio on 16/08/2017.
 *         Oyente de cambios en una lista expandible de elementos anidados
 *         <p>
 *         [EN]  Listener changes to an expandable list of nested element
 */

@SuppressWarnings("unused")
public interface OnItemComplexChangedListener<C extends Parcelable> {
    /**
     * Actualización de un grupo
     * <p>
     * [EN]  Updating a group
     *
     * @param indexchild posición del elemento anidado dentro del grupo [EN]  position of the nested element within the group
     * @param indexgroup posición del grupo [EN]  position of the group
     * @param group      grupo actualizado [EN]  updated group
     */
    void updateGroup(int indexchild, int indexgroup, ExpandableGroup<C> group);

    /**
     * Notificación a todos los elementos
     * <p>
     * [EN]  Notification to all elements
     */
    void notifityAll();


    /**
     * Called when a group is expanded
     *
     * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
     * @param itemCount the total number of children in the {@link ExpandableGroup}
     */
    void onGroupExpanded(int positionStart, int itemCount);

    /**
     * Called when a group is collapsed
     *
     * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
     * @param itemCount the total number of children in the {@link ExpandableGroup}
     */
    void onGroupCollapsed(int positionStart, int itemCount);
}
