package es.marser.backgroundtools.async;

/**
 * @author sergio
 *         Created by Sergio on 02/09/2017.
 *         Clase adaptadora para definiciones de oyentes de carga de datos asíncrona, con un unico objeto genérico de resultado
 *         <p>
 *         [EN]  Adapter class for asynchronous data load listeners definitions, with a single generic result object
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class TaskLoadingResult<T> implements DataUploaderTask<Void, T, Void> {

    /**
     * @param start Objecto Nulo [EN]  Null object
     */
    @Override
    public void onStart(Void start) {

    }

    /**
     * @param update objeto genérico procesado [EN]  processed generic object
     */
    @Override
    public void onUpdate(T update) {

    }

    /**
     * @param finish Objeto nulo [EN]  Null object

     */
    @Override
    public void onFinish(Void finish) {
    }

    /**
     * @param e Objeto de error generado durante la ejecución de la tarea
     *          <p>[EN]  Error object generated during task execution
     */
    @Override
    public void onFailure(Throwable e) {

    }
}
