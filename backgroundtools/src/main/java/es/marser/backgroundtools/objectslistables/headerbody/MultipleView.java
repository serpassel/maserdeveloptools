package es.marser.backgroundtools.objectslistables.headerbody;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Definición de componente de listas de multiples vistas
 *         <p>
 *         [EN]  Definition of multi-view list component
 */

public interface MultipleView<T> {
    int viewType();

    T getElement();
}
