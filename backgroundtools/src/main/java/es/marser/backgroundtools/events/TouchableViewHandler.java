package es.marser.backgroundtools.events;

import android.view.View;

/**
 * @author sergio
 *         Created by Sergio on 14/08/2017.
 *         Se utiliza en las listas.
 *         <p>
 *         Recoge las acciones de pulsación sobre elementos Touchables.
 *         <p>
 *         Tienen que tener el TAG @string/INCLUDE_ITEM_ACTIONS.
 *         <p>
 *         Sobre escribirá cualquier evento anterior de pulsación
 *         <p>
 *         [EN]  It is used in the lists.
 *         <p>
 *         [EN]  Collect touch actions on Touchable elements.
 *         <p>
 *         [EN]  They must have the TAG @ string / INCLUDE_ITEM_ACTIONS.
 *         <p>
 *         [EN]  Overwrite any previous pulse event
 *         <p>
 */

@SuppressWarnings("ALL")
public interface TouchableViewHandler<T> {
    /**
     * Manejador de eventos de pulsación sencilla en elementos pulsables
     * <p>
     * [EN]  Single-pulse event handler for push-button elements
     * TAG @string/INCLUDE_ITEM_ACTIONS
     *
     * @param view  Vista que inicia la acción [EN]  View that initiates the action
     * @param v        vista pulsada [EN]  pulsed view
     * @param position posición en el adpater [EN]  position in the adpater
     * @param item     objeto de datos [EN]  data object
     * @param root     Vista grupal [EN]  Group view
     */
    void onClick(View view, int position, T item, View root);

    /**
     * Manejador de eventos de pulsación prolongada en elementos pulsables
     * <p>
     * [EN]  Long-pulsed event handler on pushbutton elements
     * <p>
     * TAG @string/INCLUDE_ITEM_ACTIONS
     *
     * @param view  Vista que inicia la acción [EN]  View that initiates the action
     * @param v        vista pulsada [EN]  pulsed view
     * @param position posición en el adpater [EN]  position in the adpater
     * @param item     objeto de datos [EN]  data object
     * @param root     Vista grupal [EN]  Group view
     */
    boolean onLongClick(View view, int position, T item, View root);
}
