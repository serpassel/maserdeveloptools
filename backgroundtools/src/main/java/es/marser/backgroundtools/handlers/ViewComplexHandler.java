package es.marser.backgroundtools.handlers;

import android.os.Parcelable;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by sergio on 22/10/17.
 *         Eventos de pulsación sobre la vista anidada en una lista de elementos expandibles
 *         <p>
 *         [EN]  Nested view click events in a list of expandable elements
 */

@SuppressWarnings("ALL")
public interface ViewComplexHandler<X extends ExpandableGroup<T>, T extends Parcelable> {

    /**
     *
     * Se llama cuando un grupo se expande
     * <p>
     * Called when a group is expanded
     *
     * @param positionStart la posición plana del primer anidado en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     * the flat position of the first child in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     * @param itemCount     el número total de anidados en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     *                      the total number of children in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     */
    void onGroupExpanded(X group);

    /**
     *
     * Llamado cuando un grupo está colapsado
     * <p>
     * Called when a group is collapsed
     *
     * @param positionStart la posición plana del primer anidado en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     *                      the flat position of the first child in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     * @param itemCount     el número total de anidados en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     *                      the total number of children in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     */
    void onGroupCollapsed(X group);

    /**
     * Pulsación corta sobre vista del elemento grupo
     * <p>
     * [EN]  Shortcut on Group Element View
     *
     * @param grouppos      posición del grupo en la lista de grupos [EN]  position of the group in the group list
     * @param group elemento genérico de tipo {@link ExpandableGroup}
     * @param flatpos       posición plana del elemento [EN]  flat position of the element
     * @param selectionmode modo de selección de la vista [EN]  view selection mode
     */
    void onClickGroupItem(int grouppos, X group, int flatpos, ListExtra selectionmode);

    /**
     * Pulsación prolongada sobre vista del elemento grupo
     * <p>
     * [EN]  Long click on view of nested element
     *
     * @param grouppos      posición del grupo en la lista de grupos [EN]  position of the group in the group list
     * @param group elemento genérico de tipo {@link ExpandableGroup}
     * @param flatpos       posición plana del elemento [EN]  flat position of the element
     * @param selectionmode modo de selección de la vista [EN]  view selection mode
     * @return veradero si se ha activado la pulsación prolongada [EN]  true if the long pulse has been activated
     */
    boolean onLongClickGroupItem(int grouppos, X group, int flatpos, ListExtra selectionmode);

    /**
     * Pulsación corta sobre vista del elemento anidado
     * <p>
     * [EN]  Nested element view shortcut
     *
     * @param grouppos      posición del grupo en la lista de grupos [EN]  position of the group in the group list
     * @param childpos      posición del elemento anidado en el grupo [EN]  position of the nested element in the group
     * @param child         elemento anidado genérico [EN]  generic nested element
     * @param flatpos       posición plana del elemento [EN]  flat position of the element
     * @param selectionmode modo de selección de la vista [EN]  view selection mode
     */
    void onClickChildItem(int grouppos, int childpos, T child, int flatpos, ListExtra selectionmode);

    /**
     * Pulsación prolongada sobre vista del elemento anidado
     * <p>
     * [EN]  Long click on view of nested element
     *
     * @param grouppos posición del grupo en la lista de grupos [EN]  position of the group in the group list
     * @param childpos posición del elemento anidado en el grupo [EN]  position of the nested element in the group
     * @param child    elemento anidado genérico [EN]  generic nested element
     * @param flatpos  posición plana del elemento [EN]  flat position of the element
     * @param selectionmode modo de selección de la vista [EN]  view selection mode
     * @return veradero si se ha activado la pulsación prolongada [EN]  true if the long pulse has been activated
     */
    boolean onLongClickChildItem(int grouppos, int childpos, T child, int flatpos, ListExtra selectionmode);
}
