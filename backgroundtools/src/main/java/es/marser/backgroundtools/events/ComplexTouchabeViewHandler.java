package es.marser.backgroundtools.events;

import android.os.Parcelable;
import android.view.View;

import es.marser.backgroundtools.listables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by sergio on 24/10/17.
 *         Se utiliza en las listas.
 *         <p>
 *         Recoge las acciones de pulsación sobre elementos Touchables de tipo {@link ExpandableGroup}
 *         <p>
 *         Tienen que tener el TAG @string/TOUCHABLE_VIEW_ACTIONABLE.
 *         <p>
 *         Sobre escribirá cualquier evento anterior de pulsación
 *         <p>
 *         [EN]  It is used in the lists.
 *         <p>
 *         [EN]  Collect touch actions on elements Touchable type {@link ExpandableGroup}
 *         <p>
 *         [EN]  They must have the TAG @ string /TOUCHABLE_VIEW_ACTIONABLE
 *         <p>
 *         [EN]  Overwrite any previous pulse event
 *         <p>
 */

@SuppressWarnings("ALL")
public interface ComplexTouchabeViewHandler<X extends ExpandableGroup<T>, T extends Parcelable> {
    /**
     * * Manejador de eventos de pulsación sencilla en elementos pulsables
     * <p>
     * [EN]  Single-pulse event handler for push-button elements
     * TAG @string/TOUCHABLE_VIEW_ACTIONABLE
     *
     * @param v     vista pulsada [EN]  pulsed view
     * @param flap  posición plana [EN]  flat position
     * @param root  Vista raíz [EN]  Root view
     * @param index posición del grupoen la lista [EN]  position of the group in the list
     * @param group Objeto generico de tipo {@link ExpandableGroup} [EN]  Generic type object {@link ExpandableGroup}
     */
    void onGroupClick(View view, int flap, View root, int index, X group);

    /**
     * Manejador de eventos de pulsación prolongada en elementos pulsables
     * <p>
     * [EN]  Long-pulsed event handler on pushbutton elements
     * <p>
     * TAG @string/TOUCHABLE_VIEW_ACTIONABLE
     *
     * @param v     vista pulsada [EN]  pulsed view
     * @param flap  posición plana [EN]  flat position
     * @param root  Vista raíz [EN]  Root view
     * @param index posición del grupoen la lista [EN]  position of the group in the list
     * @param group Objeto generico de tipo {@link ExpandableGroup} [EN]  Generic type object {@link ExpandableGroup}
     */
    boolean onGroupLongClick(View view, int flap, View root, int index, X group);

    /**
     * * Manejador de eventos de pulsación sencilla en elementos pulsables
     * <p>
     * [EN]  Single-pulse event handler for push-button elements
     * TAG @string/TOUCHABLE_VIEW_ACTIONABLE
     *
     * @param v       vista pulsada [EN]  pulsed view
     * @param flap    posición plana [EN]  flat position
     * @param root    Vista raíz [EN]  Root view
     * @param index   posición del grupo en la lista [EN]  position of the group in the list
     * @param group   Objeto generico de tipo {@link ExpandableGroup} [EN]  Generic type object {@link ExpandableGroup}
     * @param childid posición del hijo en el grupo
     * @param child   Objeto generico hijo
     */
    void onChildClick(View view, int flap, View root, int groupid, X group, int childid, T child);

    /**
     * Manejador de eventos de pulsación prolongada en elementos pulsables
     * <p>
     * [EN]  Long-pulsed event handler on pushbutton elements
     * <p>
     * TAG @string/TOUCHABLE_VIEW_ACTIONABLE
     *
     * @param v       vista pulsada [EN]  pulsed view
     * @param flap    posición plana [EN]  flat position
     * @param root    Vista raíz [EN]  Root view
     * @param index   posición del grupoen la lista [EN]  position of the group in the list
     * @param group   Objeto generico de tipo {@link ExpandableGroup} [EN]  Generic type object {@link ExpandableGroup}
     * @param childid posición del hijo en el grupo
     * @param child   Objeto generico hijo
     */
    boolean onChildLongClick(View view, int flap, View root, int groupid, X group, int childid, T child);
}
