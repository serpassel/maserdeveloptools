package es.marser.async;

/**
 * @author sergio
 *         Created by Sergio on 24/08/2017.
 *          Definición de oyente de resultado de tarea asíncrona
 *          <p>
 *          [EN]  Definition of asynchronous task listener
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public interface TaskResult<T> {
    /**
     * Resultado de la finalización de una tarea
     * <p>
     *     [EN]  Result of completing a task
     * @param resultCode Codigo de Resultado [EN]  Result Code
     * @param result Objeto genérico procesado en la tarea [EN]  Processed generic object in the task
     */
    void onFinish(int resultCode, T result);
}
