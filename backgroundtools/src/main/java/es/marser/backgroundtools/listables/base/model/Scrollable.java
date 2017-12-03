package es.marser.backgroundtools.listables.base.model;

/**
 * @author sergio
 *         Created by sergio on 3/12/17.
 *         Objeto que puede hacer scroll a posiciones concretas
 *         <p>
 *         [EN]  Object that can scroll to specific positions
 */

@SuppressWarnings("unused")
public interface Scrollable {
    /**
     * Posiciona la vista en el primer elemento de la lista
     * <p>
     * [EN]  Position the view on the first item in the list
     */
    void scrollToFirst();

    /**
     * Posicionar la vista en la posición señalada
     * <p>
     * [EN]  Position the view in the indicated position
     *
     * @param position posición plana en el adaptador [EN]  flat position on the adapter
     */
    void scrollToPosition(int position);

    /**
     * Guarda la posición del pimer elemento visible
     * <p>
     * [EN]  Save the position of the visible element pimer
     */
    int getCurrentScrollPosition();

    /**
     * Posiciona el foco en la última posición de la lista de elementos
     * <p>
     * [EN]  Position the focus in the last position in the list of elements
     */
    void scrollToLast();
}
