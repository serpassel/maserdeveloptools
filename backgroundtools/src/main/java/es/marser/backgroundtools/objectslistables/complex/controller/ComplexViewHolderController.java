package es.marser.backgroundtools.objectslistables.complex.controller;

import android.os.Parcelable;

import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by sergio on 24/10/17.
 *         Conjunto de controladores para el colector de vistas de una una lista compleja
 *         <p>
 *         [EN]  Set of controllers for the view manifold from a complex list
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface ComplexViewHolderController<X extends ExpandableGroup<T>, T extends Parcelable> {


    /**
     * Called when a group is expanded
     *
     * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
     * @param itemCount     the total number of children in the {@link ExpandableGroup}
     */
    void onGroupExpanded(int positionStart, int itemCount);

    /**
     * Called when a group is collapsed
     *
     * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
     * @param itemCount     the total number of children in the {@link ExpandableGroup}
     */
    void onGroupCollapsed(int positionStart, int itemCount);

    /**
     * Indica si la vista grupo esta expandida
     * <p>
     * [EN]  Indicates whether the group view is expanded
     *
     * @param flatPos posición de la vista [EN]  flatPos of the view
     * @return verdadero si la vista está expandida [EN]  true if the view is expanded
     */
    boolean isGroupExpanded(int flatPos);

    /**
     * @param group the {@link ExpandableGroup} being checked for its collapsed state
     * @return true if {@code group} is expanded, false if it is collapsed
     */
    boolean isGroupExpanded(X group);

    /**
     * Pulsación sencilla sobre un elemento de tipo grupo
     * <p>
     * [EN]  Single click on an element
     *
     * @param view    vista pulsada [EN]  pulsed view
     * @param flatPos posición de la vista en el adapter [EN]  flatPos of the view on the adapter
     */
    void onGroupClick(BaseViewHolder<X> holder, int flatPos);

    /**
     * Pulsación prolongada sobre un elemento de tipo grupo
     * <p>
     * [EN]  Long press on an element de tipo grupo
     *
     * @param view    vista pulsada [EN]  pulsed view
     * @param flatPos posición de la vista en el adapter [EN]  flatPos of the view on the adapter
     */
    boolean onGroupLongClick(BaseViewHolder<X> holder, int flatPos);

    /**
     * Recupera el objeto generíco de un elemento de tipo grupo para una posición plana
     * <p>
     * [EN]  Retrieves the generic object of a group type element for a flat position
     *
     * @param flatPos posición del elemento [EN]  flatPos of the element
     * @return Objeto genérico [EN]  Generic object
     */
    X getGroupItemAt(int flatPos);

    /**
     * Devuelve la posición del grupo en la lista partiendo de la posición plana
     * <p>
     * [EN]  Returns the position of the group in the list starting from the flat position
     *
     * @param flaPos posición plana [EN]  flat position
     * @return posición en la lista [EN]  position in the list
     */
    int getGroupIndexAt(int flaPos);

    /**
     * Indica si la vista hijo esta seleccionada
     * <p>
     * [EN]  Indicates whether the Child view is selected
     *
     * @param flatPos posición de la vista [EN]  flatPos of the view
     * @return verdadero si la vista está seleccionada [EN]  true if the view is selected
     */
    boolean isChildSelected(int flatPos);

    /**
     * Pulsación sencilla sobre un elemento de tipo hijo
     * <p>
     * [EN]  Single click on an element
     *
     * @param view    vista pulsada [EN]  pulsed view
     * @param flatPos posición de la vista en el adapter [EN]  flatPos of the view on the adapter
     */
    void onChildClick(BaseViewHolder<T> holder, int flatPos);

    /**
     * Pulsación prolongada sobre un elemento de tipo hijo
     * <p>
     * [EN]  Long press on an element de tipo hijo
     *
     * @param view    vista pulsada [EN]  pulsed view
     * @param flatPos posición de la vista en el adapter [EN]  flatPos of the view on the adapter
     */
    boolean onChildLongClick(BaseViewHolder<T> holder, int flatPos);

    /**
     * Recupera el objeto generíco de un elemento de tipo hijo para una posición plana
     * <p>
     * [EN]  Retrieves the generic object of a Child type element for a flat position
     *
     * @param flatPos posición del elemento [EN]  flatPos of the element
     * @return Objeto genérico [EN]  Generic object
     */
    T getChildItemAt(int flatPos);

    /**
     * Devuelve la posición del hijo en la lista partiendo de la posición plana
     * <p>
     * [EN]  Returns the position of the Child in the list starting from the flat position
     *
     * @param flaPos posición plana [EN]  flat position
     * @return posición en la lista [EN]  position in the list
     */
    int getChildIndexAt(int flaPos);

}
