package es.marser.task_result;

/**
 * @author sergio
 *         Created by Sergio on 24/08/2017.
 *         Indicador para procedimientos asíncronos.
 *         <p>
 *         Resultado de tarea.
 *         <p>
 *         [EN]  Indicator for asynchronous procedures. Possibility of delivering generic object as a result
 *         <p>
 *         Indicates whether a task is terminated or otherwise delivered the error
 *         <p>
 */

@SuppressWarnings("unused")
public interface TaskResult<T> {
    /**
     * Lanzar resultado de la tarea y código de ejecución
     * <p>
     * [EN]  Launch Task Result and Execution Code
     * <p>
     * ej: onTaskFinish(Activity.RESULT_OK, Object)
     *
     * @param resultCode Indicador numérico del resultado ej; Ok, Cancel [EN]  Numerical indicator of the result ej;  Ok cancel
     * @param result     Objecto genérico procesado como resultado de la tarea [EN]  Generic object processed as a result of the task
     */
    void onTaskFinish(int resultCode, T result);
}
