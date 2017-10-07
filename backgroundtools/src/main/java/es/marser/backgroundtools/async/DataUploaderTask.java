package es.marser.backgroundtools.async;

/**
 * @author sergio
 *         Created by Sergio on 24/08/2017.
 *         Tarea de carga de datos asíncrona
 *         <p>
 *         [EN]  Asynchronous Data Loading Task
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public interface DataUploaderTask<A, B, C> {
    /**
     * Iniciar tarea
     * <p>
     * [EN]  Start task
     *
     * @param start Objeto genérico procesado previo inicio de la tarea
     *              <p>[EN] Generic object processed before task start
     */
    void onStart(A start);

    /**
     * Iteración durante la ejecución de la tarea
     * <p>
     * [EN]  Iteration during task execution
     *
     * @param update objeto genérico procesado durante la ejecución de la tarea
     *               <p>[EN]  generic object processed during the execution of the task
     */
    void onUpdate(B update);

    /**
     * Finalización de la tarea de carga de datos
     * <p>
     * [EN]  Completing the data upload task
     *
     * @param finish objeto genérico procesado al finalizar la tarea
     *               <p>[EN]  processed generic object at the end of the task
     */
    void onFinish(C finish);

    /**
     * Fallo en la ejecución de la tarea
     * <p>
     * [EN]  Failure to execute the task
     *
     * @param e Objeto de error generado durante lla ejecución de la tarea
     *          <p>[EN]  Error object generated during the execution of the task
     */
    void onFailure(Throwable e);
}
