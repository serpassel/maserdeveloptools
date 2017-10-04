package es.marser.task_result;

/**
 * @author sergio
 *         Created by Sergio on 31/08/2017.
 *         Resultados y evolución de tareas asíncronas en bucle
 *         <p>
 *         [EN]  Results and evolution of asynchronous tasks in loop
 */

@SuppressWarnings({"WeakerAccess", "unused", "EmptyMethod"})
public interface TaskUpdater<A, B, C> {
    /**
     * Lanzar al incio  del bucle
     * <p>
     * [EN]  Throw at the beginning of the loop
     *
     * @param start Objecto genérico de tipo A
     */
    void onStart(A start);

    /**
     * Lanzar para publicar resultados por iteración
     * <p>
     * [EN]  Launch to post results by iteration
     *
     * @param update Objecto genérico de tipo B
     */
    void onUpdate(B update);

    /**
     * Lanzar al completar el bucle
     * <p>
     * [EN]  Launch upon completion of the loop
     *
     * @param finish Objecto generíco de tipo C
     */
    void onFinish(C finish);

    /**
     * Lanzar en caso de error
     * <p>
     * [EN]  Throw in case of error
     *
     * @param e Error generado [EN]  Error generated
     */
    void onFailure(Throwable e);
}
