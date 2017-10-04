package es.marser.task_result;

/**
 * @author sergio
 *         Created by Sergio on 31/08/2017.
 *         Indicador para procedimientos asíncronos.
 *         <p>
 *         Indica si una tarea a terminado o entrega el error en caso contrario. Posibilidad de entregar objeto genérico como resultado
 *         <p>
 *         [EN]  Indicator for asynchronous procedures. Possibility of delivering generic object as a result
 *         <p>
 *         Indicates whether a task is terminated or otherwise delivered the error
 *         <p>
 */

@SuppressWarnings("unused")
public interface TaskFailure<T> {
    /**
     * Lanzar si la tarea termina con éxito. Incluye un objeto genérico para entregar como resultado
     * <p>
     * [EN]  Launch if the task ends successfully.  Includes a generic object to deliver as a result
     *
     * @param finish Objecto generico procesado como resultado o Void si no es necesario un resultado
     *               <p>
     *               [EN]  Generic object processed as a result or Void if no result is needed es
     */
    void onFinish(T finish);

    /**
     * Lanzar en caso de error
     * <p>
     * [EN]  Throw in case of error
     *
     * @param e Error generado
     *          <p>[EN]  Error generated
     */
    void onFailure(Throwable e);
}
