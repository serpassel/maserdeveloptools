package es.marser.backgroundtools.async;

/**
 * @author sergio
 *         Created by Sergio on 24/08/2017.
 *         Definición de oyente de resultado de tarea asíncrona, con devolución de error en caso de fallo en la ejecución
 *         <p>
 *         [EN]  Definition of asynchronous task listener, with error return in case of execution failure
 * @see es.marser.backgroundtools.async.TaskResult
 */

@SuppressWarnings("unused")
public interface TaskFailure<T> extends TaskResult<T> {
    /**
     * Resultado fallido de una tarea procesada
     * <p>
     * [EN]  Failed result of a processed task
     *
     * @param e Objeto de error generado [EN]  Error object generated
     */
    void onFailure(Throwable e);
}
