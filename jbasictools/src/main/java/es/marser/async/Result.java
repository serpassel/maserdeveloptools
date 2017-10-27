package es.marser.async;

/**
 * @author sergio
 *         Created by sergio on 26/10/17.
 *         Clase para entrega de resultados en procesos asíncronos
 *         <p>
 *         [EN]  Class for delivery of results in asynchronous processes
 */

public interface Result<T> {
    /**
     * Resultado de la finalización de una tarea
     * <p>
     *     [EN]  Result of completing a task
     * @param result Objeto genérico procesado en la tarea [EN]  Processed generic object in the task
     */
    void onResult(T result);
}
